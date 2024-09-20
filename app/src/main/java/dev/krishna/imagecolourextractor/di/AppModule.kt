package dev.krishna.imagecolourextractor.di

import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.krishna.imagecolourextractor.data.datasource.ImageDataSource
import dev.krishna.imagecolourextractor.data.repository.ImageRepositoryImpl
import dev.krishna.imagecolourextractor.domaim.repository.ImageRepository
import dev.krishna.imagecolourextractor.domaim.usecase.GetAllImagesUseCase
import dev.krishna.imagecolourextractor.domaim.usecase.SaveImageUseCase
import dev.krishna.imagecolourextractor.util.permission.PermissionManager

/*@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher


@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher*/
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providePermissionManager(@ApplicationContext context: Context): PermissionManager {
        return PermissionManager(context)
    }

    /*@IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default*/

    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    fun provideImageDataSource(contentResolver: ContentResolver): ImageDataSource {
        return ImageDataSource(contentResolver)
    }

    @Provides
    fun provideImageRepository(dataSource: ImageDataSource): ImageRepository {
        return ImageRepositoryImpl(dataSource)
    }

    @Provides
    fun provideGetAllImagesUseCase(repository: ImageRepository): GetAllImagesUseCase {
        return GetAllImagesUseCase(repository)
    }

    @Provides
    fun provideSaveImageUseCase(repository: ImageRepository): SaveImageUseCase {
        return SaveImageUseCase(repository)
    }
}