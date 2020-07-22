package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.LocationOverlay
import com.naver.maps.map.overlay.Marker
import kotlinx.android.synthetic.main.buyer_ui_today.*

class buyerUIToday : Fragment(), OnMapReadyCallback {

    private lateinit var productElementList: ArrayList<productElement>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: productElementAdapter

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


        val marker = Marker()
        marker.position = LatLng(37.359184, 127.104832)
        marker.map = naverMap

/*
        val locationOverlay = naverMap.locationOverlay
        locationOverlay.isVisible = true

        locationOverlay.position = LatLng(37.359184, 127.104832)
*/
    }


/*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.layoutManager = linearLayoutManager

        productElementList = ArrayList()

        // 데이터베이스에서 조건에 맞는 상품들 불러오기
        // 상품들 productElement 양식에 맞춰서 데이터 집어넣기
        // productElementList에 넣어주기
        for (i in 0 until 13) {
            val element = productElement("구매할 상품 $i")
            productElementList.add(element)
        }

        adapter = productElementAdapter(productElementList, requireContext(), 4)
        RecyclerView.adapter = adapter

    }

 */
}