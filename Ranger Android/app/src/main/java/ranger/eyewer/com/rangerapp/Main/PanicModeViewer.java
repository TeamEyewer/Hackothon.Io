package ranger.eyewer.com.rangerapp.Main;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.net.URISyntaxException;

import ranger.eyewer.com.rangerapp.R;

public class PanicModeViewer extends AppCompatActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    public Marker user;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://diluknodejs.azurewebsites.net/");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_mode_viewer);

        ActionBar actionBar = PanicModeViewer.this.getSupportActionBar();
        actionBar.hide();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.close();
        Toast.makeText(PanicModeViewer.this, "Closed",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSocket.close();
        Toast.makeText(PanicModeViewer.this, "Closed",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSocket.connect();
        mSocket.on("panic", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {

                    JSONObject data = (JSONObject) args[0];
                    String lat = data.getString("Lat");
                    String lon = data.getString("Lon");

                    DrawLocation(Double.parseDouble(lat), Double.parseDouble(lon));


                } catch (Exception ex) {
                    Log.d("EYERROR", ex.toString());
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mSocket.connect();
        mSocket.on("panic", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {

                    JSONObject data = (JSONObject) args[0];
                    String lat = data.getString("Lat");
                    String lon = data.getString("Lon");

                    DrawLocation(Double.parseDouble(lat), Double.parseDouble(lon));


                } catch (Exception ex) {
                    Log.d("EYERROR", ex.toString());
                }
            }
        });
    }

    public void DrawLocation(Double lat, Double lon) {
        Log.d("EYERROR", "I GOT: " + lat + " " + lon);
        final LatLng latlng = new LatLng(lat, lon);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable(){
            @Override
            public void run() {
                if (mMap != null) {
                    Log.d("EYERROR", "Map not null");
                    if (user != null) {
                        Log.d("EYERROR", "User not null");
                        user.setPosition(latlng);
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                    } else {
                        Log.d("EYERROR", "User null");
                        user = mMap.addMarker(new MarkerOptions()
                                .position(latlng)
                                .draggable(true)
                                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("fire", 190, 190))));

                        mMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
                    }
                }
            }
        });

    }

    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

}
