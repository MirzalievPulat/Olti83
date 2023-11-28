package uz.frodo.olti83.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Course::class,
            parentColumns = ["id"],
            childColumns = ["courseId"],
            onDelete = CASCADE // This line ensures cascade deletion
        )
    ]
)
data class Module(
    var courseId:Int,
    var name:String,
    var order:Int,
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
):Serializable{

}
