package com.example.contest

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
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

    /*
    private lateinit var productElementList: ArrayList<productElement>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: productElementAdapter
    */

    companion object {
        fun newInstance(): buyerUIToday {
            return buyerUIToday()
        }
    }

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
        /**
        var markerList : Array<Marker> = arrayOf()
        var coordinateList = readDate()
        for ( i in coordinateList) {
            var marker = Marker()
            marker.position = LatLng(i[1] as Double, i[2] as Double)
            marker.map = naverMap
        }
        */

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

    // 시장 정보(이름, 위도, 경도) 불러오기
    // 시장 데이터: https://discordapp.com/channels/728900415638208564/728900416086999082/737768095610961990
    // 위에 시장 데이터를 DB에 삽입
    // DB에서 값 불러오기 (이름, 위도, 경도)
    fun readDate(): Array<Array<Any>> {
        var container : Array<Array<Any>> = arrayOf()
        return container;
    }

}