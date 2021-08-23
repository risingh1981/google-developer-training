/*
 * Copyright 2018, The Android Open Source Project
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

package com.example.android.devbyteviewer.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDomainModel
import com.example.android.devbyteviewer.repository.VideosRepository
import kotlinx.coroutines.*
import java.io.IOException

/**
 * DevByteViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *
 * @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 */
@InternalCoroutinesApi
class DevByteViewModel(application: Application) : AndroidViewModel(application) {

    /**
     *
     */

    /**
     *
     */
    // 15.2. Create the database singleton.
    // Define a database variable and assign it to getDatabase(), passing the application.
    // Kotline gives following Error without Annotation:
    // This is an internal kotlinx.coroutines API that should not be used from outside of
    // kotlinx.coroutines. No compatibility guarantees are provided.It is recommended to
    // report your use-case of internal API to kotlinx.coroutines issue tracker, so stable
    // API could be provided instead
    @InternalCoroutinesApi
    private val database = getDatabase(application)

    // 15.3. Then create your repository.
    // Define val videosRepository and assign it to a VideosRepository using the database
    // singleton.
    @InternalCoroutinesApi
    val videosRepository = VideosRepository(database)

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    // 15.4. Refresh the videos using the repository.
    // Create an init block and launch a coroutine to call videosRepository.refreshVideos().
    // Kotlin says to add @InternalCoroutinesApi to class DevByteViewModel
    init {
        viewModelScope.launch {
            videosRepository.refreshVideos()
        }
    }

    // 15.5. Create the playlist.
    // Get videos LiveData from the repository and assign it to a playlist variable.
    val playlist = videosRepository.videos

    // 15.1. Delete the code that will be replaced by the repository:
    //
    //In DevByteViewModel, delete _playlist, playlist variables, the init block, and
    // refreshDataFromNetwork() function. We'll replace this code with the repository.
    /*
    /**
     * A playlist of videos that can be shown on the screen. This is private to avoid exposing a
     * way to set this value to observers.
     */
    private val _playlist = MutableLiveData<List<Video>>()

    /**
     * A playlist of videos that can be shown on the screen. Views should use this to get access
     * to the data.
     */
    val playlist: LiveData<List<Video>>
        get() = _playlist

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        refreshDataFromNetwork()
    }

    /**
     * Refresh data from network and pass it via LiveData. Use a coroutine launch to get to
     * background thread.
     */
    private fun refreshDataFromNetwork() = viewModelScope.launch {
        try {
            val playlist = Network.devbytes.getPlaylist().await()
            _playlist.postValue(playlist.asDomainModel())
        } catch (networkError: IOException) {
            // Show an infinite loading spinner if the request fails
            // challenge exercise: show an error to the user if the network request fails
        }
    }

    /**
     */
     */

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
