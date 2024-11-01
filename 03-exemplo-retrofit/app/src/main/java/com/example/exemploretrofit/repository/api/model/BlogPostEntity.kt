package com.example.exemploretrofit.repository.api.model

import com.google.gson.annotations.SerializedName

class BlogPostEntity {

    @SerializedName("userId")
    var userID: Int = 0

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("title")
    var title: String = ""

    @SerializedName("body")
    var body: String = ""
}