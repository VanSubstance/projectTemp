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
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.InfoWindow.DefaultTextAdapter
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource


class buyerUIToday : Fragment(), OnMapReadyCallback {

    private val ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100
    private var locationSource: FusedLocationSource? = null
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.buyer_ui_map, container, false)

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

        var marketList: ArrayList<Marker> = arrayListOf()
        var infoWindowList: ArrayList<InfoWindow> = arrayListOf()
        var data = database.getReference("marketDB")

        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {

                for (market in p0.children) {
                    var marketTitle = market.child("marketTitle").value.toString()
                    var marker = Marker()
                    if (market.child("latitude").value.toString()
                            .toDouble() != null && market.child("longitude").value.toString()
                            .toDouble() != null
                    ) {

                        marker.position = LatLng(
                            market.child("latitude").value.toString().toDouble(),
                            market.child("longitude").value.toString().toDouble()
                        )
                        marker.map = naverMap
                        marker.width = 70
                        marker.height = 100
                        marker.tag = marketTitle
                        marketList.add(marker)

                        var infoWindow = InfoWindow()
                        infoWindow.adapter = object : DefaultTextAdapter(context!!) {
                            override fun getText(infoWindow: InfoWindow): CharSequence {
                                return infoWindow.marker!!.tag as CharSequence
                            }
                        }
                        infoWindow.onClickListener = Overlay.OnClickListener { overlay: Overlay? ->
                            currentCondition.marketTitle = marketTitle
                            (activity as buyerUIMain).setBuyerFrag(22)
                            true
                        }
                        marker.onClickListener = Overlay.OnClickListener { overlay: Overlay? ->
                            for (infoWindow in infoWindowList) {
                                infoWindow.close()
                            }
                            infoWindow.open((overlay as Marker?)!!)
                            true
                        }
                        infoWindowList.add(infoWindow)
                    }
                }
            }
        })



        naverMap.setOnMapClickListener { pointF, latLng ->
            for (infoWindow in infoWindowList) {
                infoWindow.close()
            }
        }
        val listener = Overlay.OnClickListener { overlay ->
            val marker = overlay as Marker
            if (marker.infoWindow != null) {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                for (infoWindow in infoWindowList) {
                    infoWindow.close()
                }
            }
            true
        }
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