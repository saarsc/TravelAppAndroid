package israeltravelinsurance.israeltravelinsurance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class aroundMeMenu extends AppCompatActivity implements View.OnClickListener {
    Button hospital;
    Button habad;
    Button kosher;
    Button attraction;
    Button dentist;
    Button ATM;

    int myPremmision;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_me_menu);

        hospital = findViewById(R.id.nav_hospital);
        habad = findViewById(R.id.nav_habad);
        kosher = findViewById(R.id.nav_kosher_restaurant);
        attraction = findViewById(R.id.nav_attraction);
        dentist= findViewById(R.id.nav_dentist);
        ATM= findViewById(R.id.nav_ATM);

        hospital.setOnClickListener(this);
        kosher.setOnClickListener(this);
        habad.setOnClickListener(this);
        attraction.setOnClickListener(this);
        dentist.setOnClickListener(this);
        ATM.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Should the request be displayed
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                //request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        myPremmision);
            }

        }
//        if
    }

    @Override
    public void onClick(View view) {
        if(!isPermissionGranted()){
            startActivity(new Intent(this, MainActivity.class));
        }
        switch (view.getId()) {
            case R.id.nav_hospital:
                Intent hospital = new Intent(aroundMeMenu.this, NearbyPlacesListActivity.class);
                hospital.putExtra("place", "hospital");
                startActivity(hospital);
                break;
            case R.id.nav_habad:
                Intent habad = new Intent(aroundMeMenu.this, NearbyPlacesListActivity.class);
                habad.putExtra("place", "בית חבד");
                startActivity(habad);
                break;
            case R.id.nav_kosher_restaurant:
                Intent kosher = new Intent(aroundMeMenu.this, NearbyPlacesListActivity.class);
                kosher.putExtra("place", "מסעדות כשרות");
                startActivity(kosher);
                break;
            case R.id.nav_dentist:
                Intent dentist = new Intent(aroundMeMenu.this, NearbyPlacesListActivity.class);
                dentist.putExtra("place", "dentist");
                startActivity(dentist);
                break;
            case R.id.nav_attraction:
                Intent attraction = new Intent(aroundMeMenu.this, NearbyPlacesListActivity.class);
                attraction.putExtra("place", "attraction");
                startActivity(attraction);
                break;
            case R.id.nav_ATM:
                Intent ATM = new Intent(aroundMeMenu.this, NearbyPlacesListActivity.class);
                ATM.putExtra("place", "ATM");
                startActivity(ATM);
                break;
        }
    }
    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onBackPressed(){
        startActivity(new Intent(aroundMeMenu.this,MainActivity.class));
    }


}
