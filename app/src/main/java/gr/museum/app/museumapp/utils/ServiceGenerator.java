package gr.museum.app.museumapp.utils;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ilias.karditsis on 19/10/2015.
 */
public class ServiceGenerator {

    private static long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MB
    public static Context context;

    // No need to instantiate this class.
    private ServiceGenerator() {
    }
  /*  private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }*/

    public static <S> S createServiceRxAndroid(Class<S> serviceClass, String baseUrl) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

//        client.setConnectTimeout(30, TimeUnit.SECONDS);
        //      client.setReadTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


     /*   RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setClient(new OkClient(client));

        RestAdapter adapter = builder.build();*/

        return retrofit.create(serviceClass);
    }



   /* public static <S> S createServiceNonCached(Class<S> serviceClass, String baseUrl) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

//        client.setConnectTimeout(30, TimeUnit.SECONDS);
  //      client.setReadTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


     *//*   RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setClient(new OkClient(client));

        RestAdapter adapter = builder.build();*//*

        return retrofit.create(serviceClass);
    }*/




}
