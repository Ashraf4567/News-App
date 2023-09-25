package com.example.newsapp.ui

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.newsapp.R

@BindingAdapter("url")
fun bindImageWithUrl(
    imageView: ImageView,
    url: String?
) {


    Glide.with(imageView)
        .load(url)
        .placeholder(R.drawable.ic_launcher_foreground)
        .into(imageView)

}

@BindingAdapter(value = ["showLoading"])
fun setVisibility(view: View, value: Boolean) {
    if (value == true) {
        view.isVisible = true
    } else {
        view.isVisible = false
    }
}

@BindingAdapter("imageUrl")
fun bindImageDetailsWithUrl(
    imageView: ImageView,
    url: String?
) {


    Glide.with(imageView)
        .load(url)
        .into(imageView)

}

@BindingAdapter("launcher")
fun clickToLaunchUrl(view: View, url: String) {
    view.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view.context.startActivity(intent)
    }

    @BindingAdapter("backgroundColor")
    fun changeCardViewBackground(cardView: CardView, color: Int) {
        cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, color))
    }

    @BindingAdapter("imageId")
    fun loadImageByIdDrawable(imageView: ImageView, image: Int) {
        imageView.setImageResource(image)
    }
}
