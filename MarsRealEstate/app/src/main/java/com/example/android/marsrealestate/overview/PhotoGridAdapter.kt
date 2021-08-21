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

package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
// 12.2.2) Add the imports for the ListAdapter and MarsProperty.
// Make sure to import androidx.recyclerview.widget.ListAdapter.
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty


// 12.2.1) In PhotoGridAdapter.kt create a 'PhotoGridAdapter' class that extends a
// RecyclerView ListAdapter with DiffCallback. Have it use a custom
// PhotoGridAdapter.MarsPropertyViewHolder to present a list of <MarsProperty> objects
// 12.2.3) Use Control-I to have Android Studio override the adapter's required onCreateViewHolder()
// and onBindViewHolder() methods: Don't bother filling them out yet.
// 12.2.6) Implement the empty onCreateViewHolder() and onBindViewHolder() methods:
// 15.3.2) Add an OnClickListener parameter to the PhotoGridAdapter class declaration:
/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 * @param onClick a lambda that takes the
 */
class PhotoGridAdapter(private val onClickListener: OnClickListener): ListAdapter<MarsProperty, PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback){
    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.MarsPropertyViewHolder {
        return MarsPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }
    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty)
        // 15.3.3) In onBindViewHolder(), set up onClickListener() to pass marsProperty
        // on button click:
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
    }
    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [MarsProperty]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [MarsProperty]
     */
    // 15.3.1) InPhotoGridAdapter, create an internal OnClickListener class with a lambda
    // in its constructor that initializes a matching onClick function:
    class OnClickListener(val clickListener: (marsProperty: MarsProperty) -> Unit) {
        fun onClick(marsProperty: MarsProperty) = clickListener(marsProperty)
    }
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     */
    // 12.2.4) Create the DiffCallback companion object and override its two required
    // areItemsTheSame() methods:
    companion object DiffCallback: DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id === newItem.id
        }
    }
    /**
     * The MarsPropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [MarsProperty] information.
     */
    // 12.2.5) Create a MarsPropertyViewHolder inner class, and implement the bind() method that includes a binding to marsProperty:
    //Hint Don't forget to call binding.executePendingBindings()
    class MarsPropertyViewHolder(private var binding: GridViewItemBinding ):RecyclerView.ViewHolder(binding.root) {
        fun bind(marsProperty: MarsProperty) {
            binding.property = marsProperty
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }
}