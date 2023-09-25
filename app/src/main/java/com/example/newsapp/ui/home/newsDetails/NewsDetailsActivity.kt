package com.example.newsapp.ui.home.newsDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.api.model.newsResponse.News
import com.example.newsapp.databinding.ActivityNewsDetailsBinding

class NewsDetailsActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityNewsDetailsBinding
    private lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        news = ((intent.getParcelableExtra("news") as? News)!!)

        viewBinding.newsData = news
    }
}