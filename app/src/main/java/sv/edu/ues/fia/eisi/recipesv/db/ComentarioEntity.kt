package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "comentario"
)
data class ComentarioEntity(
    @PrimaryKey
    val idComentario: Int,
    val idUsuario: String,
    val Comentario: String
)
