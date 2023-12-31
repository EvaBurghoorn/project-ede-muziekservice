package fact.it.musicpodcastservice.service;

import fact.it.musicpodcastservice.dto.MusicPodcastRequest;
import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
import fact.it.musicpodcastservice.dto.RatingResponse;
import fact.it.musicpodcastservice.model.MusicPodcast;
import fact.it.musicpodcastservice.repository.MusicPodcastRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicPodcastService {
    private final MusicPodcastRepository musicPodcastRepository;
    private final WebClient webClient;

    @Value("${ratingservice.baseurl}")
    private String ratingServiceBaseUrl;


    @PostConstruct
    public void loadData(){
        if(musicPodcastRepository.count() <= 0){
            MusicPodcast musicPodcast = new MusicPodcast();
            musicPodcast.setTitle("Title1");
            musicPodcast.setArtist("Artist1");
            musicPodcast.setDurationSeconds(200);
            musicPodcast.setGenre("Pop");
            musicPodcast.setPodcast(false);
            musicPodcast.setUniqueIdentifier("Title1Artist1");

            MusicPodcast musicPodcast_two = new MusicPodcast();
            musicPodcast_two.setTitle("TitlePodcast");
            musicPodcast_two.setArtist("Artist2");
            musicPodcast_two.setDurationSeconds(5000);
            musicPodcast_two.setGenre("Drama");
            musicPodcast_two.setPodcast(true);
            musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");
            musicPodcastRepository.save(musicPodcast);
            musicPodcastRepository.save(musicPodcast_two);
        }
    }


//     Get all musicPodcasts with liked rating per user
    public List<MusicPodcastResponse> getAllMusicPodcastsWithRatingLikedPerUser(RatingResponse ratingResponse) {

        String username = ratingResponse.getUsername();

        if (username != null) {

            RatingResponse[] ratingResponsePerUserArray = webClient.get()
                    .uri(ratingServiceBaseUrl + "/rating/username/" + username)
                    .retrieve()
                    .bodyToMono(RatingResponse[].class)
                    .block();


            List<MusicPodcastResponse> musicPodcastResponses = Arrays.stream(ratingResponsePerUserArray)
                    .filter(rating -> rating.isLiked()) // Filter for liked ratings
                    .map(RatingResponse::getUniqueIdentifier)
                    .map(this::getMusicPodcastByUniqueIdentifier)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(this::mapToMusicPodcastResponse)
                    .collect(Collectors.toList());

            return musicPodcastResponses;
        }
            return Collections.emptyList();

    }


    //    Get all the songs and podcasts
    public List<MusicPodcastResponse> getAllMusicPodcast(){
        List<MusicPodcast> musicPodcasts = musicPodcastRepository.findAll();
        return musicPodcasts.stream().map(this::mapToMusicPodcastResponse).toList();
    }

    //    Get all songs
    public List<MusicPodcastResponse> getAllSongs() {
        List<MusicPodcast> musicPodcasts = musicPodcastRepository.findAll();
        return musicPodcasts.stream()
                .filter(musicPodcast -> !musicPodcast.isPodcast())
                .map(this::mapToMusicPodcastResponse).toList();
    }

    //    Get all the podcasts
    public List<MusicPodcastResponse> getAllPodcast() {
        List<MusicPodcast> musicPodcasts = musicPodcastRepository.findAll();
        return musicPodcasts.stream()
                .filter(MusicPodcast:: isPodcast)
                .map(this::mapToMusicPodcastResponse)
                .toList();
    }

    // Get a music podcast by unique identifier
    public Optional<MusicPodcast> getMusicPodcastByUniqueIdentifier(String songUniqueIdentifier) {
        return Optional.ofNullable(musicPodcastRepository.findByUniqueIdentifier(songUniqueIdentifier));
    }


    private MusicPodcastResponse mapToMusicPodcastResponse(MusicPodcast musicPodcast) {
        return MusicPodcastResponse.builder()
                .id(musicPodcast.getId())
                .title(musicPodcast.getTitle())
                .artist(musicPodcast.getArtist())
                .genre(musicPodcast.getGenre())
                .durationSeconds(musicPodcast.getDurationSeconds())
                .isPodcast(musicPodcast.isPodcast())
                .uniqueIdentifier(musicPodcast.getUniqueIdentifier())
                .build();
    }


}
