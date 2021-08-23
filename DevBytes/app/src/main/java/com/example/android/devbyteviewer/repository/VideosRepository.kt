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

package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.VideosDatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

// 14.1. Create the repository class:
//In VideosRepository.kt, create the VideosRepository class.
class VideosRepository(private val database: VideosDatabase) {
    // 14.5. Define a LiveData for your list of videos:
    val videos: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }
    // 14.2. Define a refreshVideos() function to refresh the offline cache:
    //Make it a suspend function since it will be called from a coroutine.
    suspend fun refreshVideos() {
        // 14.3. Get the data from the network and then put it in the database: In
        // refreshVideos(), make a network call to getPlaylist(), and use the await()
        // function to tell the coroutine to suspend until the data is available. Then call
        // insertAll() to insert the playlist into the database.
        // Note the asterisk * is the spread operator. It allows you to pass in an array to
        // a function that expects varargs.
        // 14.4. Run your code on the IO Dispatcher: Inside refreshVideos(), call
        // withContext(Dispatchers.IO) to force the Kotlin coroutine to switch to the IO
        // dispatcher.
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}
