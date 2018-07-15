package israeltravelinsurance.israeltravelinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class contactMenu extends AppCompatActivity {
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<childItem>> listHash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_menu);

        listView = findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHash);
        listView.setAdapter(listAdapter);
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("סוכנות");
        listDataHeader.add("מגדל");
        listDataHeader.add("הפניקס");
        listDataHeader.add("הראל");
        listDataHeader.add("PassportCard");
        listDataHeader.add("מנורה");

        List<childItem> mainCompany = new ArrayList<>();
        mainCompany.add(new childItem("טלפון","072-2222-123"));
        mainCompany.add(new childItem("דו\"אל","info@israeltravelinsurance.co.il"));

        List<childItem> migdal = new ArrayList<>();
        migdal.add(new childItem("טלפון בין לאומי","972-3-9206912"));
        migdal.add(new childItem("טלפון ארה\"ב","717-3-128917"));
        migdal.add(new childItem("טלפון מהארץ","03-9206912"));
        migdal.add(new childItem("פקס","972-3-9226380"));
        migdal.add(new childItem("דו\"אל","migdal@ima-mc.com"));

        List<childItem> afniex = new ArrayList<>();
        afniex.add(new childItem("טלפון","03-9206912"));
        afniex.add(new childItem("דו\"אל","fnx@ima-mc.com"));

        List<childItem> arhel = new ArrayList<>();
        arhel.add(new childItem("טלפון","972-3-7547030"));
        List<childItem> passportCard = new ArrayList<>();
        passportCard.add(new childItem("לכל המספרים מסביב לעולם",""));

        List<childItem> menora = new ArrayList<>();
        menora.add(new childItem("טלפון","972-3-9206911"));
        menora.add(new childItem("מוקד איתור וחילוץ \"מגנוס\" בטלפון","972-03-6006505"));

        listHash.put(listDataHeader.get(0),mainCompany);
        listHash.put(listDataHeader.get(1),migdal);
        listHash.put(listDataHeader.get(2),afniex);
        listHash.put(listDataHeader.get(3),arhel);
        listHash.put(listDataHeader.get(4),passportCard);
        listHash.put(listDataHeader.get(5),menora);
    }
    public class childItem{
        private String title;
        private String hint;

        public String getTitle() {
            return title;
        }

        public String getHint() {
            return hint;
        }

        public childItem(String title, String hint) {
            this.title = title;
            this.hint = hint;
        }

    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,MainActivity.class));
    }
}