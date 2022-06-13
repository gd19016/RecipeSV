package sv.edu.ues.fia.eisi.recipesv.ui.usuario

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import sv.edu.ues.fia.eisi.recipesv.R
import sv.edu.ues.fia.eisi.recipesv.entity.Usuario


class UsuarioListAdapter (onUsuarioClickListener: OnUsuarioClickListener) :
    ListAdapter<Usuario, UsuarioListAdapter.UsuarioViewHolder>(UsuarioComparator()) {
    private val mOnUsuarioClickListener = onUsuarioClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        return UsuarioViewHolder.create(parent, mOnUsuarioClickListener)
    }
    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class UsuarioViewHolder(itemView: View, onUsuarioClickListener: OnUsuarioClickListener) :
        RecyclerView.ViewHolder(itemView){

        private val onUsuarioClickListener = onUsuarioClickListener
        //private val email: TextView = itemView.findViewById(R.id.item_id)
        private val nombre: TextView = itemView.findViewById(R.id.item_name)
        private val updateButton: ImageButton = itemView.findViewById(R.id.update_button)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)
        fun bind(usuario: Usuario) {
            //email.text = usuario.email.toString()
            nombre.text = usuario.nombre
            updateButton.setOnClickListener {
                onUsuarioClickListener.onEditUsuarioClicked(usuario)
            }
            deleteButton.setOnClickListener {
                onUsuarioClickListener.onDeleteUsuarioClicked(usuario)
            }
        }
        companion object {
            fun create(parent: ViewGroup, onUsuarioClickListener: OnUsuarioClickListener):
                    UsuarioViewHolder {
                val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_mtto, parent, false)
                return UsuarioViewHolder(view, onUsuarioClickListener)
            }
        }
    }
    class UsuarioComparator : DiffUtil.ItemCallback<Usuario>() {
        override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario):
                Boolean {
            return oldItem.email == newItem.email
        }
    }
    interface OnUsuarioClickListener {
        fun onEditUsuarioClicked(usuario: Usuario)
        fun onDeleteUsuarioClicked(usuario: Usuario)

        }
}