package gr.museum.app.museumapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import gr.museum.app.museumapp.objects.UserObj;
import gr.museum.app.museumapp.utils.RetrofitManager;
import rx.Observer;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SignUpActivity extends AppCompatActivity {

    EditText nameTxt;
    EditText surnameTxt;
    EditText countryTxt;
    EditText addressTxt;
    EditText phoneTxt;
    EditText mobilePhoneTxt;
    EditText emailTxt;
    EditText usernameTxt;
    EditText passwordTxt;
    Button signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        nameTxt=(EditText)findViewById(R.id.nameTxt);
        surnameTxt=(EditText)findViewById(R.id.surnameTxt);
        countryTxt=(EditText)findViewById(R.id.countryTxt);
        addressTxt=(EditText)findViewById(R.id.addressTxt);
        phoneTxt=(EditText)findViewById(R.id.phoneTxt);
        mobilePhoneTxt=(EditText)findViewById(R.id.mobilePhoneTxt);
        emailTxt=(EditText)findViewById(R.id.emailTxt);
        usernameTxt=(EditText)findViewById(R.id.usernameTxt);
        passwordTxt=(EditText)findViewById(R.id.passwordTxt);
        signupBtn=(Button)findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


    }

    private void signUp() {
        Observer<UserObj> UserObjObserver =new Observer<UserObj>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(UserObj userObj) {

            }
        };
        new RetrofitManager(getApplicationContext(),UserObjObserver).signUp(nameTxt.getText().toString(),
                surnameTxt.getText().toString(),
                countryTxt.getText().toString(),
                addressTxt.getText().toString(),
                phoneTxt.getText().toString(),
                mobilePhoneTxt.getText().toString(),
                emailTxt.getText().toString(),
                usernameTxt.getText().toString(),
                passwordTxt.getText().toString());
    }


}
