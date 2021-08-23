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

package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

// 8.1. In database/Room.kt, define a @Dao interface called VideoDao:
@Dao
interface VideoDao {
    //8.2. Add getVideos() Query function to VideoDao that returns a List of DatabaseVideo:
    //9.1. Refactor to use LiveData:
    //In Room.kt, all you have to do is update getVideos() to return a LiveData of a List
    // of DatabaseVideo.
    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>
    // 8.3. Add an Insert function called insertAll() to your VideoDao that
    // takes vararg DatabaseVideo.
    // insertAll() is an upsert, so don’t forget to pass it onConflict=OnConflictStrategy.REPLACE!
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseVideo)
}

// 10.1. Create the VideosDatabase class:
// Create an abstract VideosDatabase class that extends RoomDatabase, and annotate it with
// @Database, including entities and version.
@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase: RoomDatabase() {
    // 10.2. Add the Dao:
    //Inside VideosDatabase, add an abstract videoDao variable.
    abstract val videoDao: VideoDao
}



// 10.3. Create a variable for you singleton:
//In Room.kt, define an INSTANCE variable to store the singleton.
private lateinit var INSTANCE: VideosDatabase

// 10.4. Define a getDatabase() function to return the VideosDatabase:
@InternalCoroutinesApi
fun getDatabase(context: Context): VideosDatabase {
    // 10.5. Check whether the database has been initialized:
    //Inside getDatabase(), use ::INSTANCE.isInitialized to check if the variable has been
    // initialized. If it hasn't, then initialize it.
    // 10.6. Make sure your code is synchronized so it’s thread safe:
    // Wrap the if-statement above with the following statement:
    // Kotlin recommended adding @InternalCoroutinesApi
    synchronized(VideosDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                VideosDatabase::class.java,
                "videos"
            ).build()
        }
    }
    return INSTANCE
}
