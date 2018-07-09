package israeltravelinsurance.israeltravelinsurance;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single NearbyPlaces detail screen.
 * This fragment is either contained in a {@link NearbyPlacesListActivity}
 * in two-pane mode (on tablets) or a {@link NearbyPlacesDetailActivity}
 * on handsets.
 */
public class NearbyPlacesDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_NAME = "name";
    public static final String ARG_ITEM_LAT = "lat";
    public static final String ARG_ITEM_LANG = "lang";
    public static final String ARG_ITEM_RATING = "rating";
    public static final String ARG_ITEM_ADDRESS = "address";
    public static final String ARG_ITEM_ISOPEN  = "openNow";
    public static final String ARG_ITEM_WORK_HOURS = "workHours";
    public static final String ARG_ITEM_PHONE_NUMBER = "phoneNumber";
    /**
     * The dummy content this fragment is presenting.
     */
    private NearbyPlaces mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NearbyPlacesDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_NAME)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
//            mItem = new NearbyPlaces(getArguments().getDouble(ARG_ITEM_LAT),getArguments().getDouble(ARG_ITEM_LANG),getArguments().getDouble(ARG_ITEM_RATING),getArguments().getString(ARG_ITEM_NAME),getArguments().getString(ARG_ITEM_ADDRESS),getArguments().getString(ARG_ITEM_ISOPEN),getArguments().getString(ARG_ITEM_WORK_HOURS),getArguments().getString(ARG_ITEM_PHONE_NUMBER));
            mItem = new NearbyPlaces(getArguments());
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.nearbyplaces_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.nearbyplaces_address)).setText(mItem.getAddress());
            ((TextView) rootView.findViewById(R.id.nearbyplaces_phone_number)).setText(mItem.getPhoneNumber());
            ((TextView) rootView.findViewById(R.id.nearbyplaces_rating)).setText(mItem.getRating());
            ((TextView) rootView.findViewById(R.id.nearbyplaces_open)).setText(mItem.isOpenNow());
            ((TextView) rootView.findViewById(R.id.nearbyplaces_work_hours)).setText(mItem.getWorkHours());
        }

        return rootView;
    }
}
