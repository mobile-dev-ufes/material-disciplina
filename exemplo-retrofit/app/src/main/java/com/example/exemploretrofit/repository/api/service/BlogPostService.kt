package com.example.exemploretrofit.repository.api.service

import com.example.exemploretrofit.repository.api.model.BlogPostEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BlogPostService {

    @GET("posts/{postId}")
    fun getSinglePost(@Path("postId")postId: String): Call<BlogPostEntity>
}