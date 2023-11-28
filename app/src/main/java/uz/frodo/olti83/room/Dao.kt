package uz.frodo.olti83.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.google.android.material.circularreveal.CircularRevealHelper.Strategy
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Lesson
import uz.frodo.olti83.room.entities.Module
import uz.frodo.olti83.room.relations.CourseWithModules
import uz.frodo.olti83.room.relations.ModuleWithLessons

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourse(course: Course)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModule(module: Module)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLesson(lesson: Lesson)


    @Update
    fun updateCourse(course: Course)

    @Update
    fun updateModule(module: Module)

    @Update
    fun updateLesson(lesson: Lesson)


//    @Delete
//    fun deleteCourse(course: Course)
//
//        @Delete
//    fun deleteModule(module: Module)

    @Delete
    fun deleteLesson(lesson: Lesson)
    @Transaction
    @Query("DELETE FROM Course WHERE id = :courseId")
    fun deleteCourseWithModulesAndLessons(courseId: Int)

    @Transaction
    @Query("DELETE FROM Module WHERE id = :moduleId")
    fun deleteModuleWithLessons(moduleId: Int)


    @Query("select id from Course where name =:name and coursePhoto =:coursePhoto")
    fun getCourseId(name: String, coursePhoto: String): Int


    @Query("select id from Module where name =:name and `order` =:order and courseId=:courseId")
    fun getModuleId(courseId: Int, name: String, order: Int): Int

    @Query("select id from Lesson where name =:name and `order` =:order and content=:content")
    fun getLessonId(name: String, order: Int, content: String): Int


    @Query("select * from Course")
    fun getAllCourses(): List<Course>

    @Transaction
    @Query("select * from Course where id = :id")
    fun getCourseWithModules(id: Int): List<CourseWithModules>

    @Transaction
    @Query("select * from Module where id = :id")
    fun getModuleWithLessons(id: Int): List<ModuleWithLessons>
}