package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dificultad")
data class DificultadEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_dificultad", defaultValue = "0")
    val idDificultad: Int,
    val nombre: String
)