package israeltravelinsurance.israeltravelinsurance;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

public class insuranceMenu extends AppCompatActivity {
    Button increaseInsurance;
    Button sendFile;
    Button claimMenu;
    private static final int FILE_SELECT_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_menu);
        increaseInsurance = findViewById(R.id.btnIncrease);
        sendFile= findViewById(R.id.btnSendFile);
        increaseInsurance.setOnClickListener(view ->startActivity(new Intent(this, extendInsurance.class)));
        sendFile.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                startActivityForResult(Intent.createChooser(intent, "בחר קובץ"), FILE_SELECT_CODE);
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Snackbar.make(findViewById(R.id.fab), "אנא התקן האפליקצייה התומכת בהצגת קבצים", Snackbar.LENGTH_SHORT).show();
            }
        });
        claimMenu = findViewById(R.id.claimsMenu);
        claimMenu.setOnClickListener(view ->startActivity(new Intent(this, claimMenu.class) ));
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
                    String to[] = {getResources().getString(R.string.nav_header_subtitle)};
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
    public void onBackPressed(){
        startActivity(new Intent(this,MainActivity.class));
}
}
