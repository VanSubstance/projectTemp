package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class sellerUIHome : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_home, container, false)

<<<<<<< HEAD
        enrollProduct.setOnClickListener{
            val sellerEnroll = Intent(getActivity(),sellerUIEnrollment::class.java)
            startActivity(sellerEnroll)
        }
=======



        return view
>>>>>>> 8d729a4ea0b87e96c77fb081fe93571b8e97668a
    }
}