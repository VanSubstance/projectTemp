package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.buyer_ui_basket.*

class buyerUIBasket : Fragment() {

    private lateinit var productElementList: ArrayList<productElement>
    private lateinit var adapter: productElementAdapter

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_basket, container, false)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.setLayoutManager(LinearLayoutManager(context))

        var data = FirebaseDatabase.getInstance().getReference("productTodayDB")
        // 이전 장바구니 정보 초기화
        productElementList = arrayListOf()
        // 데이터베이스에서 장바구니에 해당하는 상품 스캔해서 가져오기
        data.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (product in p0.children) {
                    for (buyer in product.child("buyer").children) {
                        if (buyer.key.equals(userInfo.id)) {
                            // 해당 상품 구매자 정보에 이 사용자가 있을 경우
                            var productEl = productElement()
                            productEl.setFromDb(product)
                            productElementList.add(productEl)
                        }
                    }
                    adapter = productElementAdapter(productElementList, requireContext(),5) {
                            productElement ->
                        // 정보 보이기
                        (activity as buyerUIMain).recyclerViewFun(productElement)
                    }
                    RecyclerView.adapter = adapter
                }
            }
        })




    }

}