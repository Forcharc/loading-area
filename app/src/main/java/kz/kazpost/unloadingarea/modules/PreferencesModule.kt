package kz.kazpost.unloadingarea.modules

import android.content.Context
import android.content.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kz.kazpost.unloadingarea.database.PREFERENCES_FILE_KEY
import kz.kazpost.unloadingarea.database.Preferences
import kz.kazpost.unloadingarea.database.PreferencesImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract  class PreferencesModule {
    @Binds
    abstract fun bindPreferences(prefImpl: PreferencesImpl): Preferences


    companion object {
        @Singleton
        @Provides
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        }
    }
}