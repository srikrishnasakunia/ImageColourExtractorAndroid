package dev.krishna.imagecolourextractor.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.krishna.imagecolourextractor.util.permission.PermissionManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providePermissionManager(@ApplicationContext context: Context): PermissionManager {
        return PermissionManager(context)
    }
}