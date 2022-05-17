package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorito")
data class FavoritoEntity(
    val idUsuario: Int,
    val idReceta: Int,
)
