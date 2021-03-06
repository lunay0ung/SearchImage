package com.luna.searchimage.view.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.luna.searchimage.R
import com.luna.searchimage.viewmodel.ImageDetailViewModel

class ImageDetailActivity : AppCompatActivity(){

    private lateinit var viewModel: ImageDetailViewModel
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_detail_activity)

        val siteName=intent.getStringExtra("siteName")
        val imageUrl=intent.getStringExtra("imageUrl")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = siteName

        image = findViewById(R.id.image)
        Glide.with(image.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_defaultimage)
            .into(image)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when(item.itemId) {
                android.R.id.home -> onBackPressed()
                else -> {}
            }
        }
        return true
    }
}