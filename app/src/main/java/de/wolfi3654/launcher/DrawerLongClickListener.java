package de.wolfi3654.launcher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

/**
 * Created by root on 26.07.2016.
 */
public class DrawerLongClickListener implements AdapterView.OnItemLongClickListener{

    private final Context mContext;
    private final SlidingDrawer slidingDrawer;
    private final RelativeLayout homeView;
    private final Pac[] pacs;

    public DrawerLongClickListener(Context ctx, SlidingDrawer slidingDrawer, RelativeLayout homeView, Pac... pacs){
        this.slidingDrawer = slidingDrawer;
        this.homeView = homeView;
        this.mContext = ctx;
        this.pacs = pacs;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(view.getWidth(),view.getHeight());
        lp.leftMargin = (int) view.getX();
        lp.topMargin = (int) view.getY();

        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout ll = (LinearLayout) li.inflate(R.layout.drawer_item,null);
        ((ImageView)ll.findViewById(R.id.icon_image)).setImageDrawable(((ImageView)view.findViewById(R.id.icon_image)).getDrawable());
        ((TextView)ll.findViewById(R.id.icon_text)).setText(((TextView)view.findViewById(R.id.icon_text)).getText());

        ll.setOnTouchListener(new AppTouchListener(view.getWidth()));
        ll.setOnClickListener(new AppClickListener(pacs[i],mContext));

        homeView.addView(ll,lp);
        slidingDrawer.animateClose();
        slidingDrawer.bringToFront();
        return true;
    }
}
