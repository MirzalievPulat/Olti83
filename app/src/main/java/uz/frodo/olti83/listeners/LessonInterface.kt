package uz.frodo.olti83.listeners

import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Lesson

interface LessonInterface {
    fun delete(lesson: Lesson)
    fun edit(lesson: Lesson)
    fun itemClick(lesson: Lesson)
}