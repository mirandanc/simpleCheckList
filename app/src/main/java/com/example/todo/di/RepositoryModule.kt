package com.example.todo.di

import com.example.todo.showlist.data.repository.CheckListRepositoryImpl
import com.example.todo.showlist.domain.repository.LocalCheckListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providedCheckListsRepository(impl: CheckListRepositoryImpl): LocalCheckListRepository

}