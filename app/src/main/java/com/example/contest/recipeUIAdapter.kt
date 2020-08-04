package com.example.contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class recipeUIAdapter(var recipeElementList: ArrayList<recipeElement>, val context: Context, var usage : Int, val productClick: (recipeElement) -> Unit) : RecyclerView.Adapter<recipeUIAdapter.recipeUIViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recipeUIViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.recipe_ui_element, parent, false)
        when(usage) {
            1 -> {
                view = LayoutInflater.from(context).inflate(R.layout.recipe_ui_element, parent, false)
            }
        }
        return recipeUIViewHolder(view, productClick)
    }

    override fun getItemCount(): Int {
        return recipeElementList.size
    }

    override fun onBindViewHolder(holder: recipeUIViewHolder, position: Int) {

        holder.bind(recipeElementList[position], context)
    }

    inner class recipeUIViewHolder(elementView: View, productClick: (recipeElement) -> Unit) : RecyclerView.ViewHolder(elementView) {

        val recipeImage = elementView.findViewById<ImageView>(R.id.imageRecipe)
        val recipeTitle = elementView.findViewById<TextView>(R.id.textRecipeTitle)
        val recipeCtgr = elementView.findViewById<TextView>(R.id.textRecipeCtgr)
        val recipeLevel = elementView.findViewById<ImageView>(R.id.imageRecipeLevel)
        val elementView = elementView
        val productClick = productClick

        fun bind(recipeElements: recipeElement, context: Context) {

            recipeImage.setImageResource(recipeElements.image)
            recipeTitle.text = recipeElements.title
            recipeCtgr.text = recipeElements.ctgr
            recipeLevel.setImageResource(recipeElements.level)
            elementView.setOnClickListener {
                productClick(recipeElements)
            }

        }
    }

}