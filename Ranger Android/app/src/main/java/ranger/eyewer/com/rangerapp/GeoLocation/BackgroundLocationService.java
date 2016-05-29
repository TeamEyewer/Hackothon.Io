package ranger.eyewer.com.rangerapp.GeoLocation;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import ranger.eyewer.com.rangerapp.Entity.PanicData;
import ranger.eyewer.com.rangerapp.Main.PanicModeViewer;
import ranger.eyewer.com.rangerapp.R;

public class BackgroundLocationService extends Service implements LocationListener{
    private static final String TAG = "EYERROR";
    private LocationManager mLocationManager = null;
    private static final int LOCATION_INTERVAL = 5000;
    private static final float LOCATION_DISTANCE = 0f;
    Location mLastLocation;

    private Socket mSocket;

    {
        try {
            mSocket = IO.socket("http://diluknodejs.azurewebsites.net/");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "onLocationChanged: " + location);
        mLastLocation.set(location);
        Gson gson = new Gson();
        try {
            PanicData data = new PanicData();
            data.setLat(String.valueOf(location.getLatitude()));
            data.setLon(String.valueOf(location.getLongitude()));
            data.setUserID("userrrrrrr");
            JSONObject obj = new JSONObject(gson.toJson(data));
            mSocket.emit("panic", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {

        mSocket.connect();
        mSocket.on("panic", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

                try {

                    Intent notificationIntent = new Intent(getApplicationContext(), PanicModeViewer.class);
                    // set intent so it does not start a new activity
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent intent =
                            PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

                    final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                    final Notification notification = builder
                            .setContentTitle("Broadcasting Location")
                            .setContentText("Ranger Panic Mode activated. Help is on the way")
                            .setContentIntent(intent)
                            .setSmallIcon(R.drawable.fire)
                            .setLights(Color.MAGENTA, 500, 500)
                            .build();
                    final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    notificationManager.notify(0x1234, notification);

                } catch (Exception ex) {
                    Log.d("EYERROR", ex.toString());
                }
            }
        });


        Log.e(TAG, "onCreate");
        initializeLocationManager();
        try {
            GeoLocation geo = new GeoLocation(this);

           LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

           boolean  isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

           boolean  isNetworked = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if(isGpsEnabled){

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
            }
            else if(isNetworked){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);

            }
            else {

                //cannot get anything
            }



           // mLocationManager.requestLocationUpdates(
              //      LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
             //       mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        /*
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
        */
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                  //  mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}