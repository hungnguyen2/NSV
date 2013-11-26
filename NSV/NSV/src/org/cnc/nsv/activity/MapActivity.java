package org.cnc.nsv.activity;

import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.entity.Agent;
import org.cnc.nsv.entity.Contractor;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends android.support.v4.app.FragmentActivity implements Constant {

	ImageView ivBack;
	Agent agent;
	Contractor contractor;
	TextView tvTitle;
	String title = "", subTitle = "";
	float lat, lng;
	private GoogleMap mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		ivBack = (ImageView) findViewById(R.id.button_back);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		
		new createmenu().create(this);
		findViewById(R.id.menu_search).setSelected(true);
		
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		agent = (Agent) getIntent().getSerializableExtra("nsv-agent");
		contractor = (Contractor) getIntent().getSerializableExtra("nsv-contractor");
		
		if (agent != null) {
			title = agent.getName();
			subTitle = agent.getAddress();
			lat = agent.getGeoLat();
			lng = agent.getGeoLng();
		}
		if (contractor != null) {
			title = contractor.getName();
			subTitle = contractor.getAddress();
			lat = contractor.getGeoLat();
			lng = contractor.getGeoLng();
		}
		setUpMapIfNeeded();
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
	
	private void setUpMapIfNeeded() {
	        // Do a null check to confirm that we have not already instantiated the map.
	        if (mMap == null) {
	            // Try to obtain the map from the SupportMapFragment.
	            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
	                    .getMap();
	            // Check if we were successful in obtaining the map.
	            if (mMap != null) {
	                setUpMap();
	            }
	        }
	    }

	    /**
	     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
	     * just add a marker near Africa.
	     * <p>
	     * This should only be called once and when we are sure that {@link #mMap} is not null.
	     */
	    private void setUpMap() {
	    	mMap.addMarker(new MarkerOptions()
        	.position(new LatLng(lat, lng))
			.title(title).snippet(subTitle).visible(true));
	    	 CameraUpdate center=
			CameraUpdateFactory.newLatLng(new LatLng(lat,lng));
			CameraUpdate zoom = CameraUpdateFactory.zoomTo(14);
			mMap.moveCamera(center);
			mMap.animateCamera(zoom);
	    }
}
