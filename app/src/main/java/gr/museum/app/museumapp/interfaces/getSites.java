package gr.museum.app.museumapp.interfaces;

import java.util.ArrayList;

import gr.museum.app.museumapp.objects.SiteObj;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ChrisVaio on 23/4/2016.
 */
public interface getSites {
    @GET("/museumApiWebApp/museum/sites")
    Observable<ArrayList<SiteObj>> SITES();

}
