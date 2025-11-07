package com.example.todo.di

import com.example.todo.showlist.data.local.CheckListLocalDataSource
import com.example.todo.showlist.data.local.RoomCheckListDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindCheckListLocalDataSource(
        roomDataSource: RoomCheckListDataSource
    ): CheckListLocalDataSource
}