package kz.kazpost.unloadingarea

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.kazpost.unloadingarea.databinding.ActivityOneAndOnlyBinding

@AndroidEntryPoint
class OneAndOnlyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOneAndOnlyBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOneAndOnlyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController)

    }
}