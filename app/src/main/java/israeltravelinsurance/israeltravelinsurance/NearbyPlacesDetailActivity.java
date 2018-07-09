package israeltravelinsurance.israeltravelinsurance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Locale;

/**
 * An activity representing a single NearbyPlaces detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link NearbyPlacesListActivity}.
 */
public class NearbyPlacesDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearbyplaces_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Intent intent = getIntent();
            Double lat = intent.getDoubleExtra(NearbyPlacesDetailFragment.ARG_ITEM_LAT,0);
            Double lang = intent.getDoubleExtra(NearbyPlacesDetailFragment.ARG_ITEM_LANG,0);
            String name = intent.getStringExtra(NearbyPlacesDetailFragment.ARG_ITEM_NAME);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String uri = String.format(Locale.ENGLISH,"https://maps.google.com/maps?daddr=%f,%f (%s)",lat,lang,name);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }
            });
            Bundle arguments = new Bundle();
            arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_NAME,name);
            arguments.putDouble(NearbyPlacesDetailFragment.ARG_ITEM_LAT,lat);
            arguments.putDouble(NearbyPlacesDetailFragment.ARG_ITEM_LANG,lang);
            arguments.putDouble(NearbyPlacesDetailFragment.ARG_ITEM_RATING,intent.getDoubleExtra(NearbyPlacesDetailFragment.ARG_ITEM_RATING,0));
            arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_ADDRESS,intent.getStringExtra(NearbyPlacesDetailFragment.ARG_ITEM_ADDRESS));
            arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_ISOPEN,intent.getStringExtra(NearbyPlacesDetailFragment.ARG_ITEM_ISOPEN));
            arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_WORK_HOURS,intent.getStringExtra(NearbyPlacesDetailFragment.ARG_ITEM_WORK_HOURS));
            arguments.putString(NearbyPlacesDetailFragment.ARG_ITEM_PHONE_NUMBER,intent.getStringExtra(NearbyPlacesDetailFragment.ARG_ITEM_PHONE_NUMBER));

            NearbyPlacesDetailFragment fragment = new NearbyPlacesDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nearbyplaces_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent = new Intent(this, NearbyPlaces.class);
            intent.putExtra("type",getIntent().getStringExtra("type"));
            navigateUpTo(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
