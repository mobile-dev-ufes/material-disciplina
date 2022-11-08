package com.example.exemploretrofit.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exemploretrofit.repository.api.client.ClientRetrofit
import com.example.exemploretrofit.repository.api.model.BlogPostEntity
import com.example.exemploretrofit.repository.api.service.BlogPostService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    private var postText = MutableLiveData<String>()

    fun getPostText(): LiveData<String>{
        return postText
    }

    fun requestNewBlogPost(id: String) {
        val apiBlogService = ClientRetrofit.createService(BlogPostService::class.java)
        val blogPost: Call<BlogPostEntity> = apiBlogService.getSinglePost(id)
        blogPost.enqueue(object : Callback<BlogPostEntity> {
            override fun onResponse(
                call: Call<BlogPostEntity>,
                response: Response<BlogPostEntity>
            ) {
                postText.value = response.body()?.body
            }

            override fun onFailure(call: Call<BlogPostEntity>, t: Throwable) {
                postText.value = "Falha na requisição da API"
            }
        })
    }

}