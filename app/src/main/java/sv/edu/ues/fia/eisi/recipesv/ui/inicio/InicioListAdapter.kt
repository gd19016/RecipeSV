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
import sv.edu.ues.fia.eisi.recipesv.entity.RecetaEntidad

class InicioListAdapter (onInicioClickListener: InicioListAdapter.OnInicioClickListener) :
    ListAdapter<RecetaEntidad, InicioListAdapter.InicioViewHolder>(InicioComparator()) {
        private val mOnInicioClickListener = onInicioClickListener

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InicioViewHolder {
            return InicioViewHolder.create(parent, mOnInicioClickListener)
        }
        override fun onBindViewHolder(holder: InicioViewHolder, position: Int) {
            val current = getItem(position)
            holder.bind(current)
        }

        class InicioViewHolder(itemView: View, private val onInicioClickListener: OnInicioClickListener) :
            RecyclerView.ViewHolder(itemView) {
            //private val idReceta: TextView = itemView.findViewById(R.id.item_id)
            private val nombre: TextView = itemView.findViewById(R.id.item_name)
            /*private val updateButton: ImageButton = itemView.findViewById(R.id.update_button)
            private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)*/
            private val previewButton: ImageButton = itemView.findViewById(R.id.preview_button)
            fun bind(receta: RecetaEntidad) {
                //idReceta.text = receta.idReceta.toString()
                nombre.text = receta.nombre
                /*updateButton.setOnClickListener {
                    onInicioClickListener.onEditInicioClicked(receta)
                }
                deleteButton.setOnClickListener {
                    onInicioClickListener.onDeleteInicioClicked(receta)
                }*/
                previewButton.setOnClickListener {
                    onInicioClickListener.onPreviewInicioClicked(receta)
                }
            }
            companion object {
                fun create(parent: ViewGroup, onInicioClickListener: OnInicioClickListener):
                        InicioViewHolder {
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recyclerview_list, parent, false)
                    return InicioViewHolder(view, onInicioClickListener)
                }
            }
        }
        class InicioComparator : DiffUtil.ItemCallback<RecetaEntidad>() {
            override fun areItemsTheSame(oldItem: RecetaEntidad, newItem: RecetaEntidad): Boolean {
                return oldItem === newItem
            }
            override fun areContentsTheSame(oldItem: RecetaEntidad, newItem: RecetaEntidad):
                    Boolean {
                return oldItem.idReceta == newItem.idReceta
            }
        }
        interface OnInicioClickListener {
            /*fun onEditInicioClicked(receta: RecetaEntidad)
            fun onDeleteInicioClicked(receta: RecetaEntidad)*/
            fun onPreviewInicioClicked(receta: RecetaEntidad)
        }
}