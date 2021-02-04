package kz.kazpost.loadingarea.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kz.kazpost.loadingarea.database.PREFERENCES_FILE_KEY
import kz.kazpost.loadingarea.database.RoomDatabase
import kz.kazpost.loadingarea.database.UserPreferences
import kz.kazpost.loadingarea.database.UserPreferencesImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
    @Binds
    abstract fun bindPreferences(prefImpl: UserPreferencesImpl): UserPreferences


    companion object {
        @Singleton
        @Provides
        fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        }

        @Singleton
        @Provides
        fun provideRoomDatabase(@ApplicationContext context: Context): RoomDatabase {
            return Room.databaseBuilder(
                context,
                RoomDatabase::class.java, "app-database"
            ).build()
        }
    }
}