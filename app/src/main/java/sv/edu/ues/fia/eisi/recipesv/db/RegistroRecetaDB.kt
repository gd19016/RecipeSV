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
        FavoritoEntity::class
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
            // Limpiar base
            db.recetaDao().deleteAll()
            db.coleccionesDao().deleteAll()
            db.usuarioDao().deleteAll()
            db.favoritoDao().deleteAll()
            db.colRecetaDao().deleteAll()
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