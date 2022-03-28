package com.oldautumn.movie.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.oldautumn.movie.R
import com.oldautumn.movie.data.auth.AuthLocalDataSource
import com.oldautumn.movie.data.auth.AuthRemoteDataSource
import com.oldautumn.movie.data.auth.AuthRepository
import com.oldautumn.movie.data.api.ApiProvider
import com.oldautumn.movie.data.api.model.DeviceCode
import kotlinx.coroutines.launch


class AuthUserCodeActivity : AppCompatActivity() {
    lateinit var userCode: TextView
    lateinit var authButton: Button
    lateinit var completeButton: Button
    lateinit var deviceCode: DeviceCode

    private val viewModel: AuthViewModel by lazy {
        ViewModelProvider(this, factory)[AuthViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_user_code)
        deviceCode = intent.getParcelableExtra<DeviceCode>("device_code")!!
        userCode = findViewById(R.id.user_code)
        authButton = findViewById(R.id.user_code_auth)
        completeButton = findViewById(R.id.back_to_splash)
        userCode.setText(deviceCode?.user_code)
        authButton.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(deviceCode?.verification_url)
            startActivity(i)
        }

        completeButton.setOnClickListener {
            finish()
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (deviceCode?.device_code.isNotEmpty()) {
                    viewModel.fetchDeviceToken(deviceCode?.device_code)
                }
                viewModel.uiState.collect { uiState ->
                    if (uiState.isDeviceAuthed) {
                        completeButton.visibility = View.VISIBLE
                        authButton.visibility = View.GONE
                    } else {
                        completeButton.visibility = View.GONE
                        authButton.visibility = View.VISIBLE
                        // 开始走授权流程，通过拿到的usercode 开始
                    }

                }
            }
        }
    }


    var factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AuthViewModel(
                AuthRepository(
                    AuthRemoteDataSource(ApiProvider.getApiService()),
                    AuthLocalDataSource(applicationContext)
                )
            ) as T
        }
    }

}