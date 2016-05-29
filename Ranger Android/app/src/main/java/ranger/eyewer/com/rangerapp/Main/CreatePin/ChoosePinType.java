package ranger.eyewer.com.rangerapp.Main.CreatePin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ranger.eyewer.com.rangerapp.R;

public class ChoosePinType extends AppCompatActivity {

    TextView title, desc, topbar_title;
    TextView danger_fire_title, danger_accident_title, danger_flood_title, danger_robbery_title, danger_road_title, danger_earthquake_title, danger_bomb_title, danger_tree_title, danger_tsunami_title, danger_physical_title, danger_earthslide_title, danger_planecrash_title;
    RelativeLayout danger_fire, danger_accident, danger_flood, danger_robbery, danger_road, danger_earthquake, danger_bomb, danger_tsunami, danger_tree, danger_physical, danger_earthslide, danger_planecrash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_pin_type);

        ActionBar actionBar = ChoosePinType.this.getSupportActionBar();
        actionBar.hide();

        title = (TextView) findViewById(R.id.title);
        desc = (TextView) findViewById(R.id.desc);
        topbar_title = (TextView) findViewById(R.id.topbar_title);
        danger_fire_title = (TextView) findViewById(R.id.danger_fire_title);
        danger_accident_title = (TextView) findViewById(R.id.danger_accident_title);
        danger_flood_title = (TextView) findViewById(R.id.danger_flood_title);
        danger_robbery_title = (TextView) findViewById(R.id.danger_robbery_title);
        danger_road_title = (TextView) findViewById(R.id.danger_road_title);
        danger_earthquake_title = (TextView) findViewById(R.id.danger_earthquake_title);
        danger_bomb_title = (TextView) findViewById(R.id.danger_bomb_title);
        danger_tree_title = (TextView) findViewById(R.id.danger_tree_title);
        danger_tsunami_title = (TextView) findViewById(R.id.danger_tsunami_title);
        danger_physical_title = (TextView) findViewById(R.id.danger_physical_title);
        danger_earthslide_title = (TextView) findViewById(R.id.danger_earthslide_title);
        danger_planecrash_title = (TextView) findViewById(R.id.danger_planecrash_title);

        danger_fire = (RelativeLayout) findViewById(R.id.danger_fire);
        danger_accident = (RelativeLayout) findViewById(R.id.danger_accident);
        danger_flood = (RelativeLayout) findViewById(R.id.danger_flood);
        danger_robbery = (RelativeLayout) findViewById(R.id.danger_robbery);
        danger_road = (RelativeLayout) findViewById(R.id.danger_road);
        danger_earthquake = (RelativeLayout) findViewById(R.id.danger_earthquake);
        danger_bomb = (RelativeLayout) findViewById(R.id.danger_bomb);
        danger_tree = (RelativeLayout) findViewById(R.id.danger_tree);
        danger_tsunami = (RelativeLayout) findViewById(R.id.danger_tsunami);
        danger_physical = (RelativeLayout) findViewById(R.id.danger_physical);
        danger_earthslide = (RelativeLayout) findViewById(R.id.danger_earthslide);
        danger_planecrash = (RelativeLayout) findViewById(R.id.danger_planecrash);

        Typeface face_light = Typeface.createFromAsset(getAssets(), "fonts/robotolight.ttf");

        title.setTypeface(face_light);
        desc.setTypeface(face_light);
        topbar_title.setTypeface(face_light);

        danger_fire_title.setTypeface(face_light);
        danger_accident_title.setTypeface(face_light);
        danger_flood_title.setTypeface(face_light);

        //region OnTouch

        danger_fire.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_fire.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_fire.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_fire.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "fire");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_accident.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_accident.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_accident.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_accident.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "accident");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_flood.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_flood.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_flood.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_flood.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "flood");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_robbery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_robbery.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_robbery.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_robbery.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "robbery");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_road.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_road.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_road.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_road.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "road");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_earthquake.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_earthquake.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_earthquake.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_earthquake.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "earthquake");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_bomb.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_bomb.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_bomb.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_bomb.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "bomb");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_tree.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_tree.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_tree.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_tree.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "tree");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_tsunami.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_tsunami.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_tsunami.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_tsunami.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "tsunami");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_physical.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_physical.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_physical.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_physical.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "abuse");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_earthslide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_earthslide.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_earthslide.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_earthslide.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "earthslide");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        danger_planecrash.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        danger_planecrash.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        danger_planecrash.setBackgroundColor(Color.parseColor("#F0F0F0"));
                        break;
                    case MotionEvent.ACTION_UP:
                        danger_planecrash.setBackgroundColor(Color.parseColor("#ffffff"));

                        Intent intent = new Intent(ChoosePinType.this, SetPinView.class);
                        intent.putExtra("type", "planecrash");
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        //endregion


    }
}
