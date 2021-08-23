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

package com.example.android.devbyteviewer

import android.app.Application
import android.os.Build
import androidx.work.*
import com.example.android.devbyteviewer.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * Override application to setup background work via WorkManager
 */
class DevByteApplication : Application() {

    // 19.1.1 Create a coroutine scope to use for your application:
    // Open DevByteApplication. Add a coroutine scope variable, applicationScope, which
    // uses Dispatchers.Default:
    val applicationScope = CoroutineScope(Dispatchers.Default)

    // 19.1.2. Create an initialization function that does not block the main thread:
    // It's important to note that WorkManager.initialize should be called from inside
    // onCreate without using a background thread to avoid issues caused when initialization
    // happens after WorkManager is used.
    // Create the function delayedInit() that uses the applicationScope you defined above.
    // It should call a function (which you haven't created yet) called setupRecurringWork().
    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    // 19.1.4. Make a PeriodWorkRequest:
    // Create a setupRecurringWork() function. In it, define a repeatingRequest variable
    // that uses a PeriodicWorkRequestBuilder to create a PeriodicWorkRequest for your
    // RefreshDataWorker. It should run once every day.
    // 19.2.1. Define constraints:
    // Open DevByteApplication.kt. In setupRecurringWork(), use a Builder to define
    // constraints In setupRecurringWork(). Define constraints to prevent work from
    // occurring when there is no network access or the device is low on battery.
    private fun setupRecurringWork() {
        // 19.2.1
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        // 19.2.2. Add the constraints:
        // Finally, add the constraint to the repeatingRequest definition.
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1,TimeUnit.DAYS)
            .setConstraints(constraints).build()

        // 19.1.5. Schedule the work as unique:
        // Get an instance of WorkManager and call enqueueUniquePeriodicWork to schedule the work.
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
    /**
     * onCreate is called before the first screen is shown to the user.
     *
     * Use it to setup any background tasks, running expensive setup operations in a background
     * thread to avoid delaying app start.
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        // Extra step - to show that you shouldn't do a lot of work in onCreate.
        //Thread.sleep(4_000)
        // 19.1.3. Add a call to delayedInit() in onCreate().
        delayedInit()
    }
}
