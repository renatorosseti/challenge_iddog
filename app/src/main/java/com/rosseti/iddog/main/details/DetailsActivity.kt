package com.rosseti.iddog.main.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rosseti.iddog.R
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        imageUrl = intent.extras["imageUrl"].toString()
    }

    override fun onResume() {
        super.onResume()
        Glide.with(this)
            .load(imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
            .into(detailsImageView)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
