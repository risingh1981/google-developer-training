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

package com.example.android.marsrealestate.network


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * Gets Mars real estate property information from the Mars API Retrofit service and updates the
 * [MarsProperty] and [MarsApiStatus] [LiveData]. The Retrofit service returns a coroutine
 * Deferred, which we await to get the result of the transaction.
 * @param filter the [MarsApiFilter] that is sent as part of the web server request
 */
// 8.2) In MarsProperty.kt, convert the class to a Kotlin
// data class with properties that match the JSON response fields
// JSON Response fields: price, id, type, img_src
// 8.3) Rename the img_src class property to imgSrcUrl, and
// add a @Json annotation to remap the img_src JSON field to it
/**
 * This data class defines a Mars property which includes an ID, the image URL, the type (sale
 * or rental) and the price (monthly if it's a rental).
 * The property names of this data class are used by Moshi to match the names of values in JSON.
 */
// 15.4.1) In MarsProperty, make the class parcelable by extending it from
// Parcelable and adding the @Parcelize annotation(This was also done in Step 14):
@Parcelize
data class MarsProperty(
    val id: String,
    @Json(name="img_src")
    val imgSrcUrl: String,
    val type: String,
    val price: Double)
    :Parcelable {
    //15.6.1) Inside the MarsProperty class, create an isRental boolean, and set its
    // value based on whether the property type is "rent":
        val isRental
            get() = type == "rent"
    }

/*{ <- All this can be replaced with @Parcelize annotation
    ...:Parcelable
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble()
    ) {
    }



    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(imgSrcUrl)
        parcel.writeString(type)
        parcel.writeDouble(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarsProperty> {
        override fun createFromParcel(parcel: Parcel): MarsProperty {
            return MarsProperty(parcel)
        }

        override fun newArray(size: Int): Array<MarsProperty?> {
            return arrayOfNulls(size)
        }
    }
}*/



