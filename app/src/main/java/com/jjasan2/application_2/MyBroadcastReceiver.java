package com.jjasan2.application_2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.jjasan2.application_2.attractions.AttractionsActivity;
import com.jjasan2.application_2.hotels.HotelsActivity;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        startActivity(context, intent.getAction());
    }

    public void startActivity(Context context, String activityName){
        Intent intent;
        switch (activityName){
            case "Attractions":
                intent = new Intent(context, AttractionsActivity.class);
                context.startActivity(intent);
                break;
            case "Hotels":
                intent = new Intent(context, HotelsActivity.class);
                context.startActivity(intent);
                break;
        }
    }
}