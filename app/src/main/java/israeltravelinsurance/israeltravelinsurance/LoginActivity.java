package israeltravelinsurance.israeltravelinsurance;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText _emailText;
    EditText _Date;
    Button _loginButton;
    public List<User> usersList;
    Calendar calendar = Calendar.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _emailText = findViewById(R.id.input_email);
        _Date = findViewById(R.id.date);
        _loginButton = findViewById(R.id.btn_login);

        _loginButton.setOnClickListener(v -> login());
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        _Date.setOnClickListener((View v) -> new DatePickerDialog(this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());
        Intent i =getIntent();
        usersList =(List<User>) i.getSerializableExtra("userList");
    }

    private void updateLabel() {
        String myFormat = "dd/MM/YYYY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        _Date.setText(sdf.format(calendar.getTime()));
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
//            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("מאמת...");
        progressDialog.show();


        usersList = clearDup(usersList);
        String userMail = _emailText.getText().toString();
        String returnDate = _Date.getText().toString();


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                () -> {
                    // On complete call either onLoginSuccess or onLoginFailed
                    if (userExsits(userMail, returnDate)) {
                        onLoginSuccess();
                    } else {
                        onLoginFailed();
                        progressDialog.dismiss();
                    }

                }, 500);
    }


    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent i = new Intent(this, MainActivity.class);
        String date = _Date.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences("userReturnDate", MODE_PRIVATE).edit();
        editor.putString("date", date);
        editor.commit();
        startActivity(i);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "התחברות נכשלה. אנא בדוק את הפרטים ושאתה מחובר לאינטרנט", Toast.LENGTH_LONG).show();
        Snackbar.make(findViewById(R.id.wrapper), "אין חשבון? ", Snackbar.LENGTH_INDEFINITE).setAction("הצטרף", v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@israeltravelinsurance.co.il"});
            i.putExtra(Intent.EXTRA_SUBJECT, "יצירת חשבון");
            i.putExtra(Intent.EXTRA_TEXT, "עד התאריך: " + _Date.getText().toString());
            try {
                startActivity(Intent.createChooser(i, "שלח מייל..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Snackbar.make(findViewById(R.id.fab), "אנא התקן האפליקצייה התומכת בשליחת מיילים", Snackbar.LENGTH_SHORT).show();
            }
        }).show();
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String date = _Date.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("אנא הכנס מייל תקין");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (date.isEmpty()) {
            _Date.setError("אנא בחר את תאריך החזרה השמור במערכת");
            valid = false;
        } else {
            _Date.setError(null);
        }

        return valid;
    }

    private void toast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    private ArrayList<User> clearDup(@NonNull List<User> dup) {
        ArrayList<User> og = new ArrayList<>();
        for (User user : dup) {
            if (!og.contains(user)) {
                og.add(user);
            }
        }
        return og;
    }

    private boolean userExsits(String email, String date) {
        User temp = new User(date, email);
        return usersList.contains(temp);
    }
}