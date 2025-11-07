package com.example.todo.showlist.data.local.DataBase

import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors
import androidx.room.TypeConverters
import androidx.room.RoomDatabase
import androidx.room.Database
import java.time.LocalDateTime
import java.util.UUID

@Database(
    entities = [CheckListEntity::class, CheckEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ToDoDatabase : RoomDatabase() {

    abstract fun checkListDao(): CheckListDao

    companion object {
        fun prePopulateCallback(checkListDao: dagger.Lazy<CheckListDao>) = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Executors.newSingleThreadExecutor().execute {
                    kotlinx.coroutines.runBlocking {
                        val mockData = createMockData()
                        mockData.forEach { (checklist, checks) ->
                            checkListDao.get().insertCheckList(checklist)
                            checkListDao.get().insertChecks(checks)
                        }
                    }
                }
            }
        }
        private fun createMockData(): Map<CheckListEntity, List<CheckEntity>> {
            val data = mutableMapOf<CheckListEntity, List<CheckEntity>>()
            val currentTime = LocalDateTime.now()
            
            val mercadoListId = UUID.randomUUID()
            data[CheckListEntity(id = mercadoListId, title = "Mercado", createdAt = currentTime)] = listOf(
                CheckEntity(listId = mercadoListId, description = "Leite", checked = false),
                CheckEntity(listId = mercadoListId, description = "Ovo", checked = false),
                CheckEntity(listId = mercadoListId, description = "Pão", checked = false)
            )
            
            val trabalhoListId = UUID.randomUUID()
            data[CheckListEntity(id = trabalhoListId, title = "Trabalho", createdAt = currentTime)] = listOf(
                CheckEntity(listId = trabalhoListId, description = "Fazer mock app", checked = true),
                CheckEntity(listId = trabalhoListId, description = "Receber aprovação", checked = false),
                CheckEntity(listId = trabalhoListId, description = "Começar app de verdade", checked = false)
            )
            
            val tarefasListId = UUID.randomUUID()
            data[CheckListEntity(id = tarefasListId, title = "Tarefas domesticas", createdAt = currentTime)] = listOf(
                CheckEntity(listId = tarefasListId, description = "concertar pia", checked = false),
                CheckEntity(listId = tarefasListId, description = "dar banho nos cachorros", checked = false)
            )
            
            val planoListId = UUID.randomUUID()
            data[CheckListEntity(id = planoListId, title = "Plano para ficar rico", createdAt = currentTime)] = listOf(
                CheckEntity(listId = planoListId, description = "Vender drogas", checked = true),
                CheckEntity(listId = planoListId, description = "encontrar uma velha rica", checked = false),
                CheckEntity(listId = planoListId, description = "Entrar para politica", checked = false)
            )
            
            val academiaListId = UUID.randomUUID()
            data[CheckListEntity(id = academiaListId, title = "Academia", createdAt = currentTime)] = listOf(
                CheckEntity(listId = academiaListId, description = "21", checked = false),
                CheckEntity(listId = academiaListId, description = "LatPull", checked = false),
                CheckEntity(listId = academiaListId, description = "Biceps curl", checked = true)
            )

            return data
        }
    }
}