package uz.frodo.olti83.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import uz.frodo.olti83.room.entities.Lesson
import uz.frodo.olti83.room.entities.Module

data class ModuleWithLessons(
    @Embedded val module: Module,
    @Relation(
        parentColumn = "id",
        entityColumn = "moduleId"
    )
    val lessons:List<Lesson>
)
