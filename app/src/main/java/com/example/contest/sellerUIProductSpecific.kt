package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.product_seller_home_specific.view.*

class sellerUIProductSpecific : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.product_seller_home_specific, container, false)

        view.textTitle.setText("")
        view.textPrice.setText("")
        view.textQuan.setText("")

        return view
    }
}