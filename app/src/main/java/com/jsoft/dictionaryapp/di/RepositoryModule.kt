package com.jsoft.dictionaryapp.di

import com.jsoft.dictionaryapp.data.DictionaryRepositoryImpl
import com.jsoft.dictionaryapp.domain.repository.DictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton

    fun getDictionaryRepository(
        dictionaryRepositoryImpl: DictionaryRepositoryImpl
    ): DictionaryRepository
}