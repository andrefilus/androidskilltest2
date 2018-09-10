package andre_filus.com.br.cinq.data.API;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import andre_filus.com.br.cinq.models.Album;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by André Filus on 08/09/2018.
 */

public interface IAlbumService {
    @GET("/photos")
//    Call<JSONArray> getAlbums();
    Call<ArrayList<Album>> getAlbums();
}
