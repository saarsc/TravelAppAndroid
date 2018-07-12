package israeltravelinsurance.israeltravelinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class aroundMeMenu extends AppCompatActivity implements View.OnClickListener {
    Button hospital;
    Button habad;
    Button kosher;
    Button attraction;
    Button dentist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_me_menu);

        hospital = findViewById(R.id.nav_hospital);
        habad = findViewById(R.id.nav_habad);
        kosher = findViewById(R.id.nav_kosher_restaurant);
        attraction = findViewById(R.id.nav_attraction);
        dentist= findViewById(R.id.nav_dentist);

        hospital.setOnClickListener(this);
        kosher.setOnClickListener(this);
        habad.setOnClickListener(this);
        attraction.setOnClickListener(this);
        dentist.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
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
        }
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(aroundMeMenu.this,MainActivity.class));
    }
}
