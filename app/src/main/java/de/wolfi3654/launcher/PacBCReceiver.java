package de.wolfi3654.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by root on 24.07.2016.
 */
public class PacBCReceiver extends BroadcastReceiver {

    private final MainActivity m;

    public  PacBCReceiver(MainActivity m){
        this.m = m;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        m.setPacs();
    }
}
