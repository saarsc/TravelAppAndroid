package israeltravelinsurance.israeltravelinsurance;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Intent i;
    int myPremmision;
    private static final int FILE_SELECT_CODE = 0;
    Button contactMenu;
    Button emergancyMenu;
    Button aroundMeMenu;
    Button insuranceMenu;
    Button buyInsurance;
    Button whatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Should the request be displayed
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                //request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        myPremmision);
            }

        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                //request the permission.
//                ActivityCompat.requestPermissions(this,
//                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                        myPremmision);
//            }
//        }
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
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
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset)
//            {
//                super.onDrawerSlide(drawerView, slideOffset);
//                drawer.bringChildToFront(drawerView);
//                drawer.requestLayout();
//                drawer.setScrimColor(Color.TRANSPARENT);
//
//            }
//        };
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
//                    Intent hospital = new Intent(MainActivity.this, NearbyPlacesListActivity.class);
//                    hospital.putExtra("place", "hospital");
//                    startActivity(hospital);
//                    break;
//                case R.id.nav_habad:
//                    Intent habad = new Intent(MainActivity.this, NearbyPlacesListActivity.class);
//                    habad.putExtra("place", "בית חבד");
//                    startActivity(habad);
//                    break;
//                case R.id.nav_kosher_restaurant:
//                    Intent kosher = new Intent(MainActivity.this, NearbyPlacesListActivity.class);
//                    kosher.putExtra("place", "מסעדות כשרות");
//                    startActivity(kosher);
//                    break;
//                case R.id.nav_dentist:
//                    Intent dentist = new Intent(MainActivity.this, NearbyPlacesListActivity.class);
//                    dentist.putExtra("place", "dentist");
//                    startActivity(dentist);
//                    break;
//                case R.id.nav_attraction:
//                    Intent attraction = new Intent(MainActivity.this, NearbyPlacesListActivity.class);
//                    attraction.putExtra("place", "attraction");
//                    startActivity(attraction);
//                    break;
//            }
//            return false;
//        });


        contactMenu = findViewById(R.id.contactMenu);
        contactMenu.setOnClickListener(view -> startActivity(new Intent(this,contactMenu.class)));

        emergancyMenu = findViewById(R.id.emergancyMenu);
        emergancyMenu.setOnClickListener(view -> startActivity(new Intent(this,emergancyMenu.class)));

        aroundMeMenu= findViewById(R.id.aroundMeMenu);
        aroundMeMenu.setOnClickListener(view -> startActivity(new Intent(this,aroundMeMenu.class)));

        insuranceMenu = findViewById(R.id.insuranceMenu);
        insuranceMenu.setOnClickListener(view -> startActivity(new Intent(this,insuranceMenu.class)));

        buyInsurance = findViewById(R.id.site);
        buyInsurance.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://israeltravelinsurance.co.il"))));

        whatsapp = findViewById(R.id.whatsapp);
        whatsapp.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse( "https://api.whatsapp.com/send?phone=+9720505255785"))));
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

    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//            drawer.setVisibility(View.GONE);
//        } else {
//        }
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }  else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}