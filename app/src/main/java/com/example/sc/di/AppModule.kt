package com.example.sc.di

import android.app.Application
import androidx.room.Room
import com.example.sc.data.ProductDatabase
import com.example.sc.data.ProductRepository
import com.example.sc.data.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideProductDatabase(app: Application): ProductDatabase {
        return Room.databaseBuilder(
            app,
            ProductDatabase::class.java,
            name = "product_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: ProductDatabase): ProductRepository {
        return ProductRepositoryImpl(db.dao)
    }
}