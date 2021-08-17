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

import com.squareup.moshi.Json


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
data class MarsProperty(
    val id: String,
    @Json(name="img_src")
    val imgSrcUrl: String,
    val type: String,
    val price: Double)


