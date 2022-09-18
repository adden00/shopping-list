package com.example.shoppinglist.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.activities.MainActivity
import com.example.shoppinglist.activities.MainApp
import com.example.shoppinglist.activities.ShopListActivity
import com.example.shoppinglist.databinding.FragmentShopListNamesBinding
import com.example.shoppinglist.db.MainViewModel
import com.example.shoppinglist.db.ShoppingListNameAdapter
import com.example.shoppinglist.dialogs.DeleteDialog
import com.example.shoppinglist.dialogs.NewListDialog
import com.example.shoppinglist.entities.ShopListNameItem
import com.example.shoppinglist.utils.TimeManager


class ShopListNamesFragment : BaseFragment(), ShoppingListNameAdapter.Listener {

    private lateinit var binding: FragmentShopListNamesBinding
    private lateinit var adapter: ShoppingListNameAdapter

    private val mainViewModel : MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }


        override fun onClickNew() {
            NewListDialog.showDialog(activity as MainActivity, object : NewListDialog.Listener {
                override fun onClick(name: String) {
                    val shopListName = ShopListNameItem(null, name, TimeManager.getCurrentTime(), 0, 0, "")
                    mainViewModel.insertListName(shopListName)


                }

            }, "")
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = FragmentShopListNamesBinding.inflate(inflater)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            initRcView()
            observer()
        }

        private fun observer() {
            mainViewModel.allListNamesItem.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

        }

        private fun initRcView() {
            binding.rcView.layoutManager = LinearLayoutManager(activity)
            adapter = ShoppingListNameAdapter(this)
            binding.rcView.adapter = adapter
        }

        companion object {

        @JvmStatic
        fun newInstance() = ShopListNamesFragment()
    }

    override fun deleteItem(id: Int) {
        DeleteDialog.deleteItem(context as AppCompatActivity, object  : DeleteDialog.Listener {
            override fun onClick() {
                mainViewModel.deleteShopList(id, true)
            }

        })
    }

    override fun updateItem(shopListItem: ShopListNameItem) {
        NewListDialog.showDialog(activity as MainActivity, object : NewListDialog.Listener {
            override fun onClick(name: String) {
                mainViewModel.updateShopList(shopListItem.copy(name=name))
    }}, shopListItem.name)}

    override fun onClickItem(shopListItem: ShopListNameItem) {
        val i = Intent(activity,ShopListActivity::class.java).apply {
            putExtra(ShopListActivity.SHOP_LIST_NAME, shopListItem)
        }
        startActivity(i)

    }
}


