package com.jjasan2.application_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.jjasan2.application_2.attractions.AttractionsActivity;
import com.jjasan2.application_2.hotels.HotelsActivity;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver receiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Attractions");
        intentFilter.addAction("Hotels");
        registerReceiver(receiver, intentFilter);
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
                Intent hotelsIntent = new Intent(getApplicationContext(), HotelsActivity.class);
                startActivity(hotelsIntent);
                break;
            default:
                super.onOptionsItemSelected(item);
                return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}