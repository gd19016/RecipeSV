package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receta")
data class RecetaEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_receta", defaultValue = "0")
    val idReceta: Int,
    val nombre: String,
    val descripcion: String,
    val pasos: String,
    val tiempo: Int
)