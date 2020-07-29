package com.example.contest
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.Exclude


@IgnoreExtraProperties
data class Post_seller(
        var Email: String? = "",
        var Password: String? = "",
        var Name: String? = "",
        var Address: String? = "",
        var Pnum: String? = "",
        var MarketName:String?="",
        var S_time:Any="",
        var E_time:Any="",
        var Cat:Any=""
) {

    // [START post_to_map]
    @Exclude
    fun seller_toMap(): Map<String, Any?> {
        return mapOf(
                "Email" to Email,
                "Password" to Password,
                "Name" to Name,
                "Address" to Address,
                "Pnum" to Pnum,
                "MarketName" to MarketName,
                "StartTime" to S_time,
                "EndTime" to E_time,
                "Category" to Cat
        )
    }
    // [END post_to_map]
}