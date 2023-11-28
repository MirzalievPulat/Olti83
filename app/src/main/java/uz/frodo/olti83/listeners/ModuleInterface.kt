package uz.frodo.olti83.listeners

import uz.frodo.olti83.room.entities.Course
import uz.frodo.olti83.room.entities.Module

interface ModuleInterface {
    fun delete(module: Module)
    fun edit(module: Module)
    fun itemClick(module: Module)
}