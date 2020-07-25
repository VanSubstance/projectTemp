package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recipe_ui_vegetable.*

class recipeUIVegetable : Fragment() {

    private lateinit var recipeElementList: ArrayList<recipeElement>
    private val linearLayoutManager by lazy { LinearLayoutManager(context) }
    private lateinit var adapter: recipeUIAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recipe_ui_vegetable,container,false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        RecyclerView.layoutManager = linearLayoutManager

        recipeElementList = ArrayList()


        for (i in 0 until 4) {
            val element = recipeElement("채소 샘플 $i")
            element.ctgrBig = "채소"
            recipeElementList.add(element)
        }
        adapter = recipeUIAdapter(recipeElementList, requireContext(), 1){
                recipeElement ->
            (activity as buyerUIMain).showRecipeSpecific(recipeElement)
        }
        RecyclerView.adapter = adapter

    }
}