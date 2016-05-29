package ranger.eyewer.com.rangerapp.Main.CreatePin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import ranger.eyewer.com.rangerapp.Main.Dashboard;
import ranger.eyewer.com.rangerapp.R;

public class SetPinView extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    String type;
    Marker newMarker;
    TextView title, desc, save_btn_title;
    RelativeLayout save_btn, socialMediaView;
    Boolean isSaveEnabled = false;
    String userid, dangerid;
    CheckBox facebookBox;
    String danger;
    TextView social_warning, social_desc;
    ProgressBar save_progress;
    RelativeLayout duplicateModal, modal_yes, modal_no;

    String response_pinid, response_groupid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin_view);
        type = getIntent().getStringExtra("type");

        userid = "26";

        ActionBar actionBar = SetPinView.this.getSupportActionBar();
        actionBar.hide();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        duplicateModal = (RelativeLayout) findViewById(R.id.duplicateModal);
        save_progress = (ProgressBar) findViewById(R.id.save_progress);
        facebookBox = (CheckBox) findViewById(R.id.facebookBox);
        title = (TextView) findViewById(R.id.title);
        desc = (TextView) findViewById(R.id.desc);
        save_btn = (RelativeLayout) findViewById(R.id.save_btn);
        social_warning = (TextView) findViewById(R.id.social_warning);
        social_desc = (TextView) findViewById(R.id.social_desc);
        socialMediaView = (RelativeLayout) findViewById(R.id.socialMediaView);
        save_btn_title = (TextView) findViewById(R.id.save_btn_title);
        modal_yes = (RelativeLayout) findViewById(R.id.modal_yes);
        modal_no = (RelativeLayout) findViewById(R.id.modal_no);

        save_progress.setVisibility(View.GONE);
        save_btn_title.setVisibility(View.VISIBLE);
        duplicateModal.setVisibility(View.GONE);

        Typeface face_light = Typeface.createFromAsset(getAssets(), "fonts/robotolight.ttf");

        title.setTypeface(face_light);
        desc.setTypeface(face_light);


        modal_yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        modal_yes.setBackgroundColor(Color.parseColor("#E3E3E3"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        modal_yes.setBackgroundColor(Color.parseColor("#E3E3E3"));
                        break;
                    case MotionEvent.ACTION_UP:
                        modal_yes.setBackgroundColor(Color.parseColor("#FFFFFF"));

                        //Call Update Method
                        new UpdatePin(response_pinid, response_groupid).execute();

                        break;
                }

                return true;
            }
        });

        modal_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        modal_no.setBackgroundColor(Color.parseColor("#E3E3E3"));
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        modal_no.setBackgroundColor(Color.parseColor("#E3E3E3"));
                        break;
                    case MotionEvent.ACTION_UP:
                        modal_no.setBackgroundColor(Color.parseColor("#FFFFFF"));

                        duplicateModal.setVisibility(View.GONE);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                save_progress.setVisibility(View.GONE);
                                save_btn_title.setVisibility(View.VISIBLE);
                            }
                        });

                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        save_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (isSaveEnabled) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            save_btn.setBackgroundColor(Color.parseColor("#ED5359"));
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            save_btn.setBackgroundColor(Color.parseColor("#F0F0F0"));
                            break;
                        case MotionEvent.ACTION_UP:
                            save_btn.setBackgroundColor(Color.parseColor("#FF5A60"));

                            save_progress.setVisibility(View.VISIBLE);
                            save_btn_title.setVisibility(View.GONE);

                            String lat = "" + newMarker.getPosition().latitude;
                            String lon = "" + newMarker.getPosition().longitude;

                            if (facebookBox.isChecked()) {

                                social_warning.setText(danger.toUpperCase() + " WARNING");

                                Bitmap b = Bitmap.createBitmap(200, 300, Bitmap.Config.ARGB_8888);
                                Canvas c = new Canvas(b);
                                socialMediaView.layout(0, 0, socialMediaView.getLayoutParams().width, socialMediaView.getLayoutParams().height);
                                socialMediaView.draw(c);
                                savePicture(b, "ranger_" + danger + new Random().nextInt());

                            }

                            new CreatePin(userid, lat, lon, dangerid).execute();

                            break;
                    }
                }

                return true;
            }
        });


    }

    private void savePicture(Bitmap bm, String imgName) {
        OutputStream fOut = null;
        String strDirectory = Environment.getExternalStorageDirectory().toString();

        File f = new File(strDirectory, imgName);
        try {
            fOut = new FileOutputStream(f);

            /**Compress image**/
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();

            /**Update image to gallery**/
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    f.getAbsolutePath(), f.getName(), f.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        LatLng location = getCurrentLocation(SetPinView.this);

        CameraUpdate center =
                CameraUpdateFactory.newLatLng(location);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);


        mMap.addCircle(new CircleOptions()
                .center(location)
                .radius(150)
                .strokeColor(Color.parseColor("#220084fd"))
                .fillColor(Color.parseColor("#220084fd")));


        //region OnClick

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                switch (type) {
                    case "fire":
                        dangerid = "3";
                        danger = "Fire";

                        if (newMarker == null) {

                            save_btn.setBackgroundColor(Color.parseColor("#FF5A60"));
                            isSaveEnabled = true;

                            newMarker = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .draggable(true)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("fire", 190, 190))));

                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        } else {
                            newMarker.remove();

                            newMarker = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .draggable(true)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("fire", 190, 190))));

                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                        break;

                    case "accident":
                        dangerid = "9";
                        danger = "accident";

                        if (newMarker == null) {

                            save_btn.setBackgroundColor(Color.parseColor("#FF5A60"));
                            isSaveEnabled = true;

                            newMarker = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .draggable(true)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("accident", 190, 190))));

                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        } else {
                            newMarker.remove();

                            newMarker = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .draggable(true)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("accident", 190, 190))));

                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                        break;

                    case "flood":
                        dangerid = "4";
                        danger = "flood";

                        if (newMarker == null) {

                            save_btn.setBackgroundColor(Color.parseColor("#FF5A60"));
                            isSaveEnabled = true;

                            newMarker = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .draggable(true)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("flood", 190, 190))));

                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        } else {
                            newMarker.remove();

                            newMarker = mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .draggable(true)
                                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("flood", 190, 190))));

                            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        }
                        break;

                }

            }
        });

        //endregion

    }


    private String GetAddressFromMap(double latitude, double longitude) {

        String address = "";

        try {
            Geocoder geo = new Geocoder(SetPinView.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.isEmpty()) {

            } else {
                if (addresses.size() > 0) {
                    address = addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }

        return address;
    }


    public LatLng getCurrentLocation(Context context) {
        try {
            LocationManager locMgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String locProvider = locMgr.getBestProvider(criteria, false);
            Location location = locMgr.getLastKnownLocation(locProvider);

            // getting GPS status
            boolean isGPSEnabled = locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            boolean isNWEnabled = locMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNWEnabled) {
                // no network provider is enabled
                return null;
            } else {
                // First get location from Network Provider
                if (isNWEnabled)
                    if (locMgr != null)
                        location = locMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled)
                    if (location == null)
                        if (locMgr != null)
                            location = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

            return new LatLng(location.getLatitude(), location.getLongitude());
        } catch (NullPointerException ne) {
            Log.e("Current Location", "Current Lat Lng is Null");
            return new LatLng(0, 0);
        } catch (Exception e) {
            e.printStackTrace();
            return new LatLng(0, 0);
        }
    }


    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }


    class CreatePin extends AsyncTask<Void, Void, Void> {

        String userid = "";
        String lat = "";
        String lon = "";
        String dangerid = "";

        CreatePin(String userid, String lat, String lon, String dangerid) {
            this.userid = userid;
            this.lat = lat;
            this.lon = lon;
            this.dangerid = dangerid;
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://rangermobile.azure-mobile.net/api/createpin");

            JSONObject jsonObject = new JSONObject();

            try {
                Log.d("EYERROR", "REQUEST: " + userid + " " + lat + " " + lon + " " + dangerid);
                jsonObject.accumulate("userid", userid);
                jsonObject.accumulate("latitude", lat);
                jsonObject.accumulate("longitude", lon);
                jsonObject.accumulate("dangerid", dangerid);

                String json = jsonObject.toString();

                StringEntity se = new StringEntity(json);

                httppost.setEntity(se);
                httppost.setHeader("Content-Type", "application/json; charset=utf-8");
                httppost.setHeader("X-ZUMO-APPLICATION", "XUvcuaepHpjunJyZaPscMDkXOUNFZk78"); //String.valueOf(R.string.mobileServiceAppId)
                HttpResponse httpResponse = httpclient.execute(httppost);

                final int responseCode = httpResponse.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonobj = new JSONObject(responseBody);

                Log.d("EYERROR", "RESPONSE: " + responseBody);

                if (responseCode == 200) {

                    Boolean hasNearByPins = jsonobj.getBoolean("hasNearByPins");

                    JSONObject nearbyPinObj = jsonobj.getJSONObject("nearByPins");

                    if (hasNearByPins) {

                        response_pinid = jsonobj.getString("basePinID");
                        response_groupid = nearbyPinObj.getString("group");

                        Log.d("EYERROR", response_pinid + " MACHOO " + response_groupid);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                duplicateModal.setVisibility(View.VISIBLE);

                            }
                        });

                    } else {

                        //String token = jsonobj.getString("token");
                        //Boolean isNewUser = jsonobj.getBoolean("isNewUser");

                        //JSONObject user = jsonobj.getJSONObject("user");
                        //String id = user.getString("id");
                        //Boolean confirmedNumber = user.getBoolean("confirmedNumber");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                save_progress.setVisibility(View.GONE);
                                save_btn_title.setVisibility(View.VISIBLE);
                            }
                        });

                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }

                }


            } catch (Exception ex) {
                Log.d("EYERROR", ex.toString());
            }

            return null;
        }

    }


    class UpdatePin extends AsyncTask<Void, Void, Void> {

        String pinid = "";
        String groupid = "";

        UpdatePin(String pinid, String groupid) {
            this.pinid = pinid;
            this.groupid = groupid;
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://rangermobile.azure-mobile.net/api/confirmpinlocation");

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.accumulate("pinid", pinid);
                jsonObject.accumulate("groupid", groupid);

                String json = jsonObject.toString();

                StringEntity se = new StringEntity(json);

                httppost.setEntity(se);
                httppost.setHeader("Content-Type", "application/json; charset=utf-8");
                httppost.setHeader("X-ZUMO-APPLICATION", "XUvcuaepHpjunJyZaPscMDkXOUNFZk78"); //String.valueOf(R.string.mobileServiceAppId)
                HttpResponse httpResponse = httpclient.execute(httppost);

                final int responseCode = httpResponse.getStatusLine().getStatusCode();
                String responseBody = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonobj = new JSONObject(responseBody);

                Log.d("EYERROR", "RESPONSE: " + responseBody);

                if (responseCode == 200) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            save_progress.setVisibility(View.GONE);
                            save_btn_title.setVisibility(View.VISIBLE);
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }


            } catch (Exception ex) {
                Log.d("EYERROR", ex.toString());
            }

            return null;
        }

    }

}
