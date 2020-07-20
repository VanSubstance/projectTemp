package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.buyer_ui_home.view.*

class buyerUIHome : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.buyer_ui_home, container, false)

        view.buttonRecipe1.setOnClickListener {
            // 육류
        }
        view.buttonRecipe2.setOnClickListener {
            // 해산물
        }
        view.buttonRecipe3.setOnClickListener {
            // 채소류
        }
        view.buttonRecipe4.setOnClickListener {
            // 그 외
        }

        return view
    }
}