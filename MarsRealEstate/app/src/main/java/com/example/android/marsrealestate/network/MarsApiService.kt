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

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

// 6.2) Open MarsApiService.kt. We've provided a variable
// there for the root web address of the Mars server endpoint:
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"
// Could also use the Udacity URL ""https://mars.udacity.com/"

// 8.4) In MarsApiService.kt, use the Moshi Builder
// to create a Moshi object with the KotlinJsonAdapterFactory:
/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

// 6.3) Use Retrofit.Builder to create the Retrofit object.
// Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create()).baseUrl(BASE_URL).build()
// 8.5) In the retrofit object, change the ConverterFactory
// to a MoshiConverterFactory with our moshi Object:
/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// 6.4) Create a MarsApiService interface, and define a getProperties()
// method to request the JSON response string.
// 8.6) Update MarsApiService getProperties() method to return a List of MarsProperty
// objects instead of String.
/**
 * A public interface that exposes the [getProperties] method
 */
interface MarsApiService{
    // Annotate the method with @GET, specifying the endpoint for the
    // JSON real estate response, and create the Retrofit Call object that
    // will start the HTTP request.
    /**
     * Returns a Retrofit callback that delivers a [List] of [MarsProperty]
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("realestate")
    fun getProperties():
        Call<List<MarsProperty>>
}

// 6.5) Passing in the service API you just defined, create a public object called MarsApi
// to expose the Retrofit service to the rest of the app:
/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MarsApi {
    val retrofitService: MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

