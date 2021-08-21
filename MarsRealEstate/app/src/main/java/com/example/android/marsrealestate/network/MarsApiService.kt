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

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
// 8.5) Removing use of ScalarsConverterFactory
// import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// 6.2) Open MarsApiService.kt. We've provided a variable
// there for the root web address of the Mars server endpoint:
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"
// Could also use the Udacity URL ""https://mars.udacity.com/"

// 16.1) In MarsApiService, create a MarsApiFilter enum that defines
// constants to match the query values our web service expects:
enum class MarsApiFilter(val value: String) {
    SHOW_BUY("buy"), SHOW_RENT("rent"),SHOW_ALL("all")
}

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
// 9.2) In MarsApiService, add a CoroutineCallAdapterFactory to the Retrofit builder.
/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
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
    // 9.3) Change getProperties() Call<List<MarsProperty>> to a Deferred list of MarsProperty:
    // @GET("realestate")
    //    fun getProperties(@Query("filter") type: String): Deferred<List<MarsProperty>>
    // 16.2) Add a @Query("filter") parameter to getProperties() so we can filter properties
    // based on the MarsApiFilter enum values. Looks like, they took out "deferred"
    @GET("realestate")
    fun getProperties(@Query("filter") type: String): List<MarsProperty>
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

