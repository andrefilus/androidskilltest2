package andre_filus.com.br.cinq.data.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by André Filus on 08/09/2018.
 */

public class RetrofitBuild {

    private static Retrofit retrofit = null;

    //CONSTRUCTOR OF REQUESTS
    private static Gson gson = new GsonBuilder().create();

    public static Retrofit getInstance() {
        return retrofit = constructRetrofit(retrofit, "https://jsonplaceholder.typicode.com/");
    }

    //construct your retrofit with url
    public static Retrofit constructRetrofit(Retrofit retrofit, String url) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .build()
                    )
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
