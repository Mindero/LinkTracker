package backend.academy.scrapper.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import backend.academy.scrapper.exception.LinkDontExistException;
import backend.academy.scrapper.exception.NotSuchSDKException;
import backend.academy.scrapper.repo.LinkRepository;
import backend.academy.scrapper.repo.Track;
import backend.academy.scrapper.service.sdk.LinkSDK;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScrapperServiceTest {
    @Mock
    LinkRepository repo;

    @Mock
    List<LinkSDK> sdkList;

    @InjectMocks
    private ScrapperService scrapperService;

    @Mock
    LinkSDK linkSDK;

    @Test
    void addUser() {
        doNothing().when(repo).addUser(1L);

        scrapperService.addUser(1L);

        verify(repo, times(1)).addUser(1L);
    }

    @Test
    void addTrackLink_successful() throws NotSuchSDKException {
        Track track = new Track(1L, "aboba", new ArrayList<>(), new ArrayList<>());

        when(linkSDK.validURL(track.link())).thenReturn(true);
        when(sdkList.stream()).thenReturn(Stream.of(linkSDK));
        doNothing().when(repo).addTrack(1L, track);

        scrapperService.addTrackLink(1L, track);
        verify(repo, times(1)).addTrack(1L, track);
    }

    @Test
    void unTrack_successful() {
        when(repo.unTrack(1L, "aboba")).thenReturn(true);

        scrapperService.unTrack(1L, "aboba");
        verify(repo, times(1)).unTrack(1L, "aboba");
    }

    @Test
    void unTrack_exception() {
        when(repo.unTrack(1L, "aboba")).thenReturn(false);

        LinkDontExistException ex =
                assertThrows(LinkDontExistException.class, () -> scrapperService.unTrack(1L, "aboba"));
        verify(repo, times(1)).unTrack(1L, "aboba");
    }

    @Test
    void getLinkList() {
        List<Track> tracks = List.of(
                new Track(1L, "a", List.of("a"), new ArrayList<>()),
                new Track(1L, "b", new ArrayList<>(), List.of("b")),
                new Track(1L, "b", new ArrayList<>(), new ArrayList<>()));
        List<Link> expected = List.of(
                new Link(1L, "a", List.of("a"), new ArrayList<>()),
                new Link(1L, "b", new ArrayList<>(), List.of("b")),
                new Link(1L, "b", new ArrayList<>(), new ArrayList<>()));

        when(repo.getLinkList(1L)).thenReturn(tracks);

        List<Link> result = scrapperService.getLinkList(1L);

        assertThat(result).isEqualTo(expected);
        verify(repo, times(1)).getLinkList(1L);
    }
}
