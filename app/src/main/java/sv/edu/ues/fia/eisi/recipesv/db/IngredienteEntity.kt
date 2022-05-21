package sv.edu.ues.fia.eisi.recipesv.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingrediente")
data class IngredienteEntity(
    @PrimaryKey
    @ColumnInfo(name = "id_ingrediente", defaultValue = "0")
    val idIngrediente: Int,
    val nombre: String
)