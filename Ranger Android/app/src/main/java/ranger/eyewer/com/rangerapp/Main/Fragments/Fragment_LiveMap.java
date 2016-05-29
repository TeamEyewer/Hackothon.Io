package ranger.eyewer.com.rangerapp.Main.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ranger.eyewer.com.rangerapp.Entity.Danger;
import ranger.eyewer.com.rangerapp.Entity.Pin;
import ranger.eyewer.com.rangerapp.GeoLocation.GeoLocation;
import ranger.eyewer.com.rangerapp.Main.CreatePin.ChoosePinType;
import ranger.eyewer.com.rangerapp.R;

public class Fragment_LiveMap extends Fragment implements OnMapReadyCallback {

    TextToSpeech tts;
    GoogleMap mMap;
    TextView greetingTitle, greetingDesc;
    ArrayList<Pin> pinList = new ArrayList<>();
    ArrayList<Marker> markers = new ArrayList<>();
    SlidingUpPanelLayout sliding_layout;
    TextView danger_title, danger_address, danger_advice;
    ImageView danger_icon;
    RelativeLayout add_btn, refresh_btn, speak_btn, explain_btn;
    String currSpeech = "", currSpeech_long = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment__live_map, container, false);
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    return true;
                }
                return false;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Typeface face_light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/robotolight.ttf");
        Typeface face_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/robotobold.ttf");


        tts=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        add_btn = (RelativeLayout) rootView.findViewById(R.id.add_btn);
        speak_btn = (RelativeLayout) rootView.findViewById(R.id.speak_btn);
        refresh_btn = (RelativeLayout) rootView.findViewById(R.id.refresh_btn);
        greetingTitle = (TextView) rootView.findViewById(R.id.greetingTitle);
        greetingDesc = (TextView) rootView.findViewById(R.id.greetingDesc);
        sliding_layout = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
        danger_title = (TextView) rootView.findViewById(R.id.danger_title);
        danger_address = (TextView) rootView.findViewById(R.id.danger_address);
        danger_advice = (TextView) rootView.findViewById(R.id.danger_advice);
        danger_icon = (ImageView) rootView.findViewById(R.id.danger_icon);
        explain_btn = (RelativeLayout) rootView.findViewById(R.id.explain_btn);

        greetingTitle.setTypeface(face_light);
        greetingDesc.setTypeface(face_light);
        danger_title.setTypeface(face_light);
        danger_address.setTypeface(face_light);
        danger_advice.setTypeface(face_light);


        //region Hardcoded

        /*

        Danger fire = new Danger();
        fire.setName("Fire");

        Danger flood = new Danger();
        flood.setName("Flood");

        Danger accident = new Danger();
        accident.setName("Accident");

        Pin pin = new Pin();
        pin.setLatitude("7.288658");
        pin.setLongitude("80.621871");
        pin.setDanger(fire);
        pinList.add(pin);

        pin = new Pin();
        pin.setLatitude("7.289179");
        pin.setLongitude("80.624682");
        pin.setDanger(flood);
        pinList.add(pin);

        pin = new Pin();
        pin.setLatitude("7.286816");
        pin.setLongitude("80.621238");
        pin.setDanger(accident);
        pinList.add(pin);

        */

        //endregion


        //region Location Updates

        new GetLocationUpdate("26", "6.918500", "79.861930").execute();

        /*
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        float minDist = 1;
        GeoLocation loca = new GeoLocation(getActivity());

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, minDist, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("EYERROR", "getting");
                Toast.makeText(getActivity(), "Cheking", Toast.LENGTH_LONG).show();
                //new GetLocationUpdate("1", location.getLatitude() + "", location.getLongitude() + "").execute();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });

        */


        //endregion


        //region OnTouch

        add_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        add_btn.setBackgroundResource(R.drawable.circle_red_touched);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        add_btn.setBackgroundResource(R.drawable.circle_red);
                        break;
                    case MotionEvent.ACTION_UP:
                        add_btn.setBackgroundResource(R.drawable.circle_red);

                        Intent intent = new Intent(getActivity(), ChoosePinType.class);
                        startActivity(intent);

                        break;
                }

                return true;
            }
        });

        speak_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        speak_btn.setBackgroundResource(R.drawable.circle_red_touched);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        speak_btn.setBackgroundResource(R.drawable.circle_red);
                        break;
                    case MotionEvent.ACTION_UP:
                        speak_btn.setBackgroundResource(R.drawable.circle_red);

                        tts.speak(currSpeech, TextToSpeech.QUEUE_FLUSH, null);

                        break;
                }

                return true;
            }
        });

        refresh_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        refresh_btn.setBackgroundResource(R.drawable.circle_red_touched);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        refresh_btn.setBackgroundResource(R.drawable.circle_red);
                        break;
                    case MotionEvent.ACTION_UP:
                        refresh_btn.setBackgroundResource(R.drawable.circle_red);

                        mMap.clear();

                        new GetLocationUpdate("26", "6.918500", "79.861930").execute();

                        break;
                }

                return true;
            }
        });

        explain_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        explain_btn.setBackgroundResource(R.drawable.circle_red_touched);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        explain_btn.setBackgroundResource(R.drawable.circle_red);
                        break;
                    case MotionEvent.ACTION_UP:
                        explain_btn.setBackgroundResource(R.drawable.circle_red);

                        tts.speak(currSpeech_long, TextToSpeech.QUEUE_FLUSH, null);

                        break;
                }

                return true;
            }
        });

        //endregion

        return rootView;
    }

    public void onPause(){
        if(tts !=null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        SetDangerPins();
    }

    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            if (loc != null) {
                Log.d("EYERROR", "getting");
                Toast.makeText(getActivity(), "Checking", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onProviderDisabled(String arg0) {
            // Do something here if you would like to know when the provider is disabled by the user
        }

        @Override
        public void onProviderEnabled(String arg0) {
            // Do something here if you would like to know when the provider is enabled by the user
        }

        @Override
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
            // Do something here if you would like to know when the provider status changes
        }
    }

    class GetLocationUpdate extends AsyncTask<Void, Void, Void> {

        String userid = "";
        String lat = "";
        String lon = "";

        GetLocationUpdate(String userid, String lat, String lon) {
            this.userid = userid;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                URL url = new URL("https://rangermobile.azure-mobile.net/api/getpins?userid=" + userid + "&lat=" + lat + "&lon=" + lon + "&radiusinmeters=" + 5000000);

                HttpURLConnection urlRequest = (HttpURLConnection) url.openConnection();
                urlRequest.setRequestMethod("GET");
                urlRequest.addRequestProperty("Content-Type", "application/json");
                urlRequest.addRequestProperty("ACCEPT", "application/json");
                urlRequest.addRequestProperty("X-ZUMO-APPLICATION", "XUvcuaepHpjunJyZaPscMDkXOUNFZk78");
                urlRequest.connect();
                int responseCode = urlRequest.getResponseCode();
                //Log.d("EYERROR", "LOGIN: " + responseCode);
                if (responseCode == 200) {

                    InputStream in = new BufferedInputStream(urlRequest.getInputStream());
                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));

                    StringBuilder responseString = new StringBuilder();
                    String line;

                    while ((line = bufferReader.readLine()) != null) {
                        responseString.append(line);
                    }

                    JSONObject mobj = new JSONObject(responseString.toString());

                    //Log.d("EYERROR", "LOGIN: " + responseString.toString());

                    //Log.d("EYERROR", "BADU AWA");

                    JSONArray pinArray = mobj.getJSONArray("pins");

                    if ((pinArray.length() != 0)) {
                        for (int i = 0; i < pinArray.length(); i++) {
                            JSONObject pinObj = pinArray.getJSONObject(i);
                            Pin pin = new Pin();
                            pin.setID(pinObj.getString("pinId"));
                            pin.setLatitude(pinObj.getString("lat"));
                            pin.setLongitude(pinObj.getString("lon"));
                            pin.setDistance(pinObj.getString("distance"));
                            pin.setGroup(pinObj.getString("group"));
                            pin.setPinCount(pinObj.getInt("pinCount"));

                            Danger danger = new Danger();
                            danger.setID(pinObj.getJSONObject("dangerObj").getString("id"));
                            danger.setName(pinObj.getJSONObject("dangerObj").getString("dangerName"));
                            //danger.setID(pinObj.getJSONObject("dangerObj").getString("id"));
                            pin.setDanger(danger);
                            pinList.add(pin);
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SetDangerPins();
                            }
                        });

                    }

                }


            } catch (Exception ex) {
                Log.d("EYERROR", ex.toString());
            }

            return null;
        }

    }

    private String GetAddressFromMap(double latitude, double longitude) {

        String address = "";

        try {
            Geocoder geo = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
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

    private void SetDangerPins() {

        for (Pin pin : pinList) {
            LatLng latlng = new LatLng(Double.parseDouble(pin.getLatitude()), Double.parseDouble(pin.getLongitude()));

            String iconName = "";

            switch (pin.getDanger().getName()) {
                case "Fire":
                    iconName = "fire";
                    break;
                case "Flood":
                    iconName = "flood";
                    break;
                case "Accident":
                    iconName = "accident";
                    break;
                case "Landslide":
                    iconName = "landslide";
                    break;
                case "Damanged Roadway":
                    iconName = "roadway";
                    break;
                case "Robbery":
                    iconName = "robbery";
                    break;
            }

            Marker newMarker = mMap.addMarker(new MarkerOptions()
                    .position(latlng)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(iconName, 190, 190))));

            markers.add(newMarker);

            mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));

        }

        if (pinList.size() == 0) {
            greetingTitle.setText("You are Safe, Dinuka.");
            greetingDesc.setText("Ranger will let you know when there's a danger in your area");
            currSpeech = "You are Safe. We will let you know if there is a danger in your area";
        } else {
            if (pinList.size() == 1) {
                greetingTitle.setText("There's a danger in your area");
                greetingDesc.setText("Click on any Danger pin for more information");
                currSpeech = "There's a danger in your area. Be careful!";
            } else {
                greetingTitle.setText("There's " + pinList.size() + " dangers in your area");
                greetingDesc.setText("Click on any Danger pin for more information");
                currSpeech = "There's " + pinList.size() + " dangers in your area. Be very Careful!";
            }
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (Pin pin : pinList) {
                    if (Double.parseDouble(pin.getLatitude()) == marker.getPosition().latitude && Double.parseDouble(pin.getLongitude()) == marker.getPosition().longitude) {

                        String dangerName = pin.getDanger().getName();
                        String address = GetAddressFromMap(marker.getPosition().latitude, marker.getPosition().longitude);

                        danger_title.setText(dangerName);
                        danger_address.setText(address);

                        String val = "";

                        switch (pin.getDanger().getName()) {
                            case "Fire":
                                danger_icon.setImageResource(R.drawable.fire);
                                val = "A fire was reported at " + address + ". It's best adviced to stay away from this area until the situation changes";
                                danger_advice.setText(val);
                                currSpeech_long = val;
                                break;
                            case "Flood":
                                danger_icon.setImageResource(R.drawable.flood);
                                val = "A flood was reported at " + address + ". Surrounding areas could also be affected. Please stay away from this position.";
                                danger_advice.setText(val);
                                currSpeech_long = val;
                                break;
                            case "Accident":
                                danger_icon.setImageResource(R.drawable.accident);
                                val = "An accident was reported at " + address + ". Heavy traffic could be caused by this.";
                                danger_advice.setText(val);
                                currSpeech_long = val;
                                break;
                            case "Landslide":
                                danger_icon.setImageResource(R.drawable.landslide);
                                val = "A landslide was reported at " + address + ". Heavy traffic and difficulty of driving could be caused by this.";
                                danger_advice.setText(val);
                                currSpeech_long = val;
                                break;
                            case "Damaged Roadway":
                                danger_icon.setImageResource(R.drawable.landslide);
                                val = "A fire was reported at " + address + ". It's best adviced to stay away from this area until the situation changes";
                                danger_advice.setText(val);
                                currSpeech_long = val;
                                break;
                            case "Robbery":
                                danger_icon.setImageResource(R.drawable.landslide);
                                val = "A fire was reported at " + address + ". It's best adviced to stay away from this area until the situation changes";
                                danger_advice.setText(val);
                                currSpeech_long = val;
                                break;
                        }

                        sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                    }
                }
                return true;
            }
        });


    }

    public Bitmap resizeMapIcons(String iconName, int width, int height) {
          Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
          Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
          return resizedBitmap;
    }

}
