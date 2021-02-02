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
import kz.kazpost.unloadingarea.database.UserPreferences
import kz.kazpost.unloadingarea.database.UserPreferencesImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract  class PreferencesModule {
    @Binds
    abstract fun bindPreferences(prefImpl: UserPreferencesImpl): UserPreferences


    companion object {
        @Singleton
        @Provides
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        }
    }
}