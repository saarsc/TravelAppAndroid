package israeltravelinsurance.israeltravelinsurance;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static israeltravelinsurance.israeltravelinsurance.AppConfig.ENCRPYTION_KEY;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    EditText _emailText;
    EditText _Date;
    Button _loginButton;
    public List<User> usersList;
    Calendar calendar = Calendar.getInstance();

    TextView toSignup;
    TextView toSignin;
    RelativeLayout signIn;
    RelativeLayout signUp;



    EditText nameSignUp;
    EditText emailSignUp;
    EditText phoneSignUp;
    EditText dateSignUp;
    EditText dateLeaveSignUp;
    CheckBox pastCustomer;
    CheckBox tosCheckBox;
    TextView tosDisplay;
    Button btnSignUp;

    EditText currentDateEditText;

    String myFormat = "dd/MM/yyyy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    DateFormat dateFormat = new SimpleDateFormat(myFormat,Locale.getDefault());

    RequestQueue queue;

    Snackbar register;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         queue = Volley.newRequestQueue(this);
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
        _Date.setOnClickListener((View v) ->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
            currentDateEditText = _Date;
        });

        signIn = findViewById(R.id.loginLayout);
        signUp = findViewById(R.id.signupLayout);
        toSignup = findViewById(R.id.toSignup);
        toSignup.setOnClickListener(view -> {
            toggleViability();
        });
        toSignin = findViewById(R.id.toSignin);
        toSignin.setOnClickListener(view -> {
            toggleViability();

        });

        nameSignUp = findViewById(R.id.input_nameSignUp);
        emailSignUp = findViewById(R.id.input_emailSignUp);
        phoneSignUp = findViewById(R.id.input_phoneSignUp);
        pastCustomer = findViewById(R.id.pastCustomer);
        dateSignUp = findViewById(R.id.dateBackSignUp);
        dateSignUp.setOnClickListener((View v) -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
            if(!dateLeaveSignUp.getText().toString().isEmpty()){
                try {
                    Date leaveDate = dateFormat.parse(dateLeaveSignUp.getText().toString());
                    datePickerDialog.getDatePicker().setMinDate(leaveDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else{
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() -1000);
            }
            datePickerDialog.show();
            currentDateEditText = dateSignUp;
        });
        dateLeaveSignUp = findViewById(R.id.dateLeaveSignUp);
        dateLeaveSignUp.setOnClickListener((View v) -> {    DatePickerDialog datePickerDialog = new DatePickerDialog(this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
            if(!dateSignUp.getText().toString().isEmpty()){
                try {
                    Date leaveDate = dateFormat.parse(dateSignUp.getText().toString());
                    datePickerDialog.getDatePicker().setMaxDate(leaveDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() -1000);

            datePickerDialog.show();
        currentDateEditText = dateLeaveSignUp;
        });
        tosCheckBox = findViewById(R.id.tos);
        tosDisplay = findViewById(R.id.tosDisplay);
        tosDisplay.setOnClickListener(view -> {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("תנאי שימוש");
            builder.setMessage("השימוש באתר חינם ועשוי להשתנות בכל עת ללא הודעה מראש.\n" +
                    "\n" +
                    "המידע באתר מתקבל ממקורות שונים ברחבי הרשת על כל המשתמע מכך.\n" +
                    "\n" +
                    "המרכז הישראלי לביטוחי נסיעות ואו שוורץ צבי אינם אחראים בכל דרך וצורה על נכונות הנתונים ואיכותם המתקבלים ממקורות שונים ברשת האינטרנט.\n" +
                    "\n" +
                    "באחריותו הבלעדית של המשתמש לבדוק את נכונות הנתונים.\n" +
                    "\n" +
                    "לעולם לא נעביר את פרטיך לצד ג'. אנו עשויים להשתמש בפרטיך להעברת הודעות שיווק ופירסום בנושאי ביטוח בלבד.\n" +
                    "\n" +
                    "תוכל להסיר את פרטיך בכל עת ע\"י בקשה מפורשת בדוא\"ל.\n" +
                    "\n" +
                    "האפליקציה והאתר www.israeltravelinsurance.co.il  מנוהלים ושייכים לסוכן ביטוח שוורץ צבי טל: 072-2222-123\n" +
                    "\n" +
                    "נשמח לקבל הערות / הארות בדוא\"ל info@israeltravelinsurance.co.il\n" +
                    "\n" +
                    "המידע הביטוחי הנימסר הינו כללי שיווקי ותמציתי ובכפוף לתנאי הפוליסות של החברות המיוצגות,\n" +
                    "\n" +
                    "המחירים הקובעים הם כמופיע באתרים הרישמיים של חברות הביטוח המיוצגות על ידנו.");
            builder.show();
        });
        btnSignUp =findViewById(R.id.btn_signup);
        btnSignUp.setOnClickListener(view -> signUp());

        register = Snackbar.make(findViewById(R.id.wrapper), "אין חשבון? ", Snackbar.LENGTH_INDEFINITE).setAction("הצטרף", v -> { toggleViability(); });
    }
    private void toggleViability(){
        if(signUp.getVisibility() == View.VISIBLE) {
            signUp.animate().alpha(0.0f).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    signIn.setVisibility(View.VISIBLE);
                    signIn.setAlpha(1.0f);

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    signUp.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }else{
            register.dismiss();
            signIn.animate().alpha(0.0f).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    signUp.setVisibility(View.VISIBLE);
                    signUp.setAlpha(1.0f);
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    signIn.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }


    private void updateLabel() {

        currentDateEditText.setText(sdf.format(calendar.getTime()));
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
//            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);



//        usersList = clearDup(usersList);
        String userMail = _emailText.getText().toString();
        String returnDate = _Date.getText().toString();


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                () -> {
                    // On complete call either onLoginSuccess or onLoginFailed
                    userExsits(userMail,returnDate);
//                    if (userExsits(userMail, returnDate)) {
//                        onLoginSuccess();
//                    } else {
//                        onLoginFailed();
//                        progressDialog.dismiss();
//                    }

                }, 500);
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
    private void userExsits(String email, String date) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("מאמת...");
        progressDialog.show();

        String secretKey = "4oMPk90WBT9p4xyI";
        String data= email + "&"+date;
        AesCipher aesCipher = AesCipher.encrypt(secretKey,data);

        String signupLink = null;
        try {
            signupLink = "https://israeltravelinsurance.co.il/PHP/App/signup.php?q=" + URLEncoder.encode(aesCipher.getData(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, signupLink,
                response -> {
                    if(response.equals("OK")){
                        onLoginSuccess();
                    }else if(response.equals("ERROR")){
                        progressDialog.dismiss();
                        onLoginFailed();
                    }
                }, error -> {
            Log.e( "REGISTER FUCK UP",error.toString());
        });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) {

            }
        });
        queue.add(stringRequest);
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
            toggleViability();
        }).show();
        _loginButton.setEnabled(true);
    }
//
//    private ArrayList<User> clearDup(@NonNull List<User> dup) {
//        ArrayList<User> og = new ArrayList<>();
//        for (User user : dup) {
//            if (!og.contains(user)) {
//                og.add(user);
//            }
//        }
//        return og;
//    }


    private void signUp() {
//        AppController appController = new AppController();

        if(!validateSignUp()){
            return;
        }
        btnSignUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(" נרשם...");
        progressDialog.show();

        String userData = formatData();

        AesCipher encrypted = AesCipher.encrypt(ENCRPYTION_KEY, userData);


        String signupLink = null;
        try {
            signupLink = "https://israeltravelinsurance.co.il/PHP/App/signup.php?q=" + URLEncoder.encode(encrypted.getData(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, signupLink,
                response -> {
                    if(response.equals("OK")){
                        SharedPreferences.Editor editor = getSharedPreferences("userReturnDate", MODE_PRIVATE).edit();
                        editor.putString("date", dateSignUp.getText().toString());
                        editor.commit();
                        progressDialog.dismiss();
                        startActivity(new Intent(this,MainActivity.class));
                    }else if(response.equals("ERROR")){
                        Toast.makeText(this, "התרחשה שגיאה בהרשמה אנא נסה שוב מאוחר יותר", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    Log.e( "REGISTER FUCK UP",error.toString());
                });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    private String formatData() {
        String name = nameSignUp.getText().toString();
        String email = emailSignUp.getText().toString();
        String phone = phoneSignUp.getText().toString();
        String date = dateSignUp.getText().toString();
        String dateLeave = dateLeaveSignUp.getText().toString();
        return (name + "&" + email + "&" + phone + "&" + date + "&"+(pastCustomer.isChecked() ?  "כן": "לא")) + "&" + dateLeave;
    }

    private boolean validateSignUp() {
        boolean valid = true;

        String name = nameSignUp.getText().toString();
        String email = emailSignUp.getText().toString();
        String phone = phoneSignUp.getText().toString();
        String date = dateSignUp.getText().toString();
        String dateLeave = dateLeaveSignUp.getText().toString();

        Date returnDate = null;
        Date leaveDate = null;
        try {
             returnDate = dateFormat.parse(date);
             leaveDate = dateFormat.parse(dateLeave);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (name.isEmpty() || name.length() <=1) {
            nameSignUp.setError("אנא הכנס שם תקין");
            valid = false;
        } else {
            nameSignUp.setError(null);
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailSignUp.setError("אנא הכנס מייל תקין");
            valid = false;
        } else {
            emailSignUp.setError(null);
        }
        if (email.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            phoneSignUp.setError("אנא הכנס מספר טלפון תקין");
            valid = false;
        } else {
            phoneSignUp.setError(null);
        }
        if (date.isEmpty()) {
            dateSignUp.setError("אנא בחר את תאריך החזרה ");
            valid = false;
        } else {
            dateSignUp.setError(null);
        }
        if (dateLeave.isEmpty()) {
            dateLeaveSignUp.setError("אנא בחר את תאריך הטיסה");
            valid = false;
        } else {
            dateLeaveSignUp.setError(null);
        }
        if(!tosCheckBox.isChecked()){
            tosCheckBox.setError("אנא הסכם לתנאי השימוש");
            valid = false;
        }else{
            tosCheckBox.setError(null);
        }
        if(valid){
            if(returnDate.before(leaveDate)){
                dateSignUp.setError("תאריך החזרה חייב להיות אחרי תאריך העזיבה");
                valid = false;
            }{
                dateSignUp.setError(null);
            }
        }


        return valid;
    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }
}