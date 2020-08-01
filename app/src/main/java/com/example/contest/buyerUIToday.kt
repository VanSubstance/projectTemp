package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.UiSettings
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource


class buyerUIToday : Fragment(), OnMapReadyCallback {

    private val ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100
    private var locationSource: FusedLocationSource? = null
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_today, container, false)

        val fm = childFragmentManager

        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()

            }
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onMapReady(naverMap: NaverMap) {
        locationSource = FusedLocationSource(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.setLocationSource(locationSource);
        val uiSettings: UiSettings = naverMap.uiSettings
        uiSettings.setLocationButtonEnabled(true)

        var marketList : ArrayList<Marker> = arrayListOf()

        var marker = Marker()
        marker.position = LatLng(37.645327, 127.022302)
        marker.map = naverMap

        var data = database.getReference("MarkerInfo")
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (market in p0.children) {
                    var marketTitle = market.child("전통시장명").value.toString()
                    var marker = Marker()
                    marker.position = LatLng(market.child("위도").value.toString().toDouble(), market.child("경도").value.toString().toDouble())
                    marker.map = naverMap
                    marketList.add(marker)
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACCESS_LOCATION_PERMISSION_REQUEST_CODE -> {
                locationSource!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
                return
            }
        }
    }

}