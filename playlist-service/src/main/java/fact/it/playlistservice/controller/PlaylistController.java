package fact.it.playlistservice.controller;

import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;

//    Get all playlists
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaylistResponse>getAllPlaylists(){
        return playlistService.getAllPlaylists();
    }

//    Get a playlist by id
    @GetMapping("/id/{id}")
    public ResponseEntity<PlaylistResponse> getPlaylistById (@PathVariable("id") Long playlistId) {
        Optional<PlaylistResponse> playlist = playlistService.getPlaylistById(playlistId);
        if (playlist.isPresent()) {
            return new ResponseEntity<>(playlist.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    Create a playlist
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createPlaylist(@RequestBody PlaylistRequest playlistRequest){
        playlistService.createPlaylist(playlistRequest);
    }

//    Edit a playlist
    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void editPlaylist(@PathVariable("id") Long id,@RequestBody PlaylistRequest playlistRequest)
    {
        playlistService.editPlaylist(id, playlistRequest);
    }

//    Delete a playlist
    @DeleteMapping("id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlaylist(@PathVariable("id") Long id)
    {
        playlistService.deletePlaylist(id);
    }
}