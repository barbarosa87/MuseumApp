package gr.museum.app.museumapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import gr.museum.app.museumapp.objects.MuseumObj;

/**
 * Created by ChrisVaio on 24/4/2016.
 */
public class ShowMuseum extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_museum);
        MuseumObj museum = (MuseumObj) getIntent().getSerializableExtra("key");

        ImageView imageView = (ImageView) findViewById(R.id.imageViewMuseum);
        WebView webview=(WebView) findViewById(R.id.webViewInformation);
        /*TextView textView = (TextView) findViewById(R.id.textViewMuseumInfo);*/

        Glide.with(getApplicationContext()).load(museum.getPicture1()).into(imageView);
        /*textView.setText(museum.getInformation());*/
        webview.loadData(museum.getInformation(),"text/html",null);

    }
}
