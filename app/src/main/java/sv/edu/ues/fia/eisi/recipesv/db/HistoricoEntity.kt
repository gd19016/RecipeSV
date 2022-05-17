package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historico")
data class HistoricoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_receta", defaultValue = "0")
    val idReceta: Int,
    @ColumnInfo(name = "id_usuario", defaultValue = "0")
    val idUsuario: Int
)