package israeltravelinsurance.israeltravelinsurance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * An activity representing a list of NearbyPlaces. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link NearbyPlacesDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class NearbyPlacesListActivity extends AppCompatActivity implements
        LocationListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */    Intent i;
    int myPremmision;
    private static final int FILE_SELECT_CODE = 0;

    private boolean mTwoPane;

    LocationManager locationManager;
    AppController appController = new AppController();
    static ArrayList<NearbyPlaces> nearbyPlaces = new ArrayList<>();
    RecyclerView recyclerView;

    static String type;
    SimpleItemRecyclerViewAdapter listAdapter;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearbyplaces_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
//        //Fab
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> {
//            i = new Intent(Intent.ACTION_SEND);
//            i.setType("message/rfc822");
//            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@israeltravelinsurance.co.il"});
//            i.putExtra(Intent.EXTRA_SUBJECT, "הודעה הנשלחה מתוך האפליקצייה");
//            i.putExtra(Intent.EXTRA_TEXT, "");
//            try {
//                startActivity(Intent.createChooser(i, "שלח מייל..."));
//            } catch (android.content.ActivityNotFoundException ex) {
//                Snackbar.make(findViewById(R.id.fab), "אנא התקן האפליקצייה התומכת בשליחת מיילים", Snackbar.LENGTH_SHORT).show();
//            }
//        });
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        //Navbar items
//        navigationView.setNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//
//                case R.id.nav_send_file:
//                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                    intent.setType("*/*");
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    try {
//                        startActivityForResult(Intent.createChooser(intent, "בחר קובץ"), FILE_SELECT_CODE);
//                    } catch (android.content.ActivityNotFoundException ex) {
//                        // Potentially direct the user to the Market with a Dialog
//                        Snackbar.make(findViewById(R.id.fab), "אנא התקן האפליקצייה התומכת בהצגת קבצים", Snackbar.LENGTH_SHORT).show();
//                    }
//                    break;
//                case R.id.nav_police:
//                    makeEmergenceCall();
//                    break;
//                case R.id.nav_ambulance:
//                    makeEmergenceCall();
//                    break;
//                case R.id.nav_firetruck:
//                    makeEmergenceCall();
//                    break;
//                case R.id.nav_hospital:
//                    Intent hospital = new Intent(NearbyPlacesListActivity.this, NearbyPlacesListActivity.class);
//                    hospital.putExtra("place", "hospital");
//                    startActivity(hospital);
//                    break;
//                case R.id.nav_habad:
//                    Intent habad = new Intent(NearbyPlacesListActivity.this, NearbyPlacesListActivity.class);
//                    habad.putExtra("place", "בית חבד");
//                    startActivity(habad);
//                    break;
//                case R.id.nav_kosher_restaurant:
//                    Intent kosher = new Intent(NearbyPlacesListActivity.this, NearbyPlacesListActivity.class);
//                    kosher.putExtra("place", "מסעדות כשרות");
//                    startActivity(kosher);
//                    break;
//                case R.id.nav_dentist:
//                    Intent dentist = new Intent(NearbyPlacesListActivity.this, NearbyPlacesListActivity.class);
//                    dentist.putExtra("place", "dentist");
//                    startActivity(dentist);
//                    break;
//                case R.id.nav_attraction:
//                    Intent attraction = new Intent(NearbyPlacesListActivity.this, NearbyPlacesListActivity.class);
//                    attraction.putExtra("place", "attraction");
//                    startActivity(attraction);
//                    break;
//            }
//            return false;
//        });
        recyclerView = findViewById(R.id.nearbyplaces_list);

        if (!isGooglePlayServicesAvailable()) {
            return;
        }
        if (findViewById(R.id.nearbyplaces_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.nearbyplaces_list);
        assert recyclerView != null;
//        setupRecyclerView((RecyclerView) recyclerView);
    }
    private void makeEmergenceCall() {
        Uri callUri = Uri.parse("tel://112");
        Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    myPremmision);
        }else {
            startActivity(callIntent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("File URI: ","File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    path = getPath(this, uri);
                    Log.d("File path: ", "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                    Intent sendEmail = new Intent(Intent.ACTION_SEND);
                    sendEmail.setType("vnd.android.cursor.dir/email");
                    String to[] = {"info@israeltravelinsurance.co.il"};
                    sendEmail.putExtra(Intent.EXTRA_EMAIL, to);
                    sendEmail.putExtra(Intent.EXTRA_STREAM, uri);
                    // the mail subject
                    sendEmail .putExtra(Intent.EXTRA_SUBJECT, "קובץ");
                    startActivity(Intent.createChooser(sendEmail , "שלח מייל..."));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getPath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    //Location Settings are off
    private void showLocationSettings() {
        Snackbar snackbar = Snackbar
                .make(recyclerView, "Location Error: GPS Disabled!",
                        Snackbar.LENGTH_LONG)
                .setAction("Enable", v -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)));
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = sbView
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
        type = getIntent().getStringExtra("place");
        StringBuilder googlePlacesUrl =
                new StringBuilder();
        if (type.equals("hospital") || type.equals("dentist") || type.equals("attraction")) {
            googlePlacesUrl.append("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            googlePlacesUrl.append("types=").append(type);
            googlePlacesUrl.append("&location=").append(latitude).append(",").append(longitude);
            googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
            googlePlacesUrl.append("&sensor=true");
        } else {
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

        AppController.getInstance().addToRequestQueue(request, getApplicationContext());
//YOU Can change this type at your own will, e.g hospital, cafe, restaurant.... and see how it all works
    }

    //Parsing Json from Google Maps API to
    private void parseLocationResult(JSONObject jsonResult) {
        String place_id;
        try {
            JSONArray jsonArray;
            if (getIntent().getStringExtra("place").equals("hospital") || getIntent().getStringExtra("place").equals("dentist") || getIntent().getStringExtra("place").equals("attraction")) {
                jsonArray = jsonResult.getJSONArray("results");
            } else {
                jsonArray = jsonResult.getJSONArray("candidates");
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

                    AppController.getInstance().addToRequestQueue(request, getApplicationContext());
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
        String placeName = null, address = "", phone = "";
        double latitude, longitude, rating = 0;
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
                if (!place.isNull(PHONE)) {
                    phone = place.getString(PHONE);
                }
                NearbyPlaces newPlace = new NearbyPlaces(latitude, longitude, rating, placeName, address, openNow, workHours, phone);
                nearbyPlaces.add(newPlace);

            }
        } catch (JSONException e1) {
            e1.printStackTrace();
            Log.e(TAG, "parseLocationResult: Error=" + e1.getMessage());
        }

    }

    private void setRecycleView() {
        listAdapter =new SimpleItemRecyclerViewAdapter(this, nearbyPlaces, mTwoPane);
        recyclerView.setAdapter(listAdapter);
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

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();
        if(listAdapter != null) {
            nearbyPlaces.clear();
            listAdapter.notifyDataSetChanged();
        }
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
//    @Override
//    public void onStart() {
//        super.onStart();
//        nearbyPlaces.clear();
//                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            showLocationSettings();
//        }
//        //Get the current user location
//        Location location = getLastKnownLocation();
//
//        if (location != null) {
//            onLocationChanged(location);
//        }
//        Criteria criteria = new Criteria();
//        String bestProvider = locationManager.getBestProvider(criteria, true);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(bestProvider, MIN_TIME_BW_UPDATES,
//                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//    }
    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final NearbyPlacesListActivity mParentActivity;
        private final List<NearbyPlaces> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NearbyPlaces item = (NearbyPlaces) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_NAME, item.getName());
                    arguments.putDouble(NearbyPlacesDetailFragment.ARG_ITEM_LAT,item.getLat());
                    arguments.putDouble(NearbyPlacesDetailFragment.ARG_ITEM_LANG,item.getLang());
                    arguments.putDouble(NearbyPlacesDetailFragment.ARG_ITEM_RATING,Double.parseDouble(item.getRating()));
                    arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_ADDRESS,item.getAddress());
                    arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_ISOPEN,item.isOpenNow());
                    arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_WORK_HOURS,item.getWorkHours());
                    arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_PHONE_NUMBER,item.getPhoneNumber());
                    NearbyPlacesDetailFragment fragment = new NearbyPlacesDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nearbyplaces_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, NearbyPlacesDetailActivity.class);
                    intent.putExtra(NearbyPlacesDetailFragment.ARG_ITEM_NAME, item.getName());
                    intent.putExtra(NearbyPlacesDetailFragment.ARG_ITEM_LAT,item.getLat());
                    intent.putExtra(NearbyPlacesDetailFragment.ARG_ITEM_LANG,item.getLang());
                    intent.putExtra(NearbyPlacesDetailFragment.ARG_ITEM_RATING,item.getRating());
                    intent.putExtra(NearbyPlacesDetailFragment.ARG_ITEM_ADDRESS,item.getAddress());
                    intent.putExtra(NearbyPlacesDetailFragment.ARG_ITEM_ISOPEN,item.isOpenNow());
                    intent.putExtra(NearbyPlacesDetailFragment.ARG_ITEM_WORK_HOURS,item.getWorkHours());
                    intent.putExtra(NearbyPlacesDetailFragment.ARG_ITEM_PHONE_NUMBER,item.getPhoneNumber());
                    intent.putExtra("type",type);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(NearbyPlacesListActivity parent,
                                      List<NearbyPlaces> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.nearbyplaces_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(String.valueOf(position +1 ));
            holder.mContentView.setText(mValues.get(position).getName());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
            }
        }
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(NearbyPlacesListActivity.this,aroundMeMenu.class));
    }
}
