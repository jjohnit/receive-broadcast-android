package com.jjasan2.application_2.hotels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jjasan2.application_2.ListViewModel;
import com.jjasan2.application_2.R;
import com.jjasan2.application_2.attractions.AttractionDetailsFragment;
import com.jjasan2.application_2.attractions.AttractionsActivity;
import com.jjasan2.application_2.attractions.AttractionsListFragment;

public class HotelsActivity extends AppCompatActivity {

    static String[] hotels;
    private ListViewModel viewModel;
    private FrameLayout hotelsListView, hotelDetailsView;
    private final HotelDetailsFragment hotelDetailsFragment = new HotelDetailsFragment();
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);

        hotelsListView = findViewById(R.id.hotels_list_fragment);
        hotelDetailsView = findViewById(R.id.hotel_details_fragment);

        // Get the list of attractions
        hotels = getResources().getStringArray(R.array.hotels);

        // Display the fragment with the list of attractions
        FragmentManager fragmentManager = getSupportFragmentManager();

        // To modify the layout when the backstack changes
        fragmentManager.addOnBackStackChangedListener(
                this::setLayout
        );

        // Create fragment if there is no retained fragment
        if(fragmentManager.findFragmentById(R.id.hotels_list_fragment) == null){
            FragmentTransaction attractionsListTransaction = fragmentManager.beginTransaction();
            attractionsListTransaction.replace(R.id.hotels_list_fragment,
                    HotelsListFragment.class, null).commit();
        }

        // Display the attraction details fragment when an attraction is selected
        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        viewModel.getSelectedItem().observe(this, item -> {
            if(fragmentManager.findFragmentById(R.id.hotel_details_fragment) == null ||
                    !hotelDetailsFragment.isAdded()){
                FragmentTransaction hotelDetailTransaction = fragmentManager.beginTransaction();
                hotelDetailTransaction.add(R.id.hotel_details_fragment,
                        hotelDetailsFragment);
                hotelDetailTransaction.addToBackStack(null);
                hotelDetailTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });
        setLayout();
    }

    // Set the layout based on whether an attraction is selected
    private void setLayout() {
        if (!hotelDetailsFragment.isAdded()) {
            hotelsListView.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            hotelDetailsView.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                hotelsListView.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
                hotelDetailsView.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            } else{
                hotelDetailsView.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                hotelsListView.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
            }
        }
    }

    // To show the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.option_attractions:
                Intent attractionsIntent = new Intent(getApplicationContext(), AttractionsActivity.class);
                startActivity(attractionsIntent);
                break;
            case R.id.option_hotels:
//                Intent hotelsIntent = new Intent(getApplicationContext(), HotelsActivity.class);
//                startActivity(hotelsIntent);
                Toast.makeText(this, "You are at Hotels page", Toast.LENGTH_LONG).show();
                break;
            default:
                super.onOptionsItemSelected(item);
                return false;
        }
        return true;
    }
}