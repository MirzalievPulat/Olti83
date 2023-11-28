package uz.frodo.olti83.listeners

import uz.frodo.olti83.room.entities.Course

interface CourseInterface {
    fun delete(course: Course)
    fun edit(course: Course)
    fun itemClick(course: Course)
}