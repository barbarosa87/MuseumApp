package gr.museum.app.museumapp;

import android.Manifest;
import android.Manifest.permission;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.SystemRequirementsChecker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import gr.museum.app.museumapp.adapters.SiteRecyclerViewAdapter;
import gr.museum.app.museumapp.objects.SiteObj;
import gr.museum.app.museumapp.utils.RetrofitManager;
import gr.museum.app.museumapp.utils.Statics;
import rx.Observer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback,
        com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public ArrayList<SiteObj> storedSites = new ArrayList<SiteObj>();
    private RecyclerView recyclerView;
    private SiteRecyclerViewAdapter adapter;
    private MyApplication app;
    private GoogleMap map;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    boolean bPermissionGranted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (MyApplication) getApplication();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.mainRecycler);

        adapter = new SiteRecyclerViewAdapter(new ArrayList<SiteObj>(), getApplicationContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new jp.wasabeef.recyclerview.animators.SlideInUpAnimator());
        recyclerView.setAdapter(adapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        EstimoteSDK.initialize(getApplicationContext(), getResources().getString(R.string.estimote_appID), getResources().getString(R.string.estimote_token));

        //Initialize Google Play Services


    }


    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            app.logOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.all) {
            ArrayList<SiteObj> siteObjArrayList = new ArrayList<>();
            for (int i = 0; i < storedSites.size(); i++) {
                siteObjArrayList.add(storedSites.get(i));
            }
            adapter = new SiteRecyclerViewAdapter(siteObjArrayList, getApplicationContext());
            recyclerView.setAdapter(adapter);
            setMapMarkers(siteObjArrayList, map);


        } else if (id == R.id.museums) {
            ArrayList<SiteObj> siteObjArrayList = new ArrayList<>();
            for (int i = 0; i < storedSites.size(); i++) {
                if (storedSites.get(i).getType().equalsIgnoreCase("museum")) {
                    siteObjArrayList.add(storedSites.get(i));
                }
            }
            adapter = new SiteRecyclerViewAdapter(siteObjArrayList, getApplicationContext());
            recyclerView.setAdapter(adapter);
            setMapMarkers(siteObjArrayList, map);
        } else if (id == R.id.archaelogiclaSites) {
            ArrayList<SiteObj> siteObjArrayList = new ArrayList<>();
            for (int i = 0; i < storedSites.size(); i++) {
                if (storedSites.get(i).getType().equalsIgnoreCase("archaeological_site")) {
                    siteObjArrayList.add(storedSites.get(i));
                }
            }
            adapter = new SiteRecyclerViewAdapter(siteObjArrayList, getApplicationContext());
            recyclerView.setAdapter(adapter);
            setMapMarkers(siteObjArrayList, map);
        } else if (id == R.id.showFavoritesDrawer) {
            adapter = new SiteRecyclerViewAdapter(Statics.loadAllFavoritesNew(getApplicationContext()), getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getMuseums(final GoogleMap map) {
        final Observer<ArrayList<SiteObj>> MuseumObjObserver = new Observer<ArrayList<SiteObj>>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ArrayList<SiteObj> siteObjArrayList) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                storedSites = siteObjArrayList;





                for (SiteObj siteObj : storedSites) {
                    MarkerOptions options = (new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(siteObj.getLatitude()), Double.parseDouble(siteObj.getLongitude())))
                            .title(siteObj.getName().toString()));
                    map.addMarker(options);
                    builder.include(options.getPosition());
                    adapter.insert(siteObj, adapter.getItemCount());
                }
                LatLngBounds bounds = builder.build();
                int padding = 0;
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
                map.moveCamera(cu);
                map.animateCamera(zoom);


                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    bPermissionGranted = checkLocationPermission();

                    if (bPermissionGranted) {
                        enableMyLocation();
                    }
                } else {
                    enableMyLocation();
                }
              /*  SiteRecyclerViewAdapter adapter=new SiteRecyclerViewAdapter(storedSites,getApplicationContext());
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);*/

            }
        };
        new RetrofitManager(getApplicationContext(), MuseumObjObserver).getMuseums();
    }

    private void setMapMarkers(ArrayList<SiteObj> siteObjArrayList, GoogleMap map) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        map.clear();
        for (SiteObj siteObj : siteObjArrayList) {
            MarkerOptions options = (new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(siteObj.getLatitude()), Double.parseDouble(siteObj.getLongitude())))
                    .title(siteObj.getName().toString()));
            map.addMarker(options);
            builder.include(options.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 0;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(11);
        map.moveCamera(cu);
        map.animateCamera(zoom);
    }

    private double calculateDistance(double userlatitude, double userlongitude, double markerlatitude, double markerLongitude) {
        int R = 6371; // km
        double x = (markerLongitude - userlongitude) * Math.cos((userlatitude + markerlatitude) / 2);
        double y = (markerlatitude - userlatitude);
        double distance = Math.sqrt(x * x + y * y) * R;
        return distance;
    }

    /*private ArrayList<SiteObj> shortList(ArrayList<SiteObj> ){

    }*/

    @Override
    public void onMapReady(GoogleMap map) {
        MainActivity.this.map = map;





        getMuseums(map);


    }

    private void enableMyLocation() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(true);
        }

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
      /*  LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = map.addMarker(markerOptions);
*/
        //move map camera
       /* map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(11));*/

        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        //Collections.sort(storedSites,location.distanceBetween(mLastLocation.getLatitude(),mLastLocation.getLongitude(),););
        Collections.sort(storedSites, new Comparator<SiteObj>() {
            @Override
            public int compare(SiteObj lhs, SiteObj rhs) {

                float[] resultsLeft = new float[3];
                Location.distanceBetween(Double.valueOf(lhs.getLatitude()), Double.valueOf(lhs.getLongitude()), mLastLocation.getLatitude(), mLastLocation.getLongitude(), resultsLeft);
                float[] resultsRight = new float[3];
                Location.distanceBetween(Double.valueOf(rhs.getLatitude()), Double.valueOf(rhs.getLongitude()), mLastLocation.getLatitude(), mLastLocation.getLongitude(), resultsRight);
                if (resultsLeft[0] > resultsRight[0]) {
                    return 1;
                } else {
                    return -1;
                }


            }
        });

        adapter = new SiteRecyclerViewAdapter(storedSites, getApplicationContext());
        recyclerView.setAdapter(adapter);



    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


        }else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
           /* if(LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)!=null){
                Collections.sort(storedSites, new Comparator<SiteObj>() {
                    @Override
                    public int compare(SiteObj lhs, SiteObj rhs) {

                        float[] resultsLeft = new float[3];
                        Location.distanceBetween(Double.valueOf(lhs.getLatitude()), Double.valueOf(lhs.getLongitude()), mLastLocation.getLatitude(), mLastLocation.getLongitude(), resultsLeft);
                        float[] resultsRight = new float[3];
                        Location.distanceBetween(Double.valueOf(rhs.getLatitude()), Double.valueOf(rhs.getLongitude()), mLastLocation.getLatitude(), mLastLocation.getLongitude(), resultsRight);
                        if (resultsLeft[0] > resultsRight[0]) {
                            return -1;
                        } else {
                            return +1;
                        }


                    }
                });

                adapter = new SiteRecyclerViewAdapter(storedSites, getApplicationContext());
                recyclerView.setAdapter(adapter);*/
            }
        }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}