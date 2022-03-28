package com.oldautumn.movie.ui.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.oldautumn.movie.R
import com.oldautumn.movie.data.auth.AuthLocalDataSource
import com.oldautumn.movie.data.auth.AuthRemoteDataSource
import com.oldautumn.movie.data.auth.AuthRepository
import com.oldautumn.movie.data.api.ApiProvider
import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.ui.auth.AuthUserCodeActivity
import com.oldautumn.movie.ui.main.IndexActivity
import kotlinx.coroutines.launch


const val TAG = "SplashActivity"

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this, factory)[SplashViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchAuthString()
                viewModel.uiState.collect { uiState ->
                    if (uiState.isAuthed) {
                        val intent = Intent(this@SplashActivity, IndexActivity::class.java)
                        startActivity(intent)
                    } else if (uiState.deviceCode.user_code.isNotEmpty()) {
                        // 开始走授权流程，通过拿到的usercode 开始
                        val intent = Intent(this@SplashActivity, AuthUserCodeActivity::class.java)
                        intent.putExtra("device_code", uiState.deviceCode)
                        startActivityForResult.launch(intent)
                    } else {
                        viewModel.fetchDeviceCode()
                    }

                }
            }
        }
    }


    val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            var authResult: Boolean = it.data?.getBooleanExtra("device_auth", false) ?: false
            Log.e(TAG, "startActivityForResult back, authResult is : " + authResult)
            if (authResult) {
                var deviceCode = it.data?.getParcelableExtra<DeviceCode>("device_code")
                viewModel.fetchDeviceToken(deviceCode?.device_code ?: "")
            }
        }

    var factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SplashViewModel(
                AuthRepository(
                    AuthRemoteDataSource(ApiProvider.getApiService()),
                    AuthLocalDataSource(applicationContext)
                )
            ) as T
        }
    }
}