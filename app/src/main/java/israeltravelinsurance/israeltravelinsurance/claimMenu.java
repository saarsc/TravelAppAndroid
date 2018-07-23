package israeltravelinsurance.israeltravelinsurance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class claimMenu extends AppCompatActivity {

    Button passportCard;
    Button afenix;
    Button migdal;
    Button arhel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_menu);
        passportCard = findViewById(R.id.passportCard);
        afenix = findViewById(R.id.afenix);
        migdal = findViewById(R.id.Migdal);
        arhel = findViewById(R.id.Arhel);

        passportCard.setOnClickListener( view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.passportcard.co.il/SelfService/Login?ReturnUrl=%2fSelfService"))));
        afenix.setOnClickListener( view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(" https://www.fnx.co.il/sites/docs/polarchive/travelinsurance/300305117.pdf"))));
        migdal.setOnClickListener( view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.migdal.co.il/He/insurance/TravelInsurance/Pages/migdal_medpay.aspx"))));
        arhel.setOnClickListener( view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.harel-group.co.il/Insurance/travel/claim/Pages/abroad-claim-apply.aspx"))));
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,insuranceMenu.class));
    }
}
