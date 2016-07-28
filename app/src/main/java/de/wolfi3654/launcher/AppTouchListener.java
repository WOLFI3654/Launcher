package de.wolfi3654.launcher;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

/**
 * Created by root on 26.07.2016.
 */
public class AppTouchListener implements OnTouchListener {

    private final int icon_size;
    public AppTouchListener(int size){
        this.icon_size = size;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(icon_size,icon_size);
                lp.leftMargin = (int) motionEvent.getRawX()-icon_size/2;
                lp.topMargin = (int) motionEvent.getRawY()-icon_size/2;

                view.setLayoutParams(lp);
                break;
        }
        return true;
    }


}
