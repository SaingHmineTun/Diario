package it.saimao.diario.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DiarioEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DiarioDatabase : RoomDatabase() {
    abstract fun diarioDao(): DiarioDao

    companion object {
        @Volatile
        private var instance: DiarioDatabase? = null
        fun getDatabase(context: Context): DiarioDatabase {
            return instance ?: synchronized(this){
                Room.databaseBuilder(context, DiarioDatabase::class.java, "diario_database")
                    .build()
                    .also { instance = it }
            }
        }
    }

}