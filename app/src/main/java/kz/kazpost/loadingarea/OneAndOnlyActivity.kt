package kz.kazpost.loadingarea

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
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

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.toolbar)

        binding.toolbar.setupWithNavController(navController)
    }



}