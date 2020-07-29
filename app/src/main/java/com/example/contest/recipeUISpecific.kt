package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.recipe_ui_element.view.*
import kotlinx.android.synthetic.main.recipe_ui_specific.view.*

class recipeUISpecific : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recipe_ui_specific, container, false)

        view.textTitle.text = arguments?.getString("title")
        view.recipeCtgr.text = arguments?.getString("ctgrBig")

        return view
    }
}