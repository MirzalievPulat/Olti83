package uz.frodo.olti83.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Course(
    var name:String,
    var coursePhoto:String,
    @PrimaryKey(autoGenerate = true)
var id:Int=0
):Serializable{

}
