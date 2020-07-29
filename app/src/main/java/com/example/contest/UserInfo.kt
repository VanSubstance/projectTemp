package com.example.contest
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.Exclude


@IgnoreExtraProperties
data class Post(
        var Email: String? = "",
        var Password: String? = "",
        var Name: String? = "",
        var Address: String? = "",
        var Pnum: String? = "",
        var Character:String?=""
) {

    // [START post_to_map]
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "Email" to Email,
                "Password" to Password,
                "Name" to Name,
                "Address" to Address,
                "Pnum" to Pnum,
                "Character" to Character
        )
    }
    // [END post_to_map]
}