package sv.edu.ues.fia.eisi.recipesv.db

import androidx.room.Entity

@Entity(tableName = "coleccion_recetas")
class ColeccionRecetasEntity (

    val idReceta: Int,
    val idColeccion: Int
)