package ranger.eyewer.com.rangerapp.Main.Fragments;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import ranger.eyewer.com.rangerapp.Entity.PanicData;
import ranger.eyewer.com.rangerapp.GeoLocation.BackgroundLocationService;
import ranger.eyewer.com.rangerapp.GeoLocation.GeoLocation;
import ranger.eyewer.com.rangerapp.Main.Dashboard;
import ranger.eyewer.com.rangerapp.Main.UpdateContactsView;
import ranger.eyewer.com.rangerapp.R;

public class Fragment_PanicView extends Fragment {

    TextView title, desc, panicFamily, panicFamily_edit;
    RelativeLayout panicBtn;

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://diluknodejs.azurewebsites.net/");
        } catch (URISyntaxException e) {
            Log.d("EYERROR", e.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        mSocket.on("panic", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
                final Notification notification = builder
                        .setContentTitle("Panic Alert Request")
                        .setContentText("Click here to start tracking their location and assist them")
                        .setSmallIcon(R.drawable.fire)
                        .setLights(Color.MAGENTA, 500, 500)
                        .build();
                final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
                notificationManager.notify(0x1234, notification);
            }
        });*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment__panic_view, container, false);
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {

                    return true;
                }
                return false;
            }
        });



        Typeface face_light = Typeface.createFromAsset(getActivity().getAssets(), "fonts/robotolight.ttf");
        Typeface face_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/robotobold.ttf");

        title = (TextView) rootView.findViewById(R.id.title);
        desc = (TextView) rootView.findViewById(R.id.desc);
        panicFamily = (TextView) rootView.findViewById(R.id.panicFamily);
        panicFamily_edit = (TextView) rootView.findViewById(R.id.panicFamily_edit);
        panicBtn = (RelativeLayout) rootView.findViewById(R.id.panicBtn);

        title.setTypeface(face_light);
        desc.setTypeface(face_light);
        panicFamily.setTypeface(face_light);
        panicFamily_edit.setTypeface(face_light);

        panicFamily_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateContactsView.class);
                startActivityForResult(intent, 93);
            }
        });

        SetEmergencyContactsText();

        panicBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        panicBtn.setBackgroundResource(R.drawable.insta_button_20pad_touched);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        panicBtn.setBackgroundResource(R.drawable.insta_button_20pad);
                        break;
                    case MotionEvent.ACTION_UP:
                        panicBtn.setBackgroundResource(R.drawable.insta_button_20pad);
                        //StartPanicMode();
                        //SendData();



                        break;
                }
                return true;
            }
        });

        return rootView;
    }



    private void StartPanicMode() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        final Notification notification = builder
                .setContentTitle("Broadcasting Location")
                .setContentText("Ranger Panic Mode activated. Help is on the way")
                .setSmallIcon(R.drawable.fire)
                .setLights(Color.MAGENTA, 500, 500)
                .build();
        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        notificationManager.notify(0x1234, notification);

        //region Tracking Code

        /*
        float minDist = 1;
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub
            }
            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
                // TODO Auto-generated method stub
            }
        });
        */

        /*
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        GeoLocation loca = new GeoLocation(getActivity());

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, minDist, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                builder
                        .setContentText(location.getLatitude() + ", " + location.getLongitude());
                notificationManager.notify(0x1234, notification);


                //Log.d("EYERROR", "getting");
                //Toast.makeText(getActivity(), "Cheking Ado", Toast.LENGTH_LONG).show();
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

        //attemptSend();

        //endregion

    }

    private void SendData() {

        mSocket.emit("panic", "panic");

        //getActivity().startService(new Intent(getActivity(), BackgroundLocationService.class));

        /*
        Gson gson = new Gson();
        try {
            PanicData data = new PanicData();
            data.setLat("latiiiii");
            data.setLon("longgggg");
            data.setUserID("userrrrrrr");
            JSONObject obj = new JSONObject(gson.toJson(data));
            mSocket.emit("panic", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getActivity(), "Sent", Toast.LENGTH_SHORT).show();
        */

    }

    private void SetEmergencyContactsText() {
        SharedPreferences contactData = getActivity().getSharedPreferences("contactData", Context.MODE_PRIVATE);
        String contactsString = "";
        if (contactData.getString("contact_1_name", "").length() != 0) {
            if (contactsString.length() != 0) {
                contactsString = contactsString + ", " + contactData.getString("contact_1_name", "");
            } else {
                contactsString = contactData.getString("contact_1_name", "");
            }
        }
        if (contactData.getString("contact_2_name", "").length() != 0) {
            if (contactsString.length() != 0) {
                contactsString = contactsString + ", " + contactData.getString("contact_2_name", "");
            } else {
                contactsString = contactData.getString("contact_2_name", "");
            }
        }
        if (contactData.getString("contact_3_name", "").length() != 0) {
            if (contactsString.length() != 0) {
                contactsString = contactsString + ", " + contactData.getString("contact_3_name", "");
            } else {
                contactsString = contactData.getString("contact_3_name", "");
            }
        }
        if (contactData.getString("contact_4_name", "").length() != 0) {
            if (contactsString.length() != 0) {
                contactsString = contactsString + ", " + contactData.getString("contact_4_name", "");
            } else {
                contactsString = contactData.getString("contact_4_name", "");
            }
        }
        if (contactData.getString("contact_5_name", "").length() != 0) {
            if (contactsString.length() != 0) {
                contactsString = contactsString + ", " + contactData.getString("contact_5_name", "");
            } else {
                contactsString = contactData.getString("contact_5_name", "");
            }
        }

        if (contactsString.length() != 0) {
            contactsString = "<font color=\"#FF5A60\"><b>" + contactsString + "</b></font>";
            panicFamily.setText(Html.fromHtml("Starting Panic Mode will alert " + contactsString + " about your location"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 93) {
            SetEmergencyContactsText();
        }

    }

}


