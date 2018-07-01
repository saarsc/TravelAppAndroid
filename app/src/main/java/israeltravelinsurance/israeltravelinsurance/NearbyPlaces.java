package israeltravelinsurance.israeltravelinsurance;

import org.json.JSONArray;
import org.json.JSONException;

public class NearbyPlaces {
    double lat,lang,rating;
    String name,address,workHours,phoneNumber;

    boolean openNow;

    public NearbyPlaces(double lat, double lang, double rating, String name, String address, boolean openNow, JSONArray workHours, String phoneNumber) {
        this.lat = lat;
        this.lang = lang;
        this.rating = rating;
        this.name = name;
        this.address = address;
        this.openNow = openNow;
        this.workHours = translate(parse(workHours));
        this.phoneNumber = phoneNumber;

    }

    private String parse(JSONArray workHours) {
        String end ="";
        for(int i=0; i<workHours.length();i++){
            try {
                end += "\n" + workHours.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    public String isOpenNow() {
        return openNow? "פתוח" : "סגור";
    }
    private String translate(String workHours){
        return workHours.replaceAll("Sunday:","ראשון").replaceAll("Monday:" ,"שני").replaceAll("Tuesday:","שלישי").replaceAll("Wednesday:","רביעי").replaceAll("Thursday:","חמישי").replaceAll("Friday:","שישי").replaceAll("Saturday:","שבת");
    }
}
