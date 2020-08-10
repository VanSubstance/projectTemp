package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recipe_ui_specific.view.*

class recipeUISpecific : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recipe_ui_specific, container, false)

        view.textRecipeTitle.setText(arguments?.getString("title"))
        view.textCtgrSpecific.setText(arguments?.getString("ctgr"))
        Glide.with(requireActivity()).load(arguments?.getString("titleUrl")).into(view.imageRecipe)
        view.imageRecipe.setScaleType(ImageView.ScaleType.CENTER_CROP)

        return view
    }
}