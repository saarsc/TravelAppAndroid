package israeltravelinsurance.israeltravelinsurance;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class emergancyMenu extends AppCompatActivity implements View.OnClickListener {

    Button police;
    Button ambulnce;
    Button fire;

    int myPremmision;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergancy_menu);

        police = findViewById(R.id.police);
        ambulnce = findViewById(R.id.ambulance);
        fire = findViewById(R.id.fire);

        police.setOnClickListener(this);
        ambulnce.setOnClickListener(this);
        fire.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.police:
                makeEmergenceCall();
                break;
            case R.id.ambulance:
                makeEmergenceCall();
                break;
            case R.id.fire:
                makeEmergenceCall();
                break;
        }

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
}
