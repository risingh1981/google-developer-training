/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate

import android.view.Gravity.apply
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.GravityCompat.apply
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.overview.MarsApiStatus
import com.example.android.marsrealestate.overview.PhotoGridAdapter

// 11.2.1) In BindingAdapters create a BindingAdapter to convert imgUrl to a
// URI with the https scheme.
//Inside the adapter, use Glide to download the image display it in imgView.
// 11.3.1) In BindingAdapters, apply RequestOptions to the Glide call to add a
// placeholder that displays an image while your image downloads, and an error image
// in case it can't be retrieved.
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        //Glide.with(imgView.context).load(imgUri).into(imgView)
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}
//12.2.7) In BindingAdapters, add a bindRecyclerView binding adapter for listData, and have
// it call submitList() on the PhotosGridAdapter:
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsProperty>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

// 13.4) In BindingAdapters.kt, add a binding adapter to show MarsApiStatus in the ImageView,
// and set the view's visibility depending on the status value.
@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: MarsApiStatus?) {
    Toast.makeText(statusImageView.context,"In bindStatus, Value of status is:$status", Toast.LENGTH_LONG).show()
    when (status) {
        MarsApiStatus.LOADING -> { statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)}
        MarsApiStatus.ERROR -> { statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)}
        MarsApiStatus.DONE -> { statusImageView.visibility = View.GONE }

    }
}