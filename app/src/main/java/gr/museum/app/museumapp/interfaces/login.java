package gr.museum.app.museumapp.interfaces;

import java.util.ArrayList;

import gr.museum.app.museumapp.objects.LoginObj;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by barbarosa on 19/4/2016.
 */
public interface login {
    @POST("/museumApiWebApp/museum/login")
    @FormUrlEncoded
    Observable<LoginObj> LOGIN(
            @Field("userName") String username,
            @Field("password")String password);
}
