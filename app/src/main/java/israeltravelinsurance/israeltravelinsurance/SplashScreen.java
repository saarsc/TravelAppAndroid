package israeltravelinsurance.israeltravelinsurance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity implements Serializable {

    Intent i;
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences settings = getSharedPreferences("userReturnDate", MODE_PRIVATE);
        String storedDate = settings.getString("date", "");
        if (storedDate.isEmpty()) {
            startActivity(new Intent(this,LoginActivity.class));
    }
     else {
             format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            try {
                date = format.parse(storedDate);
                if (new Date().after(date)) {
                    i = new Intent(this, LoginActivity.class);
                }else{
                    i = new Intent(this, MainActivity.class);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            startActivity(i);
        }


}
}