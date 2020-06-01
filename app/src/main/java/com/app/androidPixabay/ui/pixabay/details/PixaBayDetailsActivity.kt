package com.app.androidPixabay.ui.pixabay.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.androidPixabay.R
import com.app.androidPixabay.commons.utils.Constants.Companion.EXTRA_HITSLIST
import com.app.androidPixabay.databinding.ActivityPixabayDetailsBinding

class PixaBayDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityPixabayDetailsBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_pixabay_details
        )

        if (intent != null && intent.hasExtra(EXTRA_HITSLIST)) {
            binding.hitsList = intent.getParcelableExtra(EXTRA_HITSLIST)
            binding.callback = this
        }
    }

    fun moveToPreviousScreen() = finish()
}
