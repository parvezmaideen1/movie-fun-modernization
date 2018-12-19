package org.superbiz.moviefun.moviesapi;

import org.apache.tika.io.IOUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestOperations;
import org.superbiz.moviefun.blobstore.Blob;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpMethod.GET;

public class AlbumsClient {

    private String albumsUrl;
    private RestOperations restOperations;

    private static ParameterizedTypeReference<List<AlbumInfo>> albumListType = new ParameterizedTypeReference<List<AlbumInfo>>() {
    };
    private static ParameterizedTypeReference<HttpEntity<byte[]>> httpEntityParam = new ParameterizedTypeReference<HttpEntity<byte[]>>() {
        @Override
        public Type getType() {
            return super.getType();
        }
    };

    public AlbumsClient(String albumsUrl, RestOperations restOperations) {
        this.albumsUrl = albumsUrl;
        this.restOperations = restOperations;
    }

    public void addAlbum(AlbumInfo albumInfo) {
        restOperations.postForEntity(albumsUrl, albumInfo, AlbumInfo.class);
    }

    public AlbumInfo find(long id) {
      // ResponseEntity<AlbumInfo> albumInfo = restOperations.getForEntity(albumsUrl, AlbumInfo.class, id);
       //System.out.println("@@@@@@@@@@@@@@@@@@@<"+albumInfo);
        //return restOperations.exchange(albumsUrl, GET, null, albumInfoParam).getBody();
        return restOperations.getForObject(albumsUrl+"/"+id, AlbumInfo.class);
    }

    public List<AlbumInfo> getAlbums() {
        return restOperations.exchange(albumsUrl, GET, null, albumListType).getBody();
    }
    public ResponseEntity<HttpEntity<byte[]>> getCover(long albumId) throws IOException {
        String restURL=albumsUrl + "/" + albumId + "/cover";
        //HttpEntity<byte[]> httpEntity = new HttpEntity<>();
        /*ResponseEntity respEntity = restOperations.exchange(restURL, GET, null ,httpEntityParam);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(respEntity.getHeaders().getContentType());
        headers.setContentLength(respEntity.getHeaders().getContentLength());

        return new HttpEntity<byte[]>((byte[])respEntity.getBody(), headers);*/
        return restOperations.exchange(restURL, GET, null ,httpEntityParam);

    }
    /*@Transactional
    public void deleteAlbum(Album album) {
        entityManager.remove(album);
    }

    @Transactional
    public void updateAlbum(Album album) {
        entityManager.merge(album);
    }*/
}
