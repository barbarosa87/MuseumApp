package gr.museum.app.museumapp.interfaces;



import gr.museum.app.museumapp.objects.LoginObj;
import gr.museum.app.museumapp.objects.UserObj;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ChrisVaio on 22/4/2016.
 */
public interface SignUp {
    @POST("/museumApiWebApp/museum/signUp")
    @FormUrlEncoded
    Observable<UserObj> SIGN_UP(
            @Field("Name") String name,
            @Field("Surname")String surname,
            @Field("Country") String country,
            @Field("Address")String address,
            @Field("Phone") String phone,
            @Field("Mobilephone")String mobilephone,
            @Field("Email") String email,
            @Field("Username")String username,
            @Field("Password") String password);
}
