package ranger.eyewer.com.rangerapp.Main;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ranger.eyewer.com.rangerapp.Adapter.DashboardPagerAdapter;
import ranger.eyewer.com.rangerapp.R;

public class Dashboard extends AppCompatActivity {

    RelativeLayout mapButton, panicButton, infoButton, profileButton;
    ImageView mapButton_icon, panicButton_icon, infoButton_icon, profileButton_icon;
    ViewPager dashboardPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        android.support.v7.app.ActionBar actionBar = Dashboard.this.getSupportActionBar();
        actionBar.hide();

        dashboardPager = (ViewPager) findViewById(R.id.dashboardPager);
        mapButton = (RelativeLayout) findViewById(R.id.mapButton);
        panicButton = (RelativeLayout) findViewById(R.id.panicButton);
        infoButton = (RelativeLayout) findViewById(R.id.infoButton);
        profileButton = (RelativeLayout) findViewById(R.id.profileButton);
        mapButton_icon = (ImageView) findViewById(R.id.mapButton_icon);
        panicButton_icon = (ImageView) findViewById(R.id.panicButton_icon);
        infoButton_icon = (ImageView) findViewById(R.id.infoButton_icon);
        profileButton_icon = (ImageView) findViewById(R.id.profileButton_icon);

        DashboardPagerAdapter adapter = new DashboardPagerAdapter(getSupportFragmentManager());
        dashboardPager.setAdapter(adapter);
        dashboardPager.setOffscreenPageLimit(4);

        //region OnTouch Code

        mapButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mapButton.setBackgroundColor(Color.parseColor("#F2F2F2"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        mapButton.setBackgroundColor(Color.parseColor("#ffffff"));
                        break;
                    case MotionEvent.ACTION_UP:
                        mapButton.setBackgroundColor(Color.parseColor("#ffffff"));

                        mapButton_icon.setImageResource(R.drawable.map_selected);
                        panicButton_icon.setImageResource(R.drawable.help_icon);
                        infoButton_icon.setImageResource(R.drawable.info_icon);
                        profileButton_icon.setImageResource(R.drawable.profile_icon);

                        dashboardPager.setCurrentItem(0);

                        break;
                }
                return true;
            }
        });

        panicButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        panicButton.setBackgroundColor(Color.parseColor("#F2F2F2"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        panicButton.setBackgroundColor(Color.parseColor("#ffffff"));
                        break;
                    case MotionEvent.ACTION_UP:
                        panicButton.setBackgroundColor(Color.parseColor("#ffffff"));

                        mapButton_icon.setImageResource(R.drawable.map_icon);
                        panicButton_icon.setImageResource(R.drawable.help_icon_selected);
                        infoButton_icon.setImageResource(R.drawable.info_icon);
                        profileButton_icon.setImageResource(R.drawable.profile_icon);

                        dashboardPager.setCurrentItem(1);

                        break;
                }
                return true;
            }
        });

        infoButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        infoButton.setBackgroundColor(Color.parseColor("#F2F2F2"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        infoButton.setBackgroundColor(Color.parseColor("#ffffff"));
                        break;
                    case MotionEvent.ACTION_UP:
                        infoButton.setBackgroundColor(Color.parseColor("#ffffff"));

                        mapButton_icon.setImageResource(R.drawable.map_icon);
                        panicButton_icon.setImageResource(R.drawable.help_icon);
                        infoButton_icon.setImageResource(R.drawable.info_icon_selected);
                        profileButton_icon.setImageResource(R.drawable.profile_icon);

                        dashboardPager.setCurrentItem(2);

                        break;
                }
                return true;
            }
        });

        profileButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        profileButton.setBackgroundColor(Color.parseColor("#F2F2F2"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        profileButton.setBackgroundColor(Color.parseColor("#ffffff"));
                        break;
                    case MotionEvent.ACTION_UP:
                        profileButton.setBackgroundColor(Color.parseColor("#ffffff"));

                        mapButton_icon.setImageResource(R.drawable.map_icon);
                        panicButton_icon.setImageResource(R.drawable.help_icon);
                        infoButton_icon.setImageResource(R.drawable.info_icon);
                        profileButton_icon.setImageResource(R.drawable.profile_selected);

                        dashboardPager.setCurrentItem(3);

                        break;
                }
                return true;
            }
        });

        //endregion

    }

}