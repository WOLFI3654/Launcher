package de.wolfi3654.launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SlidingDrawer;

import java.net.DatagramPacket;

/**
 * Created by root on 24.07.2016.
 */
public class DrawerClickListener implements AdapterView.OnItemClickListener{

    private final Context mContext;
    private final Pac[] pacs;
    private final PackageManager packageManager;
    private final SlidingDrawer drawer;

    public DrawerClickListener(Context x, SlidingDrawer drawer, PackageManager pm, Pac... pacs){
        this.mContext = x;
        this.pacs = pacs;
        this.drawer = drawer;
        this.packageManager = pm;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        drawer.animateClose();
        Intent launchIntent = new Intent(Intent.ACTION_MAIN);
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cp = new ComponentName(pacs[position].packageName,pacs[position].name);
        launchIntent.setComponent(cp);
        mContext.startActivity(launchIntent);
    }
}
