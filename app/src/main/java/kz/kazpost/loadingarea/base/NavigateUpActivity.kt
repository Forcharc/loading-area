package kz.kazpost.loadingarea.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

open class NavigateUpActivity : AppCompatActivity() {
    private var backPressedCallback: OnBackButtonPressedCallback? = null

    open class OnBackButtonPressedCallback {
        open fun onNavigateUp(
            navController: NavController,
            configuration: AppBarConfiguration
        ) {
            NavigationUI.navigateUp(
                navController,
                configuration
            )
        }
    }

    fun setOnBackButtonPressedCallback(
        lifecycleOwner: LifecycleOwner,
        callback: OnBackButtonPressedCallback
    ) {
        if (lifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED) {
            return
        }

        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                removeAddNavigateUpButtonCallback()
            }
        })
        backPressedCallback = callback
    }

    fun removeAddNavigateUpButtonCallback() {
        backPressedCallback = null
    }

    protected fun Toolbar.setupWithNavController(
        navController: NavController,
        configuration: AppBarConfiguration = AppBarConfiguration(navController.graph)
    ) {
        NavigationUI.setupWithNavController(this, navController, configuration)
        this.setNavigationOnClickListener {
            val callback = backPressedCallback ?: OnBackButtonPressedCallback()
            callback.onNavigateUp(navController, configuration)
        }
    }
}