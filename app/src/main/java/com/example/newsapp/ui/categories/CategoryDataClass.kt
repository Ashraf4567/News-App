package com.example.newsapp.ui.categories

import com.example.newsapp.R

data class CategoryDataClass(
    var id: String,
    var name: String,
    var imageId: Int,
    var backgroundColorId: Int,

    ) {

    companion object {
        fun getCategoryList(): List<CategoryDataClass> {
            return listOf(
                CategoryDataClass(
                    "sports", "Sports",
                    R.drawable.sports,
                    R.color.red
                ),

                CategoryDataClass(
                    "entertainment", "Entertainment",
                    R.drawable.ent,
                    R.color.baby_blue
                ),
                CategoryDataClass(
                    "health", "Health",
                    R.drawable.health,
                    R.color.pink
                ),

                CategoryDataClass(
                    "business", "Business",
                    R.drawable.bussines,
                    R.color.gold
                ),

                CategoryDataClass(
                    "technology", "Technology",
                    R.drawable.tech,
                    R.color.blue
                ),

                CategoryDataClass(
                    "science", "Science",
                    R.drawable.science,
                    R.color.yellow
                ),
            )
        }
    }
}
