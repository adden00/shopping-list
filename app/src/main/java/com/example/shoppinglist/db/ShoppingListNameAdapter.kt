package com.example.shoppinglist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ListNameItemBinding
import com.example.shoppinglist.entities.ShopListNameItem

class ShoppingListNameAdapter(private val listener: Listener): ListAdapter<ShopListNameItem, ShoppingListNameAdapter.ItemHolder>(ItemComparator()) {


    class ItemHolder(view: View) :
        RecyclerView.ViewHolder(view) { // каждый объект класса хранит ссылку на одну пазметку list_item
        private val binding = ListNameItemBinding.bind(view)


        fun setData(shopListNameItem: ShopListNameItem, listener: Listener) {
            binding.tvName.text = shopListNameItem.name
            binding.tvTime.text = shopListNameItem.time
            binding.imDelete.setOnClickListener {
                listener.deleteItem(shopListNameItem.id!!)
            }
            binding.imEdit.setOnClickListener {
                listener.updateItem(shopListNameItem)
            }
            itemView.setOnClickListener {
                listener.onClickItem(shopListNameItem)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_name_item, parent, false)
                )
            }
        }

    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListNameItem>() {
        override fun areItemsTheSame(
            oldItem: ShopListNameItem,
            newItem: ShopListNameItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopListNameItem,
            newItem: ShopListNameItem
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingListNameAdapter.ItemHolder {
        return ItemHolder.create(parent)
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }


    interface Listener {
        fun deleteItem(id: Int)
        fun updateItem(shopListItem: ShopListNameItem)
        fun onClickItem(shopListItem: ShopListNameItem )
    }
}