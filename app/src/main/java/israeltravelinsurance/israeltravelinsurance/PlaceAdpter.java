package israeltravelinsurance.israeltravelinsurance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaceAdpter extends ArrayAdapter<NearbyPlaces> {
    private Activity activity;
    private ArrayList<NearbyPlaces> nearbyPlaces;
    private static LayoutInflater inflater = null;

    public PlaceAdpter(Activity activity, int textViewResourceId, ArrayList<NearbyPlaces> place) {
        super(activity, textViewResourceId, place);
        try {
            this.activity = activity;
            this.nearbyPlaces = place;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }

    }

    public int getCount() {
        return nearbyPlaces.size();
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_name;
        public TextView phoneNumber;
        public TextView is_open;
        public TextView address;
        public TextView rating;
        public TextView workHours;
        public Button navigateButton;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.place_list_view, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.placeName);
                holder.is_open = (TextView) vi.findViewById(R.id.isOpen);
                holder.address = (TextView) vi.findViewById(R.id.address);
                holder.rating = (TextView) vi.findViewById(R.id.rating);
                holder.phoneNumber = (TextView) vi.findViewById(R.id.phoneNumber);
                holder.workHours = (TextView) vi.findViewById(R.id.workHours);
                holder.navigateButton = (Button) vi.findViewById(R.id.navigateButton);
                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }
            holder.display_name.setText(nearbyPlaces.get(position).getName());
            holder.is_open.setText(nearbyPlaces.get(position).isOpenNow());
            holder.address.setText(nearbyPlaces.get(position).getAddress());
            holder.rating.setText(nearbyPlaces.get(position).getRating());
            holder.phoneNumber.setText(nearbyPlaces.get(position).getPhoneNumber());
            holder.workHours.setText(nearbyPlaces.get(position).getWorkHours());
            holder.navigateButton.setOnClickListener(view -> {
                String uri = String.format(Locale.ENGLISH,"https://maps.google.com/maps?daddr=%f,%f (%s)",nearbyPlaces.get(position).getLat(),nearbyPlaces.get(position).getLang(),nearbyPlaces.get(position).getName());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                activity.startActivity(intent);
            });
        } catch (Exception e) {


        }
        return vi;
    }

    public PlaceAdpter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public PlaceAdpter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public PlaceAdpter(@NonNull Context context, int resource, @NonNull NearbyPlaces[] objects) {
        super(context, resource, objects);
    }

    public PlaceAdpter(@NonNull Context context, int resource, int textViewResourceId, @NonNull NearbyPlaces[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public PlaceAdpter(@NonNull Context context, int resource, @NonNull List<NearbyPlaces> objects) {
        super(context, resource, objects);
    }

    public PlaceAdpter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<NearbyPlaces> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}
