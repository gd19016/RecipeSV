package sv.edu.ues.fia.eisi.recipesv.ui.inicio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.db.ColeccionRecetasEntity

class ColeccionListAdapter (onColeccionClickListener: ColeccionListAdapter.OnColeccionClickListener) :
    ListAdapter<ColeccionRecetasEntity, ColeccionListAdapter.ColeccionViewHolder>(ColeccionComparator()) {
    private val mOnColeccionClickListener = onColeccionClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColeccionViewHolder {
        return ColeccionViewHolder.create(parent, mOnColeccionClickListener)
    }
    override fun onBindViewHolder(holder: ColeccionViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ColeccionViewHolder(itemView: View, private val onColeccionClickListener: OnColeccionClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.item_name)
        private val previewButton: ImageButton = itemView.findViewById(R.id.preview_button)
        fun bind(coleccion: ColeccionRecetasEntity) {
            nombre.text = coleccion.idColeccion.toString()
            previewButton.setOnClickListener {
                onColeccionClickListener.onDeleteColeccionClicked(coleccion)
            }
        }
        companion object {
            fun create(parent: ViewGroup, onColeccionClickListener: OnColeccionClickListener):
                    ColeccionViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_list, parent, false)
                return ColeccionViewHolder(view, onColeccionClickListener)
            }
        }
    }
    class ColeccionComparator : DiffUtil.ItemCallback<ColeccionRecetasEntity>() {
        override fun areItemsTheSame(oldItem: ColeccionRecetasEntity, newItem: ColeccionRecetasEntity): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: ColeccionRecetasEntity, newItem: ColeccionRecetasEntity):
                Boolean {
            return oldItem.idColeccion == newItem.idColeccion
        }
    }
    interface OnColeccionClickListener {
        fun onDeleteColeccionClicked(coleccion: ColeccionRecetasEntity)
    }
}