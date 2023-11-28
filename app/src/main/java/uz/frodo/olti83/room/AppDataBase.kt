package uz.frodo.olti83.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Lesson
import uz.frodo.olti83.room.entities.Module

@Database(entities = [Course::class,Module::class,Lesson::class], version = 4)
abstract class AppDataBase: RoomDatabase() {
    abstract fun dao():Dao

    companion object{
        private var INSTANCE:AppDataBase? = null

        @Synchronized
        fun getInstance(context: Context):AppDataBase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,AppDataBase::class.java,"app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE!!
        }
    }
}