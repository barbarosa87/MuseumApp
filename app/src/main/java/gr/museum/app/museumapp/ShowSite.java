package gr.museum.app.museumapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import java.util.ArrayList;

import gr.museum.app.museumapp.adapters.SitePhotoViewPagerAdapter;
import gr.museum.app.museumapp.objects.SiteObj;

/**
 * Created by ChrisVaio on 24/4/2016.
 */
public class ShowSite extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_museum);
        SiteObj museum = (SiteObj) getIntent().getSerializableExtra("key");
        ArrayList<String> sitePhoto = new ArrayList<String>();
        sitePhoto.add(museum.getPicture1());
        sitePhoto.add(museum.getPicture2());
        sitePhoto.add(museum.getPicture3());



        ViewPager viewpager = (ViewPager) findViewById(R.id.imageViewMuseum);
        WebView webview=(WebView) findViewById(R.id.webViewInformation);
        /*TextView textView = (TextView) findViewById(R.id.textViewMuseumInfo);*/

        SitePhotoViewPagerAdapter sitePhotoViewPagerAdapter = new SitePhotoViewPagerAdapter(getApplicationContext(), sitePhoto);
        viewpager.setAdapter(sitePhotoViewPagerAdapter);
        /*textView.setText(museum.getInformation());*/
        webview.loadData(museum.getInformation(),"text/html",null);

    }
}
