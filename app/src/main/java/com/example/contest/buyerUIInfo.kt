package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.buyer_ui_info.view.*

class buyerUIInfo : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_info, container, false)
        view.imageUser.setImageResource(R.drawable.test)
        view.buttonModify.setOnClickListener {
            (activity as buyerUIMain).setBuyerFrag(32)
        }
        return view
    }
}