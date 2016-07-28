package de.wolfi3654.launcher;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private DrawerAdapter drawerAdapter;
    private GridView drawerGrid;
    private SlidingDrawer slidingDrawer;
    private RelativeLayout homeView;
    Pac[] pacs;
    PackageManager pm;
    private AppWidgetManager mAppWidgetManager;
    private AppWidgetHost mAppWidgetHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppWidgetManager = AppWidgetManager.getInstance(this);
        mAppWidgetHost = new AppWidgetHost(this,R.id.APPWIDGET_HOST_ID);

        drawerGrid = (GridView) findViewById(R.id.content);
        slidingDrawer = (SlidingDrawer) findViewById(R.id.drawer);
        homeView = (RelativeLayout) findViewById(R.id.home_view);
        pm = getPackageManager();

        setPacs();

        homeView.setOnLongClickListener(new View.OnLongClickListener() {
            void selectWidget() {
                int appWidgetId = MainActivity.this.mAppWidgetHost.allocateAppWidgetId();
                Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
                pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                addEmptyData(pickIntent);
                startActivityForResult(pickIntent, R.id.REQUEST_PICK_APPWIDGET);
            }

            void addEmptyData(Intent pickIntent) {
                ArrayList customInfo = new ArrayList<>();
                pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo);
                ArrayList customExtras = new ArrayList<>();
                pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras);
            };

            @Override
            public boolean onLongClick(View view) {
                selectWidget();
                return true;
            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        registerReceiver(new PacBCReceiver(this),filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK ) {
            if (requestCode == R.id.REQUEST_PICK_APPWIDGET) {
                configureWidget(data);
            }
            else if (requestCode == R.id.REQUEST_CREATE_APPWIDGET) {
                createWidget(data);
            }
        }
        else if (resultCode == RESULT_CANCELED && data != null) {
            int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            if (appWidgetId != -1) {
                mAppWidgetHost.deleteAppWidgetId(appWidgetId);
            }
        }
    }
    private void configureWidget(Intent data) {
        Bundle extras = data.getExtras();
        int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
        if (appWidgetInfo.configure != null) {
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            intent.setComponent(appWidgetInfo.configure);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivityForResult(intent, R.id.REQUEST_CREATE_APPWIDGET);
        } else {
            createWidget(data);
        }
    }

    public void createWidget(Intent data) {
        Bundle extras = data.getExtras();
        int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
        AppWidgetHostView hostView = mAppWidgetHost.createView(this, appWidgetId, appWidgetInfo);
        hostView.setAppWidget(appWidgetId, appWidgetInfo);
        homeView.addView(hostView);
        slidingDrawer.bringToFront();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAppWidgetHost.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAppWidgetHost.stopListening();
    }


    public void setPacs(){
        Intent mainInt = new Intent(Intent.ACTION_MAIN,null);
        mainInt.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> pacList = pm.queryIntentActivities(mainInt,0);
        pacs = new Pac[pacList.size()];
        for(int i = 0; i < pacList.size(); i++){

            Pac p  = new Pac();
            ResolveInfo in = pacList.get(i);

            p.icon = in.loadIcon(pm);
            p.label = in.loadLabel(pm).toString();
            p.enabled = in.activityInfo.isEnabled();
            p.packageName = in.activityInfo.packageName;
            p.name = in.activityInfo.name;
            pacs[i] = p;
        }
        Sorter.sort(pacs);
        drawerAdapter = new DrawerAdapter(this, pacs);
        drawerGrid.setAdapter(drawerAdapter);

        drawerGrid.setOnItemClickListener(new DrawerClickListener(this, slidingDrawer,pm, pacs));
        drawerGrid.setOnItemLongClickListener(new DrawerLongClickListener(this,slidingDrawer,homeView,pacs));
    }



}
