package sv.edu.ues.fia.eisi.recipesv.ui.colecciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.db.ColeccionesEntity

class ColeccionesListAdapter (onColeccionesClickListener: ColeccionesListAdapter.OnColeccionesClickListener) :
    ListAdapter<ColeccionesEntity, ColeccionesListAdapter.ColeccionesViewHolder>(ColeccionesListAdapter.ColeccionesComparator()) {

    private val mOnColeccionesClickListener = onColeccionesClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColeccionesViewHolder {
        return ColeccionesViewHolder.create(parent, mOnColeccionesClickListener)
    }
    override fun onBindViewHolder(holder: ColeccionesViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ColeccionesViewHolder(itemView: View, private val onColeccionesClickListener: OnColeccionesClickListener) :
        RecyclerView.ViewHolder(itemView) {

        private val idColeccion: TextView = itemView.findViewById(R.id.item_id)
        private val nombre: TextView = itemView.findViewById(R.id.item_name)
        private val descripcion: TextView = itemView.findViewById(R.id.item_name2)
        private val updateButton: ImageButton = itemView.findViewById(R.id.update_button)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)

        fun bind(coleccion: ColeccionesEntity) {

            idColeccion.text = coleccion.idColeccion.toString()
            nombre.text = coleccion.nombre
            descripcion.text = coleccion.descripcion

            updateButton.setOnClickListener {
                onColeccionesClickListener.onEditColeccionesClicked(coleccion)
            }
            deleteButton.setOnClickListener {
                onColeccionesClickListener.onDeleteColeccionesClicked(coleccion)
            }
        }

        companion object {
            fun create(parent: ViewGroup, onColeccionesClickListener: OnColeccionesClickListener):
                    ColeccionesViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_mtto, parent, false)
                return ColeccionesViewHolder(view, onColeccionesClickListener)
            }
        }
    }
    class ColeccionesComparator : DiffUtil.ItemCallback<ColeccionesEntity>() {
        override fun areItemsTheSame(oldItem: ColeccionesEntity, newItem: ColeccionesEntity): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: ColeccionesEntity, newItem: ColeccionesEntity):
                Boolean {
            return oldItem.idColeccion == newItem.idColeccion
        }
    }
    interface OnColeccionesClickListener {
        fun onEditColeccionesClicked(coleccion: ColeccionesEntity)
        fun onDeleteColeccionesClicked(coleccion: ColeccionesEntity)
    }

}