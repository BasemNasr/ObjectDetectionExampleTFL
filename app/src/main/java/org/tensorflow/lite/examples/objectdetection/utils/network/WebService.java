package org.tensorflow.lite.examples.objectdetection.utils.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebService {
    private static WebService instance;
    private static Retrofit retrofit;

    private ServiceApi api;

    public WebService() {
        if (retrofit == null) {
            synchronized (Retrofit.class) {
                if (retrofit == null) {
                    Gson gson = new GsonBuilder()
                            .setDateFormat("yyyy-MM-dd' 'HH:mm:ss")
                            .create();
                    retrofit = new Retrofit.Builder().baseUrl("https://google.serper.dev/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(getOkHttpClient())
                            .build();
                }
            }
        }
        api = retrofit.create(ServiceApi.class);
    }

    public static ServiceApi getAPI() {
        if (instance == null) {
            instance = new WebService();
        }
        return instance.api;
    }

    private OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(logging);

        okHttpClientBuilder.addInterceptor(logging);


        return okHttpClientBuilder.build();
    }

    // make retrofit and instance = null
    // to build new instance in the next call
    public static void buildNewInstance() {
        retrofit = null;
        instance = null;
    }

}
