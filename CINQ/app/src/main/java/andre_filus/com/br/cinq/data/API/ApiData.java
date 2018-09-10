package andre_filus.com.br.cinq.data.API;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.view.ViewGroup;

import java.util.ArrayList;

import andre_filus.com.br.cinq.R;
import andre_filus.com.br.cinq.data.callback.IDataReceiverCallback;
import andre_filus.com.br.cinq.models.Album;
import andre_filus.com.br.cinq.utils.ProgressDialogManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by André Filus on 08/09/2018.
 */

public class ApiData {

    public static IAlbumService mRetrofit;

    public ApiData() {
        mRetrofit = RetrofitBuild.getInstance().create(IAlbumService.class);
    }

    public static void getAllAlbums(@NonNull Context context, LoaderManager loaderManager, ViewGroup viewGroup, IDataReceiverCallback<ArrayList<Album>> callback) {
        ProgressDialogManager.showLoading(0, context, R.string.s_loading, false, null);
//        Call<JSONArray> callAlbums = mRetrofit.getAlbums();
        Call<ArrayList<Album>> callAlbums = mRetrofit.getAlbums();
        callAlbums.enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                ProgressDialogManager.hideLoading(0);
                switch (response.code()) {
                    case 200:
//                        ArrayList<Album> albums = new ArrayList<>();
                        ArrayList<Album> albums = response.body();
//                        JSONArray jsonArray = response.body();
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            try {
//                                albums.add(new Gson().fromJson(jsonArray.getString(i), Album.class));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
                        callback.onDataReceived(albums);
                        ProgressDialogManager.hideLoading(0);
                        break;
                    default:
                        callback.onDataNotReceived(new Throwable("Erro inesperado, verifique o estado da sua conexão"));
                        ProgressDialogManager.hideLoading(0);
                        break;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                callback.onDataNotReceived(new Throwable("Erro inesperado, verifique o estado da sua conexão"));
                ProgressDialogManager.hideLoading(0);
            }
        });
    }
}
