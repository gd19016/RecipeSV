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
import sv.edu.ues.fia.eisi.recipesv.db.ComentarioEntity

class ComentarioListAdapter (onComentarioClickListener: ComentarioListAdapter.OnComentarioClickListener) :
    ListAdapter<ComentarioEntity, ComentarioListAdapter.ComentarioViewHolder>(ComentarioComparator()) {
    private val mOnComentarioClickListener = onComentarioClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        return ComentarioViewHolder.create(parent, mOnComentarioClickListener)
    }
    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class ComentarioViewHolder(itemView: View, private val onComentarioClickListener: OnComentarioClickListener) :
        RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.item_name)
        private val previewButton: ImageButton = itemView.findViewById(R.id.preview_button)
        fun bind(comentario: ComentarioEntity) {
            nombre.text = comentario.Comentario
            previewButton.setOnClickListener {
                onComentarioClickListener.onDeleteComentarioClicked(comentario)
            }
        }
        companion object {
            fun create(parent: ViewGroup, onComentarioClickListener: OnComentarioClickListener):
                    ComentarioViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_list, parent, false)
                return ComentarioViewHolder(view, onComentarioClickListener)
            }
        }
    }
    class ComentarioComparator : DiffUtil.ItemCallback<ComentarioEntity>() {
        override fun areItemsTheSame(oldItem: ComentarioEntity, newItem: ComentarioEntity): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: ComentarioEntity, newItem: ComentarioEntity):
                Boolean {
            return oldItem.idComentario == newItem.idComentario
        }
    }
    interface OnComentarioClickListener {
        fun onDeleteComentarioClicked(comentario: ComentarioEntity)
    }
}