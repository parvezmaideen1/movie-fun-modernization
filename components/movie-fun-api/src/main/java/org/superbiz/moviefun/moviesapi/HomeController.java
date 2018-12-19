package org.superbiz.moviefun.moviesapi;

import org.apache.tika.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.superbiz.moviefun.blobstore.Blob;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static java.lang.String.format;

@Controller
public class HomeController {

    private final MoviesClient moviesClient;
    private final AlbumsClient albumsClient;
    private final MovieFixtures movieFixtures;
    private final AlbumFixtures albumFixtures;

    public HomeController(MoviesClient moviesClient, AlbumsClient albumsClient, MovieFixtures movieFixtures, AlbumFixtures albumFixtures) {
        this.moviesClient = moviesClient;
        this.albumsClient = albumsClient;
        this.movieFixtures = movieFixtures;
        this.albumFixtures = albumFixtures;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/setup")
    public String setup(Map<String, Object> model) {
        for (MovieInfo movieInfo : movieFixtures.load()) {
            moviesClient.addMovie(movieInfo);
        }

        for (AlbumInfo albumInfo : albumFixtures.load()) {
            albumsClient.addAlbum(albumInfo);
        }

        model.put("movies", moviesClient.getMovies());
        model.put("albums", albumsClient.getAlbums());

        return "setup";
    }
    @GetMapping("/albums")
    public String albums(Map<String, Object> model) {
        model.put("albums", albumsClient.getAlbums());
        return "albums";
    }
    @GetMapping("/albums/{id}")
    public String albums(Map<String, Object> model, @PathVariable long id) {
        model.put("album", albumsClient.find(id));
        System.out.println("&&&&&&&&&&&<"+albumsClient.find(id));
        return "albumDetails";
    }

    @GetMapping("/albums/{albumId}/cover")
    public ResponseEntity<HttpEntity<byte[]>> getCover(@PathVariable long albumId) throws IOException, URISyntaxException {


        return albumsClient.getCover(albumId) ;
    }
}
