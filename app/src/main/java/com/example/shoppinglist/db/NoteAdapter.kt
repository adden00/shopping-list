package com.example.shoppinglist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.NoteListItemBinding
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.utils.HtmlManager

class NoteAdapter(private val listener: Listener): ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) {


    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) { // каждый объект класса хранит ссылку на одну пазметку list_item
        private val binding = NoteListItemBinding.bind(view)


        fun setData(note: NoteItem, listener: Listener){
            binding.tvTitle.text = note.title
            binding.tvDescription.text = HtmlManager.getHtml(note.content).trim()
            binding.tvTime.text = note.time
            binding.imDelete.setOnClickListener{
                listener.deleteItem(note.id!!)
            }
            itemView.setOnClickListener{
                listener.onClickItem(note)
            }
        }
        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false))
            }
        }

    }

    class ItemComparator: DiffUtil.ItemCallback<NoteItem>(){
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    interface Listener{
        fun deleteItem(id: Int)
        fun onClickItem(note: NoteItem)
    }
}