package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rol")
data class RolEntity(
    @PrimaryKey
    val idRol: Int,
    @ColumnInfo(name = "nombre", defaultValue = "Admnistrador")
    val nombre: String
)
