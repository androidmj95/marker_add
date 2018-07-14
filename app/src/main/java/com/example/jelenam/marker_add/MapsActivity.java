package com.example.jelenam.marker_add;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_addmarkers:
                addMarker();
                return true;

            case R.id.info:
                Toast.makeText(this,"Nesto onako",Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void addMarker(){

        if (mMap != null){
            LinearLayout layout = new LinearLayout(MapsActivity.this);
            layout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.setOrientation(LinearLayout.VERTICAL);

            final EditText titleField = new EditText(MapsActivity.this);
            titleField.setHint("Title");

            final EditText latField = new EditText(MapsActivity.this);
            latField.setHint("Latitude");
            latField.setInputType(InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_NUMBER_FLAG_DECIMAL
                    | InputType.TYPE_NUMBER_FLAG_SIGNED);

            final EditText lonField = new EditText(MapsActivity.this);
            lonField.setHint("Longitude");
            lonField.setInputType(InputType.TYPE_CLASS_NUMBER
                    | InputType.TYPE_NUMBER_FLAG_DECIMAL
                    | InputType.TYPE_NUMBER_FLAG_SIGNED);

            layout.addView(titleField);
            layout.addView(latField);
            layout.addView(lonField);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add Marker");
            builder.setView(layout);
            AlertDialog alertDialog = builder.create();

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean parsable = true;
                    Double lat = null, lon = null;

                    String strLat = latField.getText().toString();
                    String strLon = lonField.getText().toString();
                    String strTitle = titleField.getText().toString();

                    try{
                        lat = Double.parseDouble(strLat);
                    }catch (NumberFormatException ex){
                        parsable = false;
                        Toast.makeText(MapsActivity.this,
                                "Latitude does not contain a parsable double",
                                Toast.LENGTH_LONG).show();
                    }

                    try{
                        lon = Double.parseDouble(strLon);
                    }catch (NumberFormatException ex){
                        parsable = false;
                        Toast.makeText(MapsActivity.this,
                                "Longitude does not contain a parsable double",
                                Toast.LENGTH_LONG).show();
                    }


                    if(parsable){
                        LatLng targetLatLng = new LatLng(lat, lon);
                        MarkerOptions markerOptions =
                                new MarkerOptions().position(targetLatLng).title(strTitle);
                        mMap.addMarker(markerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(targetLatLng));
                    }

                }
            });

            builder.setNegativeButton("Cancel", null);

            builder.show();
        }else {
            Toast.makeText(MapsActivity.this, "Map not ready", Toast.LENGTH_LONG).show();
        }

    }




}
