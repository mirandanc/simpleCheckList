package com.example.todo.di

import android.content.Context
import androidx.room.Room
import com.example.todo.showlist.data.local.DataBase.CheckListDao
import com.example.todo.showlist.data.local.DataBase.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideToDoDatabase(
        @ApplicationContext context: Context,
        checkListDao: dagger.Lazy<CheckListDao>
    ): ToDoDatabase {
        return Room.databaseBuilder(
            context,
            ToDoDatabase::class.java,
            "todo_database"
        ).addCallback(ToDoDatabase.prePopulateCallback(checkListDao)).build()
    }

    @Provides
    @Singleton
    fun provideCheckListDao(database: ToDoDatabase): CheckListDao {
        return database.checkListDao()
    }
}