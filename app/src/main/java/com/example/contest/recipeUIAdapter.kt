package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class recipeUIAdapter(var recipeElementList: ArrayList<recipeUIElement>, val context: Context, var usage : Int) : RecyclerView.Adapter<recipeUIAdapter.recipeUIViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recipeUIViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.recipe_ui_element, parent, false)
        when(usage) {
            1 -> {
                view = LayoutInflater.from(context).inflate(R.layout.recipe_ui_element, parent, false)
            }
        }
        return recipeUIViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeElementList.size
    }

    override fun onBindViewHolder(holder: recipeUIViewHolder, position: Int) {

        holder.bind(recipeElementList[position], context)
    }

    inner class recipeUIViewHolder(elementView: View) : RecyclerView.ViewHolder(elementView) {

        val recipeImage = elementView.findViewById<ImageView>(R.id.recipeImage)
        val recipeTitle = elementView.findViewById<TextView>(R.id.recipeTitle)
        val recipeCtgr = elementView.findViewById<TextView>(R.id.recipeCtgr)
        val recipeLevel = elementView.findViewById<TextView>(R.id.recipeLevel)

        fun bind(recipeElements: recipeUIElement, context: Context) {

            recipeImage.setImageResource(recipeElements.image)
            recipeTitle.text = recipeElements.title
            recipeCtgr.text = recipeElements.ctgr
            recipeLevel.text = recipeElements.level

        }
    }

}