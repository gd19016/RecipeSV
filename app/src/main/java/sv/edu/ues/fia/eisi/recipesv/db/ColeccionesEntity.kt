package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colecciones")
class ColeccionesEntity (
    @PrimaryKey
    @ColumnInfo(name = "id_coleccion", defaultValue = "0")
    val idColeccion: Int,

    val nombre: String,
    val descripcion: String,
    val idUsuario: Int
)