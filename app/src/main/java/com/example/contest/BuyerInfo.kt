package com.example.contest
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.Exclude


@IgnoreExtraProperties
data class Post(
        var ID: String? = "",
        var Password: String? = "",
        var Name: String? = "",
        var pNum: String? = "",
        var role: String?=""
) {

    // [START post_to_map]
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "id" to ID,
                "pw" to Password,
                "Name" to Name,
                "pNum" to pNum,
                "role" to role
        )
    }
    // [END post_to_map]
}