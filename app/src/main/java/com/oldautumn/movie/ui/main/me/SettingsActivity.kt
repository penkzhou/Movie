package com.oldautumn.movie.ui.main.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oldautumn.movie.BuildConfig
import com.oldautumn.movie.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class ModalBottomSheet : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val root = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)
            
            return root
        }

        companion object {
            const val TAG = "ModalBottomSheet"
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val preference = findPreference<Preference>("about")
            preference?.title = "Version ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"


            val logoutPreference = findPreference<Preference>("logout")
            logoutPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                // do something
                val modalBottomSheet = ModalBottomSheet()
                modalBottomSheet.show(childFragmentManager, ModalBottomSheet.TAG)
                true
            }
        }
    }
}