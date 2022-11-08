package com.example.exemploretrofit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.exemploretrofit.R
import com.example.exemploretrofit.databinding.ActivityMainBinding
import com.example.exemploretrofit.viewModel.MainViewModel


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainVM: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainVM = ViewModelProvider(this).get(MainViewModel::class.java)
        setObserver()
        binding.buttonGetPost.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if(view.id == R.id.button_get_post) {
            val postId = binding.editPostId.text.toString()
            if (postId == "")
                Toast.makeText(this, R.string.edit_empty, Toast.LENGTH_SHORT).show()
            else
                mainVM.requestNewBlogPost(postId)
        }
    }

    private fun setObserver() {
        mainVM.getPostText().observe(this, Observer {
            if(it != "")
                binding.textPost.text = it
            else
                binding.textPost.text = R.string.post_content.toString()
        })
    }
}