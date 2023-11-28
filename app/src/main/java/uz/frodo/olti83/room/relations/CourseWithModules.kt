package uz.frodo.olti83.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Module

data class CourseWithModules(
    @Embedded val course: Course,
    @Relation(
        parentColumn = "id",
        entityColumn = "courseId"
    )
    val modules: List<Module>
)