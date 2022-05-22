package sv.edu.ues.fia.eisi.recipesv.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        RecetaEntity::class,
        ColeccionesEntity::class,
        UsuarioEntity::class,
        ColeccionRecetasEntity::class,
        FavoritoEntity::class,
        DificultadEntity::class,
        TipoEntity::class,
        RolEntity::class,
        IngredienteEntity::class,
        HistoricoEntity::class,
        ComentarioEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class RegistroRecetaDB : RoomDatabase() {
    abstract fun recetaDao(): RecetaDao
    abstract fun coleccionesDao(): ColeccionesDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun favoritoDao(): FavoritoDao
    abstract fun colRecetaDao(): ColeccionRecetaDao
    abstract fun dificultadDao(): DificultadDao
    abstract fun tipoDao(): TipoDao
    abstract fun rolDao(): RolDao
    abstract fun ingredienteDao(): IngredienteDao
    abstract fun historicoDao(): HistoricoDao
    abstract fun comentarioDao(): ComentarioDao

    private class RecetaDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database)
                }
            }
        }
        suspend fun populateDatabase(db: RegistroRecetaDB) {
            db.recetaDao().deleteAll()
            db.coleccionesDao().deleteAll()
            db.usuarioDao().deleteAll()
            db.favoritoDao().deleteAll()
            db.colRecetaDao().deleteAll()
            db.dificultadDao().deleteAll()
            db.tipoDao().deleteAll()
            db.rolDao().deleteAll()
            db.ingredienteDao().deleteAll()
            db.historicoDao().deleteAll()
            db.comentarioDao().deleteAll()

            db.rolDao().insert(RolEntity(0,"Administrador"))
            db.rolDao().insert(RolEntity(1,"Usuario"))

            db.dificultadDao().insert(DificultadEntity(0, "Fácil"))
            db.dificultadDao().insert(DificultadEntity(1, "Media"))
            db.dificultadDao().insert(DificultadEntity(2, "Difícil"))

            db.tipoDao().insert(TipoEntity(0,"Desayuno"))
            db.tipoDao().insert(TipoEntity(1,"Almuerzo"))
            db.tipoDao().insert(TipoEntity(2,"Cena"))
            db.tipoDao().insert(TipoEntity(3,"Merienda"))
            db.tipoDao().insert(TipoEntity(4,"Refrigerio"))

            db.usuarioDao().insert(UsuarioEntity("gd19016@ues.edu.sv","123456","Roberto Gutiérrez","0"))
            db.usuarioDao().insert(UsuarioEntity("hr18015@ues.edu.sv","123456","Cicely Hernández","0"))
            db.usuarioDao().insert(UsuarioEntity("nm19010@ues.edu.sv","123456","Kevin Núñez","0"))
            db.usuarioDao().insert(UsuarioEntity("hm11019@ues.edu.sv","123456","Erick Hernández","0"))
            db.usuarioDao().insert(UsuarioEntity("karens.medrano@ues.edu.sv","123456","Karens Medrano","0"))

            db.coleccionesDao().insert(ColeccionesEntity(0,"Mi Colección","", 0))
        }
    }
    companion object {
        @Volatile
        private var INSTANCE: RegistroRecetaDB? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): RegistroRecetaDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RegistroRecetaDB::class.java,
                    "registro_receta_db"
                ).addCallback(RecetaDBCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}