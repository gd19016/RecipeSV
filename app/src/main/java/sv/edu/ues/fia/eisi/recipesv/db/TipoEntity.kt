package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipo")
data class TipoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_tipo", defaultValue = "0")
    val idRTipo: Int,
    val nombre: String
)