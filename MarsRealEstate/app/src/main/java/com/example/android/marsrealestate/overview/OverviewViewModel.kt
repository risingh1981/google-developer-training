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

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.marsrealestate.network.MarsApi
import com.example.android.marsrealestate.network.MarsApiFilter
import com.example.android.marsrealestate.network.MarsApiService
import com.example.android.marsrealestate.network.MarsProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Callable


// 13.1) In OverviewViewModel.kt, create a MarsApiStatus enum with LOADING, ERROR, DONE states.
// Note: We created it outside of the class, but it can go either place.
enum class MarsApiStatus { LOADING, ERROR,DONE }

/**
 * The [ViewModel] that is attached to the [OverviewFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    // 11.1.2) In OverViewViewModel, rename _response LiveData to _status.
    //13.2) Change _status type from String to MarsApiStatus.
    private val _status = MutableLiveData<MarsApiStatus>()
    // The external immutable LiveData for the response String
    val status: LiveData<MarsApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values.
    // 11.1.3) Add an encapsulated LiveData<MarsProperty> property:
    // 12.1.2) In OverviewViewModel rename _property to _properties, and
    // assign it a List of MarsProperty:
    private val _properties = MutableLiveData<List<MarsProperty>>()
    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    // 15.2.1) In OverviewViewModel, add an encapsulated LiveData variable for
    // navigating to the selectedProperty detail screen:
    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty
    //9.4.1) Add variables for a coroutine Job and a CoroutineScope using the Main Dispatcher:
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     */
    // 15.2.2) Add a function to set _navigateToSelectedProperty to marsProperty and
    // initiate navigation to the detail screen on button click:
    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }
    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    // 15.2.2 Continued...
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    /**
     * Updates the data set filter for the web services by querying the data with the new filter
     * by calling [getMarsRealEstateProperties]
     * @param filter the [MarsApiFilter] that is sent as part of the web server request
     */
    // 16. 6) Now create an updateFilter() method to requery the data by calling
    // getMarsRealEstateProperties with the new filter:
    fun updateFilter(filter: MarsApiFilter) {
        getMarsRealEstateProperties(filter)
    }

    // 9.4.6) Finally, override onCleared() and cancel the Job when the ViewModel is
    // finished.
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    // 16.5) In the init block, pass MarsApiFilter.SHOW_ALL as the
    // default parameter to getMarsRealEstateProperties():
    init {
        getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
    }

    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved.
     */
    /**
     * Gets Mars real estate property information from the Mars API Retrofit service and updates the
     * [MarsProperty] [List] and [MarsApiStatus] [LiveData]. The Retrofit service returns a
     * coroutine Deferred, which we await to get the result of the transaction.
     */
    //6.6) In OverviewViewModel.kt, use MarsApi.retrofitService to enqueue the Retrofit request
    // in getMarsRealEstateProperties(), overriding the required Retrofit callbacks to assign
    // the JSON response or an error message to the _response LiveData value. Make sure to
    // import the Retrofit versions of Callback, Call, and Response.
    // 8.7) In OverviewModel.kt, update getMarsRealEstateProperties() to handle list of
    // MarsProperty instead of String. A successful response should include the number of
    // real estate properties returned, response.body().size:
    // 9.4.2) In getMarsRealEstateProperties(), replace the enque() code with a coroutine
    // for making the API request.
    // 9.4.3) Inside the coroutine, create a getPropertiesDeferred variable and assign it a
    // call to getProperties().
    // 9.4.4) Add a try/catch block with a call to getPropertiesDeferred.await().
    // 9.4.5) Then return the list size (as before) in the success message, and the
    // message from the exception in the failure message.
    // 11.1.4) Update getMarsRealEstateProperties() to set _property to the first
    // MarsProperty from listResult.
    // 12.1.3) Then update getMarsRealEstateProperties() to return the entire list
    // instead of just one item.
    // 13.3) In getMarsRealEstateProperties(), using the enums defined above, set _status
    // value to LOADING, DONE, or ERROR. In the error case, clear the properties LiveData
    // by setting it to a new empty ArrayList:
    // 16.3) In OverviewViewModel, add a MarsApiFilter parameter to getMarsRealEstateProperties():
    private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
        // 9.4.1 - 9.4.5
        coroutineScope.launch {
            // 16.4) Pass the filter.value to retrofitService.getProperties(), they
            // also removed the Deferred part in solution.
            var getPropertiesDeferred = MarsApi.retrofitService.getProperties(filter.value)
            _status.value = MarsApiStatus.LOADING
            try {
                //_status.value = MarsApiStatus.LOADING
                //_properties.value = MarsApi.retrofitService.getProperties(filter.value)
                var listResult = getPropertiesDeferred.await()
                _status.value = MarsApiStatus.DONE
                //_response.value = "Success ${listResult.size} Properties Retrieved"
                _properties.value = listResult

            } catch (t: Throwable) {
                //Log.i("In Catch with:","Exception:$t")
                //_response.value = "Failure ${t.message}"
                //_status.value = "Failure ${t.message}"
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
        /* Pre-9.4.2 Code:
        MarsApi.retrofitService.getProperties().enqueue(object: Callback<List<MarsProperty>> {
            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
                _response.value = "Failure " + t.message
            }
            override fun onResponse(call: Call<List<MarsProperty>>, response: Response<List<MarsProperty>>) {
                _response.value = "Success ${response.body()?.size} Properties Retrieved"
            }
        })
        */
    }

}
