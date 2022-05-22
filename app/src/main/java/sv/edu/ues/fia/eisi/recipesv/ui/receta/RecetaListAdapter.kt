package sv.edu.ues.fia.eisi.recipesv.ui.receta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.db.RecetaEntity

class RecetaListAdapter (onRecetaClickListener: OnRecetaClickListener) :
    ListAdapter<RecetaEntity, RecetaListAdapter.RecetaViewHolder>(RecetaComparator()) {
    private val mOnRecetaClickListener = onRecetaClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {
        return RecetaViewHolder.create(parent, mOnRecetaClickListener)
    }
    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class RecetaViewHolder(itemView: View, private val onRecetaClickListener: OnRecetaClickListener) :
        RecyclerView.ViewHolder(itemView) {
        //private val idReceta: TextView = itemView.findViewById(R.id.item_id)
        private val nombre: TextView = itemView.findViewById(R.id.item_name)
        private val updateButton: ImageButton = itemView.findViewById(R.id.update_button)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
        fun bind(receta: RecetaEntity) {
            //idReceta.text = receta.idReceta.toString()
            nombre.text = receta.nombre
            updateButton.setOnClickListener {
                onRecetaClickListener.onEditRecetaClicked(receta)
            }
            deleteButton.setOnClickListener {
                onRecetaClickListener.onDeleteRecetaClicked(receta)
            }
        }
        companion object {
            fun create(parent: ViewGroup, onRecetaClickListener: OnRecetaClickListener):
                    RecetaViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_mtto, parent, false)
                return RecetaViewHolder(view, onRecetaClickListener)
            }
        }
    }
    class RecetaComparator : DiffUtil.ItemCallback<RecetaEntity>() {
        override fun areItemsTheSame(oldItem: RecetaEntity, newItem: RecetaEntity): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: RecetaEntity, newItem: RecetaEntity):
                Boolean {
            return oldItem.idReceta == newItem.idReceta
        }
    }
    interface OnRecetaClickListener {
        fun onEditRecetaClicked(receta: RecetaEntity)
        fun onDeleteRecetaClicked(receta: RecetaEntity)
    }
}