package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.seller_ui_home.*
import kotlinx.android.synthetic.main.seller_ui_home.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class sellerUIHome : Fragment() {

    private lateinit var productElementList: ArrayList<productElement>

    private lateinit var adapter: productElementAdapter
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_home, container, false)

        view.buttonEnrollProduct.setOnClickListener {
            if (userInfo.timeClose < SimpleDateFormat("HH:mm", Locale.KOREA).format(Calendar.getInstance().time)) {
                // 만약에 오늘 마감시간이 끝났으면
                /**
                 *
                 *
                 *
                 * 경고메세지 (view.textAlert) 가 샤라락 사라져야댐
                 *
                 *
                 *
                 */
                view.textAlert.isVisible = true
            } else {
                (activity as sellerUIMain).setSellerFrag(12)
            }
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        RecyclerView.setLayoutManager(LinearLayoutManager(context))
        productElementList = ArrayList()

        val data = database.getReference("productTodayDB")
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (product in p0.children) {
                    if (userInfo.id.equals(product.child("seller").value)) {
                        var productEl = productElement()
                        productEl.setFromDb(product)
                        productElementList.add(productEl)
                    }
                }
                adapter = productElementAdapter(productElementList, requireContext(), 3) {
                        productElement ->
                    (activity as sellerUIMain).recyclerViewFun()
                }
                RecyclerView.adapter = adapter
            }
        })

    }

}