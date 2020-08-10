package com.example.contest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recipe_ui_specific.view.*

class recipeUISpecific : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.recipe_ui_specific, container, false)

        var layoutList : ArrayList<androidx.constraintlayout.widget.ConstraintLayout> = arrayListOf()
        layoutList.add(androidx.constraintlayout.widget.ConstraintLayout(requireContext()))
        layoutList.add(view.layoutRecipeProcedureElement1)
        layoutList.add(view.layoutRecipeProcedureElement2)
        layoutList.add(view.layoutRecipeProcedureElement3)
        layoutList.add(view.layoutRecipeProcedureElement4)
        layoutList.add(view.layoutRecipeProcedureElement5)
        layoutList.add(view.layoutRecipeProcedureElement6)
        layoutList.add(view.layoutRecipeProcedureElement7)
        layoutList.add(view.layoutRecipeProcedureElement8)
        layoutList.add(view.layoutRecipeProcedureElement9)
        layoutList.add(view.layoutRecipeProcedureElement10)
        layoutList.add(view.layoutRecipeProcedureElement11)
        layoutList.add(view.layoutRecipeProcedureElement12)
        layoutList.add(view.layoutRecipeProcedureElement13)
        layoutList.add(view.layoutRecipeProcedureElement14)
        layoutList.add(view.layoutRecipeProcedureElement15)
        layoutList.add(view.layoutRecipeProcedureElement16)
        layoutList.add(view.layoutRecipeProcedureElement17)
        layoutList.add(view.layoutRecipeProcedureElement18)
        layoutList.add(view.layoutRecipeProcedureElement19)
        layoutList.add(view.layoutRecipeProcedureElement20)
        var recipeList : ArrayList<TextView> = arrayListOf()
        recipeList.add(TextView(requireContext()))
        recipeList.add(view.textProcedure1)
        recipeList.add(view.textProcedure2)
        recipeList.add(view.textProcedure3)
        recipeList.add(view.textProcedure4)
        recipeList.add(view.textProcedure5)
        recipeList.add(view.textProcedure6)
        recipeList.add(view.textProcedure7)
        recipeList.add(view.textProcedure8)
        recipeList.add(view.textProcedure9)
        recipeList.add(view.textProcedure10)
        recipeList.add(view.textProcedure11)
        recipeList.add(view.textProcedure12)
        recipeList.add(view.textProcedure13)
        recipeList.add(view.textProcedure14)
        recipeList.add(view.textProcedure15)
        recipeList.add(view.textProcedure16)
        recipeList.add(view.textProcedure17)
        recipeList.add(view.textProcedure18)
        recipeList.add(view.textProcedure19)
        recipeList.add(view.textProcedure20)
        var recipeImageList : ArrayList<ImageView> = arrayListOf()
        recipeImageList.add(ImageView(requireContext()))
        recipeImageList.add(view.imageProcedure1)
        recipeImageList.add(view.imageProcedure2)
        recipeImageList.add(view.imageProcedure3)
        recipeImageList.add(view.imageProcedure4)
        recipeImageList.add(view.imageProcedure5)
        recipeImageList.add(view.imageProcedure6)
        recipeImageList.add(view.imageProcedure7)
        recipeImageList.add(view.imageProcedure8)
        recipeImageList.add(view.imageProcedure9)
        recipeImageList.add(view.imageProcedure10)
        recipeImageList.add(view.imageProcedure11)
        recipeImageList.add(view.imageProcedure12)
        recipeImageList.add(view.imageProcedure13)
        recipeImageList.add(view.imageProcedure14)
        recipeImageList.add(view.imageProcedure15)
        recipeImageList.add(view.imageProcedure16)
        recipeImageList.add(view.imageProcedure17)
        recipeImageList.add(view.imageProcedure18)
        recipeImageList.add(view.imageProcedure19)
        recipeImageList.add(view.imageProcedure20)

        var procedure : MutableMap<String, String> = mutableMapOf()
        var recipe = arguments?.getStringArrayList("recipe")!!
        var recipeUrl = arguments?.getStringArrayList("recipeUrl")!!
        for (i in 1..recipe.size-1) {
            if (recipeUrl[i] != null) {
                procedure.put(recipe[i], recipeUrl[i])
            } else {
                if (i != 0) {
                    procedure.put(recipe[i], recipeUrl[i-1])
                } else {
                    procedure.put(recipe[i], arguments?.getString("titleUrl")!!)
                }
            }
        }

        var ingredient = view.textIngredient
        var ingreList : ArrayList<String> = arguments?.getStringArrayList("ingredient")!!

        for (ingre in ingreList) {
            ingredient.setText(ingredient.text.toString() + ingre + "\n")
        }

        view.textRecipeTitle.setText(arguments?.getString("title"))
        view.textCtgrSpecific.setText(arguments?.getString("ctgr"))
        Glide.with(requireActivity()).load(arguments?.getString("titleUrl")).into(view.imageRecipe)
        view.imageRecipe.setScaleType(ImageView.ScaleType.CENTER_CROP)

        for (seq in 0..procedure.size) {
            layoutList[seq].isVisible = true
            recipeList[seq].setText(recipe[seq])
            if (recipeUrl[seq] != null) {
                Glide.with(requireActivity()).load(recipeUrl[seq]).into(recipeImageList[seq])
                recipeImageList[seq].setScaleType(ImageView.ScaleType.CENTER_CROP)
            } else {
                Glide.with(requireActivity()).load(arguments?.getString("titleUrl")).into(recipeImageList[seq])
                recipeImageList[seq].setScaleType(ImageView.ScaleType.CENTER_CROP)
            }
        }
        /**
        for (procedure in 0..arguments?.getStringArrayList("recipe")!!.size-1) {
        }
        for (procedureImage in 0..arguments?.getStringArrayList("recipeUrl")!!.size-1) {

        }
        */

        return view
    }
}