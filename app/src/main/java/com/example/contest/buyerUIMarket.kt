package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.buyer_ui_market.*
import java.sql.Array

class buyerUIMarket : Fragment() {

    private lateinit var productElementList: ArrayList<productElement>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: productElementAdapter
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_market, container, false)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.layoutManager = linearLayoutManager
        auth = FirebaseAuth.getInstance()

        var data = database.getReference("marketDB").child(currentCondition.marketTitle).child("store")
        var sellers : ArrayList<String> = ArrayList()
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                // 해당 시장의 판매자 리스트 생성
                for (market in p0.children) {
                    sellers.add(market.child("seller").value.toString())
                }
            }
        })
        productElementList = ArrayList()
        data = database.getReference("productTodayDB")
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (product in p0.children) {
                    // 판매자가 일치하는 오늘의 상품들 = 그 시장의 오늘의 상품
                    if (sellers.any{it.equals(product.child("seller").value.toString())}) {
                        var productEl = productElement()
                        productEl.setFromDb(product)
                        productElementList.add(productEl)
                    }
                }
                adapter = productElementAdapter(productElementList, requireContext(), 4) {
                        productElement ->
                    // 팝업창 띄우기
                    when (arguments?.getInt("usage")) {
                        // 재료
                        2 -> {
                            (activity as buyerUIMain).showProductSpecific(productElement, 2)
                        }
                        // 완제품
                        3 -> {
                            (activity as buyerUIMain).showProductSpecific(productElement, 3)
                        }
                    }
                }
                RecyclerView.adapter = adapter
            }
        })



    }

}