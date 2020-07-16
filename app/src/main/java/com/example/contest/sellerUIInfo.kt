package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.seller_ui_info.view.*

class sellerUIInfo : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_info, container, false)
        view.imageUser.setImageResource(R.drawable.test)
        view.buttonSellerInfoModify.setOnClickListener {
            (activity as sellerUIMain).setSellerFrag(42)
        }
        return view
    }
}