package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coleccion_recetas")
class ColeccionRecetasEntity (
    @PrimaryKey(autoGenerate = true)
    val idColeccionReceta: Int,
    val idReceta: Int,
    val idColeccion: Int
)