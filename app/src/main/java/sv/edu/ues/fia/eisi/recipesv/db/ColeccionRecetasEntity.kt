package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "coleccion_recetas",
    primaryKeys = ["idReceta", "idColeccion"]
)
class ColeccionRecetasEntity (
    val idReceta: Int,
    val idColeccion: Int
)