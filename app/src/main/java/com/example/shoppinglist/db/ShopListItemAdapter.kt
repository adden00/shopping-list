package com.example.shoppinglist.db

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ShopListItemBinding
import com.example.shoppinglist.entities.ShopListItem

class ShopListItemAdapter(private val listener: Listener) :
    ListAdapter<ShopListItem, ShopListItemAdapter.ItemHolder>(ItemComparator()) {

    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun setItemData(item: ShopListItem, listener: Listener) {

            val binding = ShopListItemBinding.bind(view)
            binding.tvName.text = item.name
            binding.tvInfo.visibility = infoVisibility(item)
            binding.tvInfo.text = item.item_info
            binding.checkBox.setOnClickListener {
                setFlag(binding)

            }
        }

        fun setLibraryData(item: ShopListItem, listener: Listener) {


        }

        private fun setFlag(binding: ShopListItemBinding) {
            binding.apply {
                if (checkBox.isChecked) {
                    tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    binding.tvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.passiveText))
                    binding.tvInfo.setTextColor(ContextCompat.getColor(binding.root.context, R.color.passiveText))
                }

                else {
                    tvName.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                    binding.tvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.activeText))
                    binding.tvInfo.setTextColor(ContextCompat.getColor(binding.root.context, R.color.activeText))
                }
            }
        }

        private fun infoVisibility(item: ShopListItem): Int {
            return if (item.item_info.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        companion object {
            fun createShopItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_list_item, parent, false)
                )
            }

            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_library_list_item, parent, false)
                )
            }


        }
    }

    class ItemComparator : DiffUtil.ItemCallback<ShopListItem>() {
        override fun areItemsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShopListItem,
            newItem: ShopListItem
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return if (viewType == 0) ItemHolder.createShopItem(parent)
        else ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (getItem(position).item_type == 0)
            holder.setItemData(getItem(position), listener)
        else
            holder.setLibraryData(getItem(position), listener)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).item_type
    }

    interface Listener {
        fun onClick()
    }


}