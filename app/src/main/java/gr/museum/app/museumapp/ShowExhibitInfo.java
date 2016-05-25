package gr.museum.app.museumapp;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.BeaconManager.ServiceReadyCallback;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

import gr.museum.app.museumapp.adapters.ExhibitPhotoViewPagerAdapter;
import gr.museum.app.museumapp.objects.ExhibitObj;
import gr.museum.app.museumapp.objects.SiteObj;

public class ShowExhibitInfo extends AppCompatActivity implements BeaconManager.MonitoringListener, BeaconManager.RangingListener {
    private BeaconManager beaconManager;
    SiteObj siteObj;
    //ExhibitObj showingExhibitObj=new ExhibitObj();
    int showingExchibitID = -1;
    private MediaPlayer mMediaPlayer;
    private MediaController mcontroller;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exhibit_info);
        siteObj = (SiteObj) getIntent().getSerializableExtra("key");

        TextView textView = (TextView) findViewById(R.id.welcomeMessage);
        textView.setText("Welcome to " + siteObj.getName() + "\nCome closer to the exhibit to see more information");
        //  showingExhibitObj.setId(-1);
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.connect(new ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(new Region(
                        siteObj.getName(),
                        UUID.fromString(siteObj.getBeacon_uuid()),
                        null, null));
            }
        });


        beaconManager.setRangingListener(this);


        beaconManager.setBackgroundScanPeriod(1000, 1000);

    }

    @Override
    public void onEnteredRegion(Region region, List<Beacon> list) {
        /*TextView textView = (TextView) findViewById(R.id.welcomeMessage);
        textView.setText("Beacon" + list.get(0).getMinor());*/


    }


    @Override
    protected void onPause() {
        showingExchibitID=-1;
        super.onPause();
    }

    @Override
    public void onExitedRegion(Region region) {
        TextView textView = (TextView) findViewById(R.id.welcomeMessage);
        textView.setText("Welcome to " + siteObj.getName() + "\nCome closer to the exhibit to see more information");
    }


    @Override
    public void onBeaconsDiscovered(Region region, List<Beacon> list) {
        TextView textView = (TextView) findViewById(R.id.welcomeMessage);
        textView.setVisibility(View.GONE);


        if (!list.isEmpty()) {
            Beacon nearestBeacon = list.get(0);
            ExhibitObj exhibitObj = getNearestExchibit(nearestBeacon);
            if (showingExchibitID != exhibitObj.getId()) {
                showingExchibitID = exhibitObj.getId();
                ViewPager viewpager = (ViewPager) findViewById(R.id.imageViewExhibit);
                WebView webview = (WebView) findViewById(R.id.webViewInformation);
                VideoView videoView = (VideoView) findViewById(R.id.videoView);
                videoView.setVisibility(View.GONE);
        /*TextView textView = (TextView) findViewById(R.id.textViewMuseumInfo);*/

                ExhibitPhotoViewPagerAdapter exhibitPhotoViewPagerAdapter = new ExhibitPhotoViewPagerAdapter(getApplicationContext(), exhibitObj.getImageExchibitContents());
                viewpager.setAdapter(exhibitPhotoViewPagerAdapter);
                if (exhibitObj.getVideoExhibitContentObj().size()>0){
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoPath(String.valueOf(exhibitObj.getVideoExhibitContentObj().get(0).getPath()));
                    MediaController mediaController = new
                            MediaController(this);
                    mediaController.setAnchorView(videoView);
                    videoView.setMediaController(mediaController);
                    videoView.start();
                }
        /*textView.setText(siteObj.getInformation());*/
                webview.loadData(exhibitObj.getInformation(), "text/html", null);
            }
        }
    }


    private ExhibitObj getNearestExchibit(Beacon beacon) {
        for (ExhibitObj exhibitObj : siteObj.getExhibits()) {
            if (exhibitObj.getBeacon_minor().equals(String.valueOf(beacon.getMinor())) && exhibitObj.getBeacon_major().equals(String.valueOf(beacon.getMajor()))) {
                return exhibitObj;
            }
        }

        return null;
    }


}
