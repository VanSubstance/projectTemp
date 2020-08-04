package com.example.contest
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.Exclude


@IgnoreExtraProperties
data class Post_s(
        var Name: String? = "",
        var pNum: String? = "",
        var role: String?="",
        var market_n: String?=""
) {

    // [START post_to_map]
    @Exclude
    fun toMap_s(): Map<String, Any?> {
        return mapOf(
                "Name" to Name,
                "pNum" to pNum,
                "role" to role,
                "marketTitle" to market_n
        )
    }
    // [END post_to_map]
}