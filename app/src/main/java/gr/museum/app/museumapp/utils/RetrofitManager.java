package gr.museum.app.museumapp.utils;

import android.content.Context;

import java.util.ArrayList;

import gr.museum.app.museumapp.interfaces.login;
import gr.museum.app.museumapp.objects.LoginObj;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by barbarosa on 19/4/2016.
 */
public class RetrofitManager {


    private static final String API_URL = "http://192.168.1.15:8080/";
    private static final String TAG = "RetrofitManager";
    private Context context;
    /*private Handler handler;*/
    private Observer observer;

    public RetrofitManager(Context context, Observer observer) {
        this.context = context;
        /*this.handler = handler;*/
        this.observer = observer;
        ServiceGenerator.context = context;
    }


    private void subscribeObservable(Observable call) {
        call.subscribeOn(Schedulers.newThread()) // Create a new Thread
                .observeOn(AndroidSchedulers.mainThread()) // Use the UI thread
                .subscribe(observer);
    }


    public void login(String username,String password) {
        login loginClient = ServiceGenerator.createServiceRxAndroid(login.class, API_URL);


        Observable<LoginObj> call = loginClient.LOGIN(username,password);

      /*  Observable<UserObj> call=editTeamClient.EDIT_PROFILE(userID, token, username, email, mobile, first_name, lastname);*/

        subscribeObservable(call);

    }

}
