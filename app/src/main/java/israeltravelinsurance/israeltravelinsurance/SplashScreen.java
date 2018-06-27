package israeltravelinsurance.israeltravelinsurance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.felipecsl.gifimageview.library.GifImageView;
import com.google.android.gms.common.util.IOUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SplashScreen extends AppCompatActivity implements Serializable {
    GifImageView gifImageView;
    public List<User> usersList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbRef = database.getReference().child("user");
    boolean done = false;
    int noChild = 0;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        gifImageView = findViewById(R.id.gifImageView);
        SharedPreferences settings = getSharedPreferences("userReturnDate", MODE_PRIVATE);
        String storedDate = settings.getString("date", "");
        usersList = new ArrayList<>();
        if (storedDate.equals("")) {
        dbRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                noChild++;
                usersList.add(dataSnapshot.getValue(User.class));
                if (noChild >= dataSnapshot.getChildrenCount()) {
                    done = (true);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        new Thread(() -> {
            try {
                //Put the gif file into the layout
                InputStream inputStream = getAssets().open("loadinganimation.gif");
                byte[] bytes = IOUtils.toByteArray(inputStream);
                gifImageView.setBytes(bytes);
                gifImageView.startAnimation();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                while (!done) ;
                i = new Intent(this, LoginActivity.class);
                i.putExtra("userList", (Serializable) usersList);
                startActivity(i);
            }

        }).start();
    }
     else {
            DateFormat format = new SimpleDateFormat("dd/MM/YYYY", Locale.US);
            try {
                Date date;
                date = format.parse(storedDate);
                if (date.after(new Date())) {
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