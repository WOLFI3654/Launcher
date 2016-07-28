package de.wolfi3654.launcher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by root on 24.07.2016.
 */
public class DrawerAdapter extends BaseAdapter{

    private final Context mContext;
    private final Pac[] pacsForAdapter;
    public DrawerAdapter (Context c, Pac... pacs){
        this.mContext = c;
        this.pacsForAdapter = pacs;
    }

    @Override
    public int getCount() {
        return pacsForAdapter.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder{
        TextView text;
        boolean enabled;
        ImageView icon;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pac p = pacsForAdapter[position];
        ViewHolder viewHolder;
        LayoutInflater li = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            convertView = li.inflate(R.layout.drawer_item,null);
            viewHolder = new ViewHolder();
            viewHolder.enabled = p.enabled;
            viewHolder.text = (TextView) convertView.findViewById(R.id.icon_text);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon_image);
            convertView.setTag(viewHolder);
        }else
            viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.text.setText(p.label);
        if(viewHolder.enabled) viewHolder.icon.setImageDrawable(p.icon); else viewHolder.icon.setImageResource(R.drawable.app_disabled);
        return convertView;
    }
}
