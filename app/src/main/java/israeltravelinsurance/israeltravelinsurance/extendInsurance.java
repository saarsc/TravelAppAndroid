package israeltravelinsurance.israeltravelinsurance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class extendInsurance extends AppCompatActivity {
    Button passportCard;
    Button afenix;
    Button migdal;
    Button arhel;
    Button menora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extend_insurance);

        passportCard = findViewById(R.id.passportCard);
        afenix = findViewById(R.id.afenix);
        migdal = findViewById(R.id.Migdal);
        arhel = findViewById(R.id.Arhel);
        menora = findViewById(R.id.menora);

        passportCard.setOnClickListener( view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.passportcard.co.il/SelfService/Login?ReturnUrl=%2fSelfService"))));
        afenix.setOnClickListener( view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.fnx.co.il/sites/docs/polarchive/travelinsurance/300305127.pdf"))));
        migdal.setOnClickListener( view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.migdal.co.il/Handlers/PDFCatalogHandler.ashx?docId=522&itemType=0"))));
        arhel.setOnClickListener( view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.harel-group.co.il/insurance/travel/requests/doclib/%D7%98%D7%95%D7%A4%D7%A1%20%D7%91%D7%A7%D7%A9%D7%94%20%D7%9C%D7%94%D7%90%D7%A8%D7%9B%D7%AA%20%D7%91%D7%99%D7%98%D7%95%D7%97%20%D7%A0%D7%A1%D7%99%D7%A2%D7%94%20%D7%9C%D7%97%D7%95%D7%9C.pdf"))));
        menora.setOnClickListener( view -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.menoramivt.co.il/wps/wcm/connect/bc1364d8-f930-4050-bf0d-04059f8ca509/%D7%91%D7%A7%D7%A9%D7%94+%D7%9C%D7%94%D7%90%D7%A8%D7%9B%D7%AA+%D7%AA%D7%A7%D7%95%D7%A4%D7%AA+%D7%94%D7%91%D7%99%D7%98%D7%95%D7%97.pdf?MOD=AJPERES&CONVERT_TO=url&CACHEID=bc1364d8-f930-4050-bf0d-04059f8ca509"))));
    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,insuranceMenu.class));
    }
}
