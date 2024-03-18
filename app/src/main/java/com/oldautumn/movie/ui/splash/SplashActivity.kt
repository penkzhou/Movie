/*
 * Copyright 2023 The Old Autumn Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oldautumn.movie.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.ui.auth.AuthUserCodeActivity
import com.oldautumn.movie.ui.main.IndexActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val TAG = "SplashActivity"

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchAuthString()
                viewModel.uiState.collect { uiState ->
                    if (uiState.isAuthed) {
                        val intent =
                            Intent(
                                this@SplashActivity,
                                IndexActivity::class.java
                            )
                        startActivity(intent)
                        finish()
                    } else if (uiState.deviceCode.user_code.isNotEmpty()) {
                        // 开始走授权流程，通过拿到的usercode 开始
                        val intent =
                            Intent(
                                this@SplashActivity,
                                AuthUserCodeActivity::class.java
                            )
                        intent.putExtra("device_code", uiState.deviceCode)
                        startActivityForResult.launch(intent)
                    } else {
                        viewModel.fetchDeviceCode()
                    }
                }
            }
        }
    }

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val authResult: Boolean =
                it.data?.getBooleanExtra("device_auth", false) ?: false
            Log.e(TAG, "startActivityForResult back, authResult is : $authResult")
            if (authResult) {
                val deviceCode = it.data?.getParcelableExtra<DeviceCode>("device_code")
                viewModel.fetchDeviceToken(deviceCode?.device_code ?: "")
            }
        }
}
