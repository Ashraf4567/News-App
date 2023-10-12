package com.example.newsapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.ui.categories.CategoriesFragment
import com.example.newsapp.ui.categories.CategoryDataClass
import com.example.newsapp.ui.home.setting.SettingFragment
import com.example.newsapp.ui.news.NewsFragment

class HomeActivity : AppCompatActivity(), CategoriesFragment.OnCategoryClickListener {

    override fun onCategoryClick(category: CategoryDataClass) {
        showCategoryDetailsFragment(category)
    }


    private lateinit var viewBinding: ActivityHomeBinding
    private var categoriesFragment = CategoriesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        categoriesFragment.onCategoryClickListener = this

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, categoriesFragment)
            .commit()

        showToggleNavDrawer()
    }

    private fun showToggleNavDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, viewBinding.myDrawer, viewBinding.toolbar, R.string.open, R.string.close
        )
        viewBinding.myDrawer.addDrawerListener(toggle)
        toggle.syncState()
        viewBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_category -> {
                    showCategoryFragment()
                }

                R.id.nav_setting -> {
                    showSettingsFragment()
                }

                else -> {}
            }
            viewBinding.myDrawer.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }

    private fun showCategoryFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, categoriesFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showSettingsFragment() {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, SettingFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun showCategoryDetailsFragment(category: CategoryDataClass) {

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, NewsFragment.getInstance(category))
            .addToBackStack(null)
            .commit()

    }


}