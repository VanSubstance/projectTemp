package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.seller_ui_home.*
import kotlinx.android.synthetic.main.seller_ui_home.view.*

class sellerUIHome : Fragment() {

    private lateinit var productElementList: ArrayList<productElement>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: productElementAdapter
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val DatabaseReference = database.reference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_home, container, false)

        view.enrollProduct.setOnClickListener {
            (activity as sellerUIMain).setSellerFrag(12)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        RecyclerView.layoutManager = linearLayoutManager
        productElementList = ArrayList()
        var productEl = productElement()
        auth = FirebaseAuth.getInstance()
        val data = database.getReference("productTodayDB")
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (product in p0.children) {
                    if (userInfo.id.equals(product.child("seller").value)) {
                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        var title = product.child("title").value
                        var price = product.child("price").value
                        var quan = product.child("quantity").value
                        productEl.setInfo(title.toString(), price as Int, quan as Int)
                        productElementList.add(productEl)
                    }
                }
            }
        })

        adapter = productElementAdapter(productElementList, requireContext(), 3) {
                productElement ->
            // 팝업창 띄우기
            (activity as sellerUIMain).showProductSpecific(productElement, 1)
        }
        RecyclerView.adapter = adapter

    }

}