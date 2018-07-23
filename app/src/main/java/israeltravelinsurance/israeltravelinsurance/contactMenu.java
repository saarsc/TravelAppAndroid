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
        listDataHeader.add("משרד החוץ");

        List<childItem> mainCompany = new ArrayList<>();
        mainCompany.add(new childItem("טלפון","072-2222-123"));
        mainCompany.add(new childItem("דו\"אל",getResources().getString(R.string.nav_header_subtitle)));
        mainCompany.add(new childItem("אתר","","https://israeltravelinsurance.co.il"));
        mainCompany.add(new childItem("WhatsApp","https://israeltravelinsurance.co.il"));

        List<childItem> migdal = new ArrayList<>();
        migdal.add(new childItem("טלפון בין לאומי","972-3-9206912"));
        migdal.add(new childItem("טלפון ארה\"ב","717-3-128917"));
        migdal.add(new childItem("טלפון מהארץ","03-9206912"));
        migdal.add(new childItem("פקס","972-3-9226380"));
        migdal.add(new childItem("דו\"אל","migdal@ima-mc.com"));
        migdal.add(new childItem("אתר","","https://www.migdal.co.il/He/Pages/MigdalHomePage.aspx"));
        migdal.add(new childItem("אפליקצייה","","com.migdal.migdalcustomers"));

        List<childItem> afniex = new ArrayList<>();
        afniex.add(new childItem("טלפון","03-9206912"));
        afniex.add(new childItem("דו\"אל","fnx@ima-mc.com"));
        afniex.add(new childItem("אתר","","https://www.fnx.co.il/"));

        List<childItem> arhel = new ArrayList<>();
        arhel.add(new childItem("טלפון","972-3-7547030"));
        arhel.add(new childItem("אתר","","https://www.harel-group.co.il/Pages/default.aspx"));
        arhel.add(new childItem("אפליקצייה","","com.harel.droid"));
        List<childItem> passportCard = new ArrayList<>();
        passportCard.add(new childItem("לכל המספרים מסביב לעולם","","https://israeltravelinsurance.co.il/PDF/PassportCard%20contact.pdf"));
        passportCard.add(new childItem("אתר","","https://www.passportcard.co.il/"));
        passportCard.add(new childItem("אפליקצייה","","com.passportcard.pocket"));

        List<childItem> menora = new ArrayList<>();
        menora.add(new childItem("טלפון","972-3-9206911"));
        menora.add(new childItem("מוקד איתור וחילוץ \"מגנוס\" בטלפון","972-03-6006505"));
        menora.add(new childItem("אתר","","https://www.menoramivt.co.il/wps/portal/home/"));

        List<childItem>  foriegn= new ArrayList<>();
        foriegn.add(new childItem("טלפון - חדר מצב","+972-2-5303155"));
        listHash.put(listDataHeader.get(0),mainCompany);
        listHash.put(listDataHeader.get(1),migdal);
        listHash.put(listDataHeader.get(2),afniex);
        listHash.put(listDataHeader.get(3),arhel);
        listHash.put(listDataHeader.get(4),passportCard);
        listHash.put(listDataHeader.get(5),menora);
        listHash.put(listDataHeader.get(6),foriegn);
    }
    public class childItem{
        private String title;
        private String hint;
        private String link;

        public childItem(String title, String hint) {
            this.title = title;
            this.hint = hint;
        }
        public childItem(String title, String hint, String link) {
            this.title = title;
            this.hint = hint;
            this.link = link;
        }

        public String getLink() {
            return link;
        }

        public String getTitle() {
            return title;
        }

        public String getHint() {
            return hint;
        }

    }
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this,MainActivity.class));
    }
}