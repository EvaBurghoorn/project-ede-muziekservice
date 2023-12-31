package fact.it.playlistservice;

import fact.it.playlistservice.dto.PlaylistRequest;
import fact.it.playlistservice.dto.PlaylistResponse;
import fact.it.playlistservice.model.Playlist;
import fact.it.playlistservice.repository.PlaylistRepository;
import fact.it.playlistservice.service.PlaylistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceApplicationTests {

    @InjectMocks
    private PlaylistService playlistService;

    @Mock
    private PlaylistRepository playlistRepository;

    @Test
    public void testGetAllPlaylists() {
        // Arrange
        List<Playlist> playlists = new ArrayList<>();
        Playlist playlist1 = new Playlist();
        playlist1.setId(1L);
        playlist1.setTitle("My Playlist 1");
        playlist1.setUsername("kyara123");
        playlist1.setUniqueIdentifier("1");
        playlist1.setPrivate(true);
        playlist1.setDescription("This is playlist1");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setTitle("My Playlist 2");
        playlist2.setUsername("evaBurghoorn");
        playlist2.setUniqueIdentifier("2");
        playlist2.setPrivate(false);
        playlist2.setDescription("This is the second playlist");

        playlists.add(playlist1);
        playlists.add(playlist2);

        when(playlistRepository.findAll()).thenReturn(playlists);

        // Act
        List<PlaylistResponse> result = playlistService.getAllPlaylists();

        // Assert
        assertEquals(2, result.size());
        verify(playlistRepository, times(1)).findAll();
    }

    @Test
    public void testGetPlayListByIdExists(){
        // Arrange
        List<Playlist> playlists = new ArrayList<>();
        Playlist playlist1 = new Playlist();
        playlist1.setId(1L);
        playlist1.setTitle("My Playlist 1");
        playlist1.setUsername("user123");
        playlist1.setUniqueIdentifier("20");
        playlist1.setPrivate(true);
        playlist1.setDescription("This is playlist1");

        Playlist playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setTitle("My Playlist 2");
        playlist2.setUsername("lexi");
        playlist1.setUniqueIdentifier("12");
        playlist1.setPrivate(false);
        playlist1.setDescription("This is the second playlist");

        playlists.add(playlist1);
        playlists.add(playlist2);

        // Id of the first playlist
        Long playlistId = 1L;

        when(playlistRepository.findById(playlistId)).thenReturn(playlists.stream().filter(p -> p.getId().equals(playlistId)).findFirst());

        // Act
        Optional<PlaylistResponse> responseOptional = playlistService.getPlaylistById(playlistId);

        //Assert
        assertTrue(responseOptional.isPresent());
        assertEquals("My Playlist 1", responseOptional.get().getTitle());

    }
    @Test
    public void testGetPlaylistByIdWhenNotExists() {
        // Arrange
        List<Playlist> playlists = new ArrayList<>();
        Playlist playlist1 = new Playlist();
        playlist1.setId(5L);
        playlist1.setTitle("Pop music");
        playlist1.setUsername("eva1");
        playlist1.setUniqueIdentifier("14");
        playlist1.setPrivate(false);
        playlist1.setDescription("Pop music 2020");

        Playlist playlist2 = new Playlist();
        playlist2.setId(3L);
        playlist2.setTitle("My Playlist");
        playlist2.setUsername("lexi");
        playlist1.setUniqueIdentifier("5");
        playlist1.setPrivate(true);
        playlist1.setDescription("This is my favorite playlist");

        playlists.add(playlist1);
        playlists.add(playlist2);
//        Id of the playlist
        Long nonExistentPlaylistId = 4L;

        when(playlistRepository.findById(nonExistentPlaylistId)).thenReturn(Optional.empty());

        // Act
        Optional<PlaylistResponse> responseOptional = playlistService.getPlaylistById(nonExistentPlaylistId);

        // Assert
        assertTrue(responseOptional.isEmpty());
    }

    @Test
    public void testCreatePlaylist(){
        // Arrange
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setTitle("My Playlist");
        playlistRequest.setUsername("kyara2");
        playlistRequest.setUniqueIdentifier("25");
        playlistRequest.setPrivate(false);
        playlistRequest.setDescription("This is a test playlist");

        // Act
        playlistService.createPlaylist(playlistRequest);

        // Assert
        verify(playlistRepository, times(1)).save(any(Playlist.class));

    }

    @Test
    public void testEditPlaylist(){
        // Arrange
        Playlist playlist = new Playlist();
        playlist.setId(5L);
        playlist.setTitle("My Playlist");
        playlist.setUsername("lexi");
        playlist.setUniqueIdentifier("25");
        playlist.setPrivate(false);
        playlist.setDescription("This is a test playlist");


        when(playlistRepository.findById(5L)).thenReturn(Optional.of(playlist));

        // Act
        PlaylistRequest playlistRequest = new PlaylistRequest();
        playlistRequest.setTitle("Podcasts");
        playlistRequest.setUniqueIdentifier("20");
        playlistRequest.setDescription("Podcasts about life");

        playlistService.editPlaylist(playlist.getId(), playlistRequest);

        // Assert
        verify(playlistRepository, times(1)).findById(5L);
        verify(playlistRepository, times(1)).save(any(Playlist.class));
        assertEquals("Podcasts", playlist.getTitle());
        assertEquals("20", playlist.getUniqueIdentifier());
        assertEquals("Podcasts about life", playlist.getDescription());

    }

    @Test
    public void testDeletePlaylist(){
        // Arrange
        Long playlistId = 1L;
        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setTitle("My Playlist");
        playlist.setUsername("lexi");
        playlist.setUniqueIdentifier("25");
        playlist.setPrivate(false);
        playlist.setDescription("This is a test playlist");

        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        // Act
        playlistService.deletePlaylist(playlistId);

        // Assert
        verify(playlistRepository, times(1)).findById(playlistId);
        verify(playlistRepository, times(1)).delete(playlist);
    }
}