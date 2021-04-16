package kz.kazpost.loadingarea

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import dagger.hilt.android.AndroidEntryPoint
import kz.kazpost.loadingarea.base.NavigateUpActivity
import kz.kazpost.loadingarea.databinding.ActivityOneAndOnlyBinding

@AndroidEntryPoint
class OneAndOnlyActivity : NavigateUpActivity() {
    private lateinit var binding: ActivityOneAndOnlyBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOneAndOnlyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.authFragment, R.id.transportFragment))

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        // For some reason app's name is present on start destination fragment's title, so we change title manually
        supportActionBar?.title = getString(R.string.authorization)
        // Show app name on app's every page (so that workers won't confuse apps)
        supportActionBar?.subtitle = getString(R.string.app_name)
    }


}