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

package com.example.android.devbyteviewer.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.devbyteviewer.database.getDatabase
import com.example.android.devbyteviewer.repository.VideosRepository
import kotlinx.coroutines.InternalCoroutinesApi
import retrofit2.HttpException

// 18.2. Create a class called RefreshDataWorker that is a CoroutineWorker.
// In work/RefreshDataWorker.kt, add the RefreshDataWorker class. WorkManager workers
// always extend a Worker class. We're going to use a CoroutineWorker, because we want to
// use coroutines to handle our asynchronous code and threading. Have RefreshDataWorker
// extend from the CoroutineWorker class. You also need to pass a Context and
// WorkerParameters to the class and its parent class.
class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    // 19.1.6. Create a unique identifier for your work:
    // In RefreshDataWorker.kt, create a companion object and define a work name that can
    // be used to uniquely identify this worker.
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }


    // 18. 3. Define your work in the doWork method
    // Override the required doWork() method. This is what "work" your RefreshDataWorker
    // does, in our case, syncing with the network. Add variables for the database and
    // the repository. They say to use Payload instead of Result, but its giving an error.
    @InternalCoroutinesApi
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)
        // 18.4. Correctly return success or retry:
        // Use refresh the videos and call Payload() to return SUCCESS or RETRY.
        return try {
            repository.refreshVideos()
            Result.success()
        } catch(e: HttpException){
            Result.retry()
        }
    }
}