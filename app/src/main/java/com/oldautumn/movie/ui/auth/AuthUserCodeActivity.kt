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
package com.oldautumn.movie.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.oldautumn.movie.databinding.ActivityAuthUserCodeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthUserCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthUserCodeBinding

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthUserCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToSplash.setOnClickListener {
            finish()
        }

        viewModel.fetchDeviceCode()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    if (uiState.deviceCode != null) {
                        if (uiState.deviceCode.user_code.isNotEmpty()) {
                            binding.userCode.text = uiState.deviceCode.user_code
                        }
                        binding.userCodeAuth.setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW)
                            intent.data = Uri.parse(uiState.deviceCode.verification_url)
                            startActivity(intent)
                        }
                    }

                    if (uiState.isDeviceAuthed) {
                        binding.backToSplash.visibility = View.VISIBLE
                        binding.userCodeAuth.visibility = View.GONE
                    } else {
                        binding.backToSplash.visibility = View.GONE
                        binding.userCodeAuth.visibility = View.VISIBLE
                        // 开始走授权流程，通过拿到的usercode 开始
                        if (uiState.deviceCode != null &&
                            uiState.deviceCode.device_code.isNotEmpty()
                        ) {
                            viewModel.fetchDeviceToken(uiState.deviceCode.device_code)
                        }
                    }
                }
            }
        }
    }
}
