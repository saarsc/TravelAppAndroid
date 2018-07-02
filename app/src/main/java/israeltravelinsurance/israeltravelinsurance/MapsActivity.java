package israeltravelinsurance.israeltravelinsurance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static israeltravelinsurance.israeltravelinsurance.AppConfig.FORMATTED_ADDRESS;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.GEOMETRY;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.GOOGLE_BROWSER_API_KEY;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.LATITUDE;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.LOCATION;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.LONGITUDE;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.MIN_DISTANCE_CHANGE_FOR_UPDATES;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.MIN_TIME_BW_UPDATES;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.NAME;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.OK;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.OPEING_HOURS;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.OPEN_NOW;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.PHONE;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.PLACE_ID;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.PLAY_SERVICES_RESOLUTION_REQUEST;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.PROXIMITY_RADIUS;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.RATING;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.STATUS;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.TAG;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.WEEKDAY_TEXT;
import static israeltravelinsurance.israeltravelinsurance.AppConfig.ZERO_RESULTS;


public class MapsActivity extends AppCompatActivity implements
        LocationListener {

    LocationManager locationManager;
    JSONObject oneLocation;
    AppController appController = new AppController();
    static ArrayList<NearbyPlaces>  nearbyPlaces = new ArrayList<>();

    ListView placeView;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isGooglePlayServicesAvailable()) {
            return;
        }
        setContentView(R.layout.activity_maps);

        placeView = findViewById(R.id.placesList);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showLocationSettings();
        }
        //Get the current user location
        Location location = getLastKnownLocation();

        if (location != null) {
            onLocationChanged(location);
        }
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(bestProvider, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    //Location Settings are off
    private void showLocationSettings() {
        Snackbar snackbar = Snackbar
                .make(placeView, "Location Error: GPS Disabled!",
                        Snackbar.LENGTH_LONG)
                .setAction("Enable", v -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    @SuppressWarnings("MissingPermission")
    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() > bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    private void loadNearByPlaces(double latitude, double longitude) {
        nearbyPlaces.clear();
        String type = getIntent().getStringExtra("place");
        StringBuilder googlePlacesUrl =
                new StringBuilder();
        if(type.equals("hospital")){
            googlePlacesUrl.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            googlePlacesUrl.append("types=").append(type);
            googlePlacesUrl.append("&location=").append(latitude).append(",").append(longitude);
            googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
            googlePlacesUrl.append("&sensor=true");
        }else{
            googlePlacesUrl.append("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?");
            googlePlacesUrl.append("input=").append(type).append("&inputtype=textquery");
            googlePlacesUrl.append("&fields=place_id");
            googlePlacesUrl.append("&locationbias=circle:").append(PROXIMITY_RADIUS).append("@").append(latitude).append(",").append(longitude);
        }
        googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);
        googlePlacesUrl.append("&language=iw");

        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(),
                result -> {

                    Log.i(TAG, "onResponse: Result= " + result.toString());
                    parseLocationResult(result);
                },
                error -> {
                    Log.e(TAG, "onErrorResponse: Error= " + error);
                    Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                });

        appController.getInstance().addToRequestQueue(request, getApplicationContext());
//YOU Can change this type at your own will, e.g hospital, cafe, restaurant.... and see how it all works
    }

    //Parsing Json from Google Maps API to
    private void parseLocationResult(JSONObject jsonResult) {
        String  place_id;
        try {
            JSONArray jsonArray;
            if(getIntent().getStringExtra("place").equals("hospital")) {
                 jsonArray = jsonResult.getJSONArray("results");
            }else{
                jsonArray =  jsonResult.getJSONArray("candidates");
            }
            if (jsonResult.getString(STATUS).equalsIgnoreCase(OK)) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject placeID = jsonArray.getJSONObject(i);
                    place_id = placeID.getString(PLACE_ID);
                    StringBuilder googlePlacesUrl =
                            new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json?");
                    googlePlacesUrl.append("placeid=").append(place_id);
                    googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);
                    googlePlacesUrl.append("&language=iw");
                    JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(),
                            result -> {

                                Log.i(TAG, "onResponse: Result= " + result.toString());
                                processResult(result);
                                setRecycleView();
                            },
                            error -> {
                                Log.e(TAG, "onErrorResponse: Error= " + error);
                                Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                            });

                    appController.getInstance().addToRequestQueue(request, getApplicationContext());
                }

//                Toast.makeText(getBaseContext(), jsonArray.length() + " Supermarkets found!",Toast.LENGTH_LONG).show();
            } else if (jsonResult.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                Toast.makeText(getBaseContext(), "No Supermarket found in 5KM radius!!!",
                        Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }

    }
    private synchronized void processResult(JSONObject result) {
            String placeName = null, address ="", phone = "";
            double latitude, longitude,rating =0;
            boolean openNow = true;
            JSONArray workHours = new JSONArray();
            try {
                JSONObject place = result.getJSONObject("result");
                if (result.getString(STATUS).equalsIgnoreCase(OK)) {
                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LATITUDE);
                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)
                            .getDouble(LONGITUDE);
                    if (!place.isNull(RATING)) {
                        rating = place.getDouble(RATING);
                    }
                    if (!place.isNull(NAME)) {
                        placeName = place.getString(NAME);
                    }
                    if (!place.isNull(FORMATTED_ADDRESS)) {
                        address = place.getString(FORMATTED_ADDRESS);
                    }
                    if (!place.isNull(OPEING_HOURS)) {
                        openNow = place.getJSONObject(OPEING_HOURS).getBoolean(OPEN_NOW);
                        workHours = place.getJSONObject(OPEING_HOURS).getJSONArray(WEEKDAY_TEXT);
                    }
                    if(!place.isNull(PHONE)){
                        phone = place.getString(PHONE);
                    }
                    NearbyPlaces newPlace = new NearbyPlaces(latitude, longitude, rating, placeName, address, openNow, workHours,phone);
                    nearbyPlaces.add(newPlace);

                }
            } catch (JSONException e1) {
                e1.printStackTrace();
                Log.e(TAG, "parseLocationResult: Error=" + e1.getMessage());
            }

    }

    private void setRecycleView() {
        PlaceAdpter placeAdpter =new PlaceAdpter(this,0,nearbyPlaces);
        placeView.setAdapter(placeAdpter);
    }
    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        loadNearByPlaces(latitude, longitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}