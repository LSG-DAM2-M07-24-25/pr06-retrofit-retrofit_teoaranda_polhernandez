package com.lasalle.triviaLegends.di

import android.content.Context
import androidx.room.Room
import com.lasalle.triviaLegends.data.api.TriviaApiService
import com.lasalle.triviaLegends.data.database.TriviaDatabase
import com.lasalle.triviaLegends.data.database.dao.UserScoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    // Proveir la base de dades
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TriviaDatabase {
        return Room.databaseBuilder(
            context,
            TriviaDatabase::class.java,
            "trivia_database"
        ).build()
    }
    
    // Proveir el DAO de puntuacions
    @Provides
    @Singleton
    fun provideUserScoreDao(database: TriviaDatabase): UserScoreDao {
        return database.userScoreDao()
    }
    
    // Proveir el client HTTP amb logging
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
    
    // Proveir el servei de l'API
    @Provides
    @Singleton
    fun provideTriviaApiService(okHttpClient: OkHttpClient): TriviaApiService {
        return Retrofit.Builder()
            .baseUrl(TriviaApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TriviaApiService::class.java)
    }
} 