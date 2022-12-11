package com.jjasan2.application_2.attractions;

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
import com.jjasan2.application_2.MainActivity;
import com.jjasan2.application_2.R;
import com.jjasan2.application_2.hotels.HotelsActivity;

public class AttractionsActivity extends AppCompatActivity {

    static String[] attractions;
    private ListViewModel viewModel;
    private FrameLayout attractionsListView, attractionDetailsView;
    private final AttractionDetailsFragment attractionDetailsFragment = new AttractionDetailsFragment();
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        attractionsListView = findViewById(R.id.attractions_list_fragment);
        attractionDetailsView = findViewById(R.id.attractions_details_fragment);

        // Get the list of attractions
        attractions = getResources().getStringArray(R.array.attractions);

        // Display the fragment with the list of attractions
        FragmentManager fragmentManager = getSupportFragmentManager();

        // To modify the layout when the backstack changes
        fragmentManager.addOnBackStackChangedListener(
                this::setLayout
        );

        // Create fragment if there is no retained fragment
        if(fragmentManager.findFragmentById(R.id.attractions_list_fragment) == null){
            FragmentTransaction attractionsListTransaction = fragmentManager.beginTransaction();
            attractionsListTransaction.replace(R.id.attractions_list_fragment,
                    AttractionsListFragment.class, null).commit();
        }

        // Display the attraction details fragment when an attraction is selected
        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        viewModel.getSelectedItem().observe(this, item -> {
            if(fragmentManager.findFragmentById(R.id.attractions_details_fragment) == null ||
                    !attractionDetailsFragment.isAdded()){
                FragmentTransaction attractionDetailTransaction = fragmentManager.beginTransaction();
                attractionDetailTransaction.add(R.id.attractions_details_fragment,
                        attractionDetailsFragment);
                attractionDetailTransaction.addToBackStack(null);
                attractionDetailTransaction.commit();
                fragmentManager.executePendingTransactions();
            }
        });
        setLayout();
    }

    // Set the layout based on whether an attraction is selected
    private void setLayout() {
        if (!attractionDetailsFragment.isAdded()) {
            attractionsListView.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            attractionDetailsView.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                attractionsListView.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));
                attractionDetailsView.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            } else{
                attractionDetailsView.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
                attractionsListView.setLayoutParams(new LinearLayout.LayoutParams(0,
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
//                Intent attractionsIntent = new Intent(getApplicationContext(), AttractionsActivity.class);
//                startActivity(attractionsIntent);
                Toast.makeText(this, "You are at Attractions page", Toast.LENGTH_LONG).show();
                break;
            case R.id.option_hotels:
                Intent hotelsIntent = new Intent(getApplicationContext(), HotelsActivity.class);
                startActivity(hotelsIntent);
                break;
            default:
                super.onOptionsItemSelected(item);
                return false;
        }
        return true;
    }
}