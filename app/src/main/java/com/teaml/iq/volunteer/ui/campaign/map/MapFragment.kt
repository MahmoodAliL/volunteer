package com.teaml.iq.volunteer.ui.campaign.map

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import com.teaml.iq.volunteer.utils.AppConstants
import org.jetbrains.anko.find


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : BaseFragment(), OnMapReadyCallback {

    companion object {

        const val BUNDLE_KEY_LATITUDE = "bundle_key_latitude"
        const val BUNDLE_KEY_LONGITUDE = "bundle_key_longitude"

        val TAG: String = MapFragment::class.java.simpleName
        fun newInstance(bundle: Bundle = Bundle.EMPTY): MapFragment = MapFragment().apply {
            arguments = bundle
        }

        lateinit var googleMapView:MapView
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_map, container, false)

        with(activity as CampaignActivity) {
            supportActionBar?.title = getString(R.string.map)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
        }

        // google map setup
        googleMapView = view.find(R.id.googleMap)
        googleMapView.onCreate(savedInstanceState)
        googleMapView.getMapAsync(this)

        return view
    }

    override fun setup(view: View) {

    }

    override fun onResume() {
        googleMapView.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        googleMapView.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        arguments?.let {
            val latitude = it.getDouble(BUNDLE_KEY_LATITUDE)
            val longitude = it.getDouble(BUNDLE_KEY_LONGITUDE)

            val location = LatLng(latitude, longitude)
            val marker = MarkerOptions().position(location)

            googleMap?.let {
                it.addMarker(marker)
                it.animateCamera(CameraUpdateFactory.newLatLngZoom(location,AppConstants.MAP_ZOOM))
            }

        }
    }
}// Required empty public constructor
