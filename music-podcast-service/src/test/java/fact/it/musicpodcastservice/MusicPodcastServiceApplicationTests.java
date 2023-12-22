package fact.it.musicpodcastservice;

import fact.it.musicpodcastservice.dto.MusicPodcastRequest;
import fact.it.musicpodcastservice.dto.MusicPodcastResponse;
import fact.it.musicpodcastservice.model.MusicPodcast;
import fact.it.musicpodcastservice.repository.MusicPodcastRepository;
import fact.it.musicpodcastservice.service.MusicPodcastService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MusicPodcastServiceApplicationTests {

	@InjectMocks
	private MusicPodcastService musicPodcastService;

	@Mock
	private MusicPodcastRepository musicPodcastRepository;
	@Test
	public void testGetAllMusicPodcast(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
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

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		when(musicPodcastRepository.findAll()).thenReturn(musicPodcasts);

		// Act
		List<MusicPodcastResponse> result = musicPodcastService.getAllMusicPodcast();

		// Assert
		assertEquals(2, result.size());
		verify(musicPodcastRepository, times(1)).findAll();
	}

	@Test
	public void testGetAllSongs(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
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

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		when(musicPodcastRepository.findAll()).thenReturn(musicPodcasts);

		// Act
		List<MusicPodcastResponse> result = musicPodcastService.getAllSongs();

		// Assert
		assertEquals(1, result.size());
		verify(musicPodcastRepository, times(1)).findAll();
	}

	@Test
	public void testGetAllPodcast(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
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

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		when(musicPodcastRepository.findAll()).thenReturn(musicPodcasts);

		// Act
		List<MusicPodcastResponse> result = musicPodcastService.getAllPodcast();

		// Assert
		assertEquals(1, result.size());
		verify(musicPodcastRepository, times(1)).findAll();
	}

	@Test
	public void testGetMusicPodcastByUniqueIdentifier(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
		MusicPodcast musicPodcast = new MusicPodcast();
		musicPodcast.setId("1");
		musicPodcast.setTitle("Title1");
		musicPodcast.setArtist("Artist1");
		musicPodcast.setDurationSeconds(200);
		musicPodcast.setGenre("Pop");
		musicPodcast.setPodcast(false);
		musicPodcast.setUniqueIdentifier("Title1Artist1");

		MusicPodcast musicPodcast_two = new MusicPodcast();
		musicPodcast.setId("2");
		musicPodcast_two.setTitle("TitlePodcast");
		musicPodcast_two.setArtist("Artist2");
		musicPodcast_two.setDurationSeconds(5000);
		musicPodcast_two.setGenre("Drama");
		musicPodcast_two.setPodcast(true);
		musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		String musicPodcastUniqueIdentifier = "Title1Artist1";
		when(musicPodcastRepository.findByUniqueIdentifier(musicPodcastUniqueIdentifier)).thenReturn(musicPodcast);


		// Act
		Optional<MusicPodcast> responseOptional = musicPodcastService.getMusicPodcastByUniqueIdentifier(musicPodcastUniqueIdentifier);

		// Assert
		assertTrue(responseOptional.isPresent());
		assertEquals("Title1", responseOptional.get().getTitle());
	}

	@Test
	public void testGetMusicPodcastByUniqueIdentifier_NotFound(){
		// Arrange
		List<MusicPodcast> musicPodcasts = new ArrayList<>();
		MusicPodcast musicPodcast = new MusicPodcast();
		musicPodcast.setId("1");
		musicPodcast.setTitle("Title1");
		musicPodcast.setArtist("Artist1");
		musicPodcast.setDurationSeconds(200);
		musicPodcast.setGenre("Pop");
		musicPodcast.setPodcast(false);
		musicPodcast.setUniqueIdentifier("Title1Artist1");

		MusicPodcast musicPodcast_two = new MusicPodcast();
		musicPodcast.setId("2");
		musicPodcast_two.setTitle("TitlePodcast");
		musicPodcast_two.setArtist("Artist2");
		musicPodcast_two.setDurationSeconds(5000);
		musicPodcast_two.setGenre("Drama");
		musicPodcast_two.setPodcast(true);
		musicPodcast_two.setUniqueIdentifier("TitlePodcastArtist2");

		musicPodcasts.add(musicPodcast);
		musicPodcasts.add(musicPodcast_two);

		String nonExistentUniqueIdentifier = "NonExistent";
		when(musicPodcastRepository.findByUniqueIdentifier(nonExistentUniqueIdentifier)).thenReturn(null);

		// Act
		Optional<MusicPodcast> responseOptional = musicPodcastService.getMusicPodcastByUniqueIdentifier(nonExistentUniqueIdentifier);

		// Assert
		assertFalse(responseOptional.isPresent());
	}

}
