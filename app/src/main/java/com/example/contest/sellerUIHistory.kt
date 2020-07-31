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
import kotlinx.android.synthetic.main.seller_ui_history.*

class sellerUIHistory : Fragment() {

    private lateinit var historyElementList: MutableMap<String, ArrayList<productElement>>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: sellerUIHistoryAdapter
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_history, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.layoutManager = linearLayoutManager

        historyElementList = mutableMapOf()
        auth = FirebaseAuth.getInstance()
        val data = database.getReference("productDB")
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (product in p0.children) {
                    // 해당 사용자의 아이디 + 날짜 확인
                    if (product.child("seller").value.toString().equals(userInfo.id)) {
                        if (!historyElementList.keys.any{(product.child("soldDate").value).toString().equals(it)}) {
                            var productElementList : ArrayList<productElement> = ArrayList()
                            historyElementList.put(product.child("soldDate").value.toString(), productElementList)
                        }
                        var productEl = productElement()
                        productEl.setFromDb(product)
                        productEl.soldDate = product.child("soldDate").value.toString()
                        historyElementList[product.child("soldDate").value.toString()]?.add(productEl)
                    }
                }
                adapter = sellerUIHistoryAdapter(historyElementList, requireContext(), 1)
                RecyclerView.adapter = adapter
            }
        })


    }
}