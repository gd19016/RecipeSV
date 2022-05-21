package sv.edu.ues.fia.eisi.recipesv.ui.ingrediente


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.db.IngredienteEntity

class IngredienteListAdapter (onIngredienteClickListener: OnIngredienteClickListener) :
    ListAdapter<IngredienteEntity, IngredienteListAdapter.IngredienteViewHolder>(IngredienteComparator()) {
    private val mOnIngredienteClickListener = onIngredienteClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredienteViewHolder {
        return IngredienteViewHolder.create(parent, mOnIngredienteClickListener)
    }
    override fun onBindViewHolder(holder: IngredienteViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class IngredienteViewHolder(itemView: View, private val onIngredienteClickListener: OnIngredienteClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val idIngrediente: TextView = itemView.findViewById(R.id.item_id)
        private val nombre: TextView = itemView.findViewById(R.id.item_name)
        private val updateButton: ImageButton = itemView.findViewById(R.id.update_button)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
        fun bind(ingrediente: IngredienteEntity) {
            idIngrediente.text = ingrediente.idIngrediente.toString()
            nombre.text = ingrediente.nombre
            updateButton.setOnClickListener {
                onIngredienteClickListener.onEditIngredienteClicked(ingrediente)
            }
            deleteButton.setOnClickListener {
                onIngredienteClickListener.onDeleteIngredienteClicked(ingrediente)
            }
        }
        companion object {
            fun create(parent: ViewGroup, onIngredienteClickListener: OnIngredienteClickListener):
                    IngredienteViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_mtto, parent, false)
                return IngredienteViewHolder(view, onIngredienteClickListener)
            }
        }
    }
    class IngredienteComparator : DiffUtil.ItemCallback<IngredienteEntity>() {
        override fun areItemsTheSame(oldItem: IngredienteEntity, newItem: IngredienteEntity): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: IngredienteEntity, newItem: IngredienteEntity):
                Boolean {
            return oldItem.idIngrediente == newItem.idIngrediente
        }
    }
    interface OnIngredienteClickListener {
        fun onEditIngredienteClicked(ingrediente: IngredienteEntity)
        fun onDeleteIngredienteClicked(ingrediente: IngredienteEntity)
    }
}