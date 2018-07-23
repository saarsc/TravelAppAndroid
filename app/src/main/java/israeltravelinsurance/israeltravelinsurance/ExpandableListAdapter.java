package israeltravelinsurance.israeltravelinsurance;


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;



/**
 * Created by reale on 21/11/2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity context;
    private List<String> listDataHeader;
    private HashMap<String,List<contactMenu.childItem>> listHashMap;
    private int myPremmision;
    public ExpandableListAdapter(Activity context, List<String> listDataHeader, HashMap<String, List<contactMenu.childItem>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = Group Item , i1 = ChildItem
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_item,null);
        }
        TextView lblListHeader = view.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childTitle = ((contactMenu.childItem) getChild(i,i1)).getTitle();
        final String childHint = ((contactMenu.childItem) getChild(i,i1)).getHint();
        final String childLink =((contactMenu.childItem) getChild(i,i1)).getLink();
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item,null);
        }

        TextView txtListChild = view.findViewById(R.id.lblListItem);
        TextView hintListChild = view.findViewById(R.id.lblListHint);
        txtListChild.setText(childTitle);
        txtListChild.setOnClickListener(view1 -> {
            if(childTitle.contains("טלפון")){
                Uri callUri = Uri.parse("tel://"+childHint);
                Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.CALL_PHONE},
                            myPremmision);
                }else {
                    context.startActivity(callIntent);
                }
            }else if(childTitle.contains("דו\"אל")){
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{childHint});
            try {
                context.startActivity(Intent.createChooser(intent, "שלח מייל..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Snackbar.make(context.findViewById(R.id.fab), "אנא התקן האפליקצייה התומכת בשליחת מיילים", Snackbar.LENGTH_SHORT).show();
            }
            }else if(childTitle.contains("עולם") || childTitle.equals("אתר")){
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(childLink));
                    context.startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Snackbar.make(context.findViewById(R.id.fab), "אנא התקן האפליקצייה התומכת בפתיחת קישורים", Snackbar.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }else if(childTitle.equals("אפליקצייה")){
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + childLink)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + childLink)));
                }
            }else if(childTitle.equals("WhatsApp")){
                context.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse( "https://api.whatsapp.com/send?phone=+9720505255785")));
            }
        });
        hintListChild.setText(childHint);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}