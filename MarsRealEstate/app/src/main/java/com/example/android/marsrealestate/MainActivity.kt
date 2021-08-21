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

package com.example.android.marsrealestate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
// Wheat(16K Tons), Rice(12K), Maize (0.5K), Barley(44), Pulses(10)
// Rapeseed and Mustard(42), Sunflower(10), Total Oil Seed (52) Sugarcane(558),
// Cotton(1.4K)
// Vegetables: Potato, Onion, cauliflower, lady finger, tomatoes, carrot and raddish
// Fruits: Kinnow(1200K), Guava(182K), Mangoes(113K), Pear(67K), Peach(32K), Litchi(38)
// Orange and Malta(23K), Lemon, Ber(25K)
// Livestock, Fisheries
//
//  bajra - edible seeds of pearl millet
// paddy(unmilled rice); cereal crops like jowar(Sorghum)

class MainActivity : AppCompatActivity() {

    /**
     * Our MainActivity is only responsible for setting the content view that contains the
     * Navigation Host.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
