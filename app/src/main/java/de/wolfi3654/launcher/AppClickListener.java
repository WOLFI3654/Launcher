package de.wolfi3654.launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by root on 26.07.2016.
 */
public class AppClickListener implements View.OnClickListener {

    private final Pac pac;
    private final Context mContext;

    public AppClickListener(Pac pac, Context c){
        this.pac = pac;
        this.mContext = c;
    }

    @Override
    public void onClick(View view) {
        Intent launchIntent = new Intent(Intent.ACTION_MAIN);
        launchIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cp = new ComponentName(pac.packageName,pac.name);
        launchIntent.setComponent(cp);
        mContext.startActivity(launchIntent);
    }
}
