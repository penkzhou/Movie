package com.oldautumn.movie.ui.main.me

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.oldautumn.movie.R
import com.oldautumn.movie.data.api.model.DeviceCode
import com.oldautumn.movie.databinding.FragmentMeBinding
import com.oldautumn.movie.ui.auth.AuthUserCodeActivity
import com.oldautumn.movie.ui.splash.TAG
import com.oldautumn.movie.utils.Utils.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeFragment : Fragment() {

    private val viewModel: MeViewModel by viewModels()
    private var _binding: FragmentMeBinding? = null

    private val binding get() = _binding!!

    private lateinit var menuHost: MenuHost

    override fun onAttach(context: Context) {
        super.onAttach(context)
        menuHost = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.setting.setOnClickListener {
            val intent = Intent(context, SettingsActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            launchAndRepeatWithViewLifecycle(Lifecycle.State.STARTED) {
                viewModel.fetchAuthString()
                viewModel.uiState.collect { uiState ->
                    if (uiState.isAuthed) {
                        binding.login.visibility = View.GONE
                        viewModel.fetchUserInfo(uiState.authString)
                    } else {
                        binding.login.visibility = View.VISIBLE
                        binding.login.setOnClickListener {
                            val intent = Intent(context, AuthUserCodeActivity::class.java)
                            startActivityForResult.launch(intent)
                        }
                    }
                    if (uiState.isAuthed && uiState.userSettings != null) {
                        binding.username.text = uiState.userSettings.user.username
                        binding.desc.text = uiState.userSettings.user.about
                        binding.username.visibility = View.VISIBLE
                        binding.desc.visibility = View.VISIBLE
                        binding.avatar.visibility = View.VISIBLE
                        binding.avatar.load(uiState.userSettings.user.images.avatar.full) {
                            transformations(CircleCropTransformation())
                        }
                        binding.follow.visibility = View.VISIBLE
                        binding.follow.text = "关注 ${uiState.userSettings.limits.list.count}"
                    }
                }
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    // Add menu items here
                    menuInflater.inflate(R.menu.menu_me, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    // Handle the menu selection
                    return when (menuItem.itemId) {
                        R.id.action_settings -> {
                            val intent = Intent(context, SettingsActivity::class.java)
                            startActivity(intent)
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val startActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val authResult: Boolean = it.data?.getBooleanExtra("device_auth", false) ?: false
            Log.e(TAG, "startActivityForResult back, authResult is : $authResult")
            if (authResult) {
                val deviceCode = it.data?.getParcelableExtra<DeviceCode>("device_code")
                viewModel.fetchDeviceToken(deviceCode?.device_code ?: "")
            }
        }
}
