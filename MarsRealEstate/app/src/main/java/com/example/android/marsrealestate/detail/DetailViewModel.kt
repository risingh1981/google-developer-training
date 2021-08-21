/*
 *  Copyright 2019, The Android Open Source Project
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.android.marsrealestate.detail

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.*
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.R

/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [MarsProperty].
 */
/**
 * The [ViewModel] that is associated with the [DetailFragment].
 */
// 15.1.1) In DetailViewModel, remove the @Suppress("UNUSED_PARAMETER") annotation
// from the class declaration.
class DetailViewModel(marsProperty: MarsProperty, app: Application) : AndroidViewModel(app) {
    // The external LiveData for the SelectedProperty
    // 15.1.2) Add an encapsulated selectedProperty LiveData variable,
    // then set its value in an init block:
    private val _selectedProperty = MutableLiveData<MarsProperty>()

    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = marsProperty
    }

    // The displayPropertyPrice formatted Transformation Map LiveData, which displays the sale
    // or rental price.
    // 15.6.2) In DetailViewModel, create a transformation map, displayPropertyPrice, to convert
    // selectedProperty's price to a displayable string:
    val displayPropertyPrice = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
            when (it.isRental) {
                true -> R.string.display_price_monthly_rental
                false -> R.string.display_price
            }, it.price)
    }
    // The displayPropertyType formatted Transformation Map LiveData, which displays the
    // "For Rent/Sale" String
    // 15.6.2 cont.) and a second transformation map, displayPropertyType, to display
    // whether selectedProperty is for sale or rent:
    val displayPropertyType = Transformations.map(selectedProperty) {
        app.applicationContext.getString(R.string.display_type,
            app.applicationContext.getString(
                when (it.isRental) {
                    true -> R.string.type_rent
                    false -> R.string.type_sale
                }))
    }
}
