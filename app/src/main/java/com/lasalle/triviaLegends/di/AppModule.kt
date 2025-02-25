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

/**
 * Mòdul principal de Dagger Hilt per la injecció de dependències
 * Configura totes les dependències que necessitem a l'aplicació
 * 
 * @author Pol & Teo
 * 
 * NOTA POL: He afegit logging per veure les crides a l'API en debug
 * 
 * NOTA TEO: Molt bé! També he posat tot com a Singleton per
 * no crear instàncies innecessàries
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    /**
     * Proveeix la base de dades Room
     * 
     * @param context Context de l'aplicació
     * @return Instància de TriviaDatabase
     */
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
    
    /**
     * Proveeix el DAO per accedir a les puntuacions
     * 
     * @param database Instància de la base de dades
     * @return UserScoreDao per accedir a les puntuacions
     */
    @Provides
    @Singleton
    fun provideUserScoreDao(database: TriviaDatabase): UserScoreDao {
        return database.userScoreDao()
    }
    
    /**
     * Configura i proveeix el client HTTP amb logging
     * 
     * NOTA POL: El logging només s'hauria d'activar en debug
     * NOTA TEO: Ho podem millorar a la fase 2
     */
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
    
    /**
     * Configura i proveeix el servei de l'API
     * 
     * @param okHttpClient Client HTTP configurat
     * @return Instància de TriviaApiService
     */
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