package uz.frodo.olti83.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys = [
    ForeignKey(
        entity = Module::class,
        parentColumns = ["id"],
        childColumns = ["moduleId"],
        onDelete = CASCADE
    )
])
data class Lesson(
    var moduleId:Int,
    var name:String,
    var content:String,
    var order:Int,
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
):Serializable{

}

