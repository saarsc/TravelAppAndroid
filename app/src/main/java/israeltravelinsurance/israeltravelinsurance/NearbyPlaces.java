package israeltravelinsurance.israeltravelinsurance;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

public class NearbyPlaces {
    double lat,lang,rating;
    String name,address,workHours,phoneNumber;

    String openNow;

    public NearbyPlaces(double lat, double lang, double rating, String name, String address, boolean openNow, JSONArray workHours, String phoneNumber) {
        this.lat = lat;
        this.lang = lang;
        this.rating = rating;
        this.name = name;
        this.address = address;
        this.openNow = isOpenNow(openNow);
        this.workHours = translate(parse(workHours));
        this.phoneNumber = phoneNumber;

    }


    public NearbyPlaces(Bundle arguments) {
        this.lat = arguments.getDouble(NearbyPlacesDetailFragment.ARG_ITEM_LAT);
        this.lang = arguments.getDouble(NearbyPlacesDetailFragment.ARG_ITEM_LANG);
        this.rating = arguments.getDouble(NearbyPlacesDetailFragment.ARG_ITEM_RATING);
        this.name = arguments.getString(NearbyPlacesDetailFragment.ARG_ITEM_NAME);
        this.address = arguments.getString(NearbyPlacesDetailFragment.ARG_ITEM_ADDRESS);
        this.openNow = arguments.getString(NearbyPlacesDetailFragment.ARG_ITEM_ISOPEN);
        this.workHours = arguments.getString(NearbyPlacesDetailFragment.ARG_ITEM_WORK_HOURS);
        this.phoneNumber = arguments.getString(NearbyPlacesDetailFragment.ARG_ITEM_PHONE_NUMBER);
    }

    private String parse(JSONArray workHours) {
        String end = "";
        try {
            for(int i=0; i<workHours.length();i++){
                    end += "\n" + workHours.getString(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return end;
    }

    public double getLat() {
        return lat;
    }

    public double getLang() {
        return lang;
    }

    public String getRating() {
        return String.valueOf(rating);
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkHours() {
        return workHours;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String isOpenNow(){
        return this.openNow;
    }
    public String isOpenNow(boolean open) {
        return open? "פתוח" : "סגור";
    }
    private String translate(String workHours){
        return workHours.replaceAll("Sunday:","ראשון").replaceAll("Monday:" ,"שני").replaceAll("Tuesday:","שלישי").replaceAll("Wednesday:","רביעי").replaceAll("Thursday:","חמישי").replaceAll("Friday:","שישי").replaceAll("Saturday:","שבת");
    }
}
