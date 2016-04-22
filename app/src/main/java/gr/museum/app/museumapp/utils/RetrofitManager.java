package gr.museum.app.museumapp.utils;

import android.content.Context;

import java.util.ArrayList;
import java.util.StringTokenizer;

import gr.museum.app.museumapp.interfaces.SignUp;
import gr.museum.app.museumapp.interfaces.login;
import gr.museum.app.museumapp.objects.LoginObj;
import gr.museum.app.museumapp.objects.UserObj;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by barbarosa on 19/4/2016.
 */
public class RetrofitManager {


    private static final String API_URL = "192.168.1.6:8080/";
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

    public void signUp(String name, String surname, String country, String address, String phone, String mobilephone, String email, String username, String password) {
        SignUp signUpClient = ServiceGenerator.createServiceRxAndroid(SignUp.class, API_URL);


        Observable<UserObj> call = signUpClient.SIGN_UP(name, surname, country, address, phone, mobilephone, email, username, password);

      /*  Observable<UserObj> call=editTeamClient.EDIT_PROFILE(userID, token, username, email, mobile, first_name, lastname);*/

        subscribeObservable(call);

    }

}
