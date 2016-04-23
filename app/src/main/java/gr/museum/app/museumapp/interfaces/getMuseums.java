package gr.museum.app.museumapp.interfaces;

import java.util.ArrayList;

import gr.museum.app.museumapp.objects.MuseumObj;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ChrisVaio on 23/4/2016.
 */
public interface getMuseums {
    @GET("/museumApiWebApp/museum/museums")
    Observable<ArrayList<MuseumObj>> MUSEUMS();

}
