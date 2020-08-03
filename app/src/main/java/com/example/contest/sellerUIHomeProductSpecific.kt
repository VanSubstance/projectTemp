package com.example.contest

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.product_seller_home_specific.view.*

class sellerUIHomeProductSpecific: Fragment() {

    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val mStorageRef = FirebaseStorage.getInstance().getReference("productImageDB")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.product_seller_home_specific, null)
        var productElement = currentProductElement.currentProductElement

        val data = database.getReference("productTodayDB")
        val imagePath = mStorageRef.child(productElement.productId + ".png")
        val imageSize: Long = 1024 * 1024 * 10

        view.textTitle.text = productElement.title
        view.textPrice.text = productElement.price.toString()
        view.textServing.text = productElement.serve.toString()
        imagePath.getBytes(imageSize).addOnSuccessListener {
            val imageBitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.productImage.setImageBitmap(imageBitmap)
        }

        view.buttonModify.setOnClickListener {
            (activity as sellerUIMain).setSellerFrag(14)
        }

        view.buttonDelete.setOnClickListener {
            data.child(productElement.productId).removeValue()
            imagePath.delete()
            (activity as sellerUIMain).setSellerFrag(11)
        }

        return view
    }

}