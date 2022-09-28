package com.example.shoppinglist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopListBinding
import com.example.shoppinglist.db.MainViewModel
import com.example.shoppinglist.db.ShopListItemAdapter
import com.example.shoppinglist.dialogs.EditListItemDialog
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem
import com.example.shoppinglist.utils.ShareHelper

class ShopListActivity : AppCompatActivity(), ShopListItemAdapter.Listener {

    private lateinit var binding: ActivityShopListBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem
    private var edItem: EditText? = null
    private var adapter: ShopListItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        initRcView()
        listItemObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu.findItem(R.id.save_item)
        val newItem = menu.findItem(R.id.new_item)
        edItem = newItem.actionView.findViewById(R.id.edNewShopListItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_item ->
                addNewShopItem()
            R.id.delete_list -> {
                viewModel.deleteShopList(shopListNameItem?.id!!, true)
                finish()
            }
            R.id.clear_list -> {
                viewModel.deleteShopList(shopListNameItem?.id!!, false)
            }
            R.id.share_list -> {
                startActivity(
                    Intent.createChooser(
                        ShareHelper.shareShopList(
                            adapter?.currentList!!,
                            shopListNameItem?.name!!
                        ), "Share by"
                    )
                )
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun addNewShopItem() {
        if (edItem?.text.toString().isEmpty()) return
        val item =
            ShopListItem(null, edItem?.text.toString(), null, false, shopListNameItem?.id!!, 0)
        edItem?.setText("")
        viewModel.insertItem(item)

    }

    private fun listItemObserver() {
        viewModel.getAllItemsFromList(shopListNameItem?.id!!).observe(this) {
            adapter?.submitList(it)
            binding.tvIsEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun initRcView() {
        adapter = ShopListItemAdapter(this)
        binding.rcView.layoutManager = LinearLayoutManager(this)
        binding.rcView.adapter = adapter
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                saveItem.isVisible = true
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                saveItem.isVisible = false
                invalidateOptionsMenu()
                return true
            }

        }
    }

    private fun init() {
        shopListNameItem = intent.getSerializableExtra(SHOP_LIST_NAME) as ShopListNameItem
//        binding.tvIsEmpty.text = shopListNameItem?.name.toString()\

    }

    companion object {
        const val SHOP_LIST_NAME = "shop_list_name"
    }

    override fun onClick(shopListItem: ShopListItem, state: Int) {
        when (state) {
            ShopListItemAdapter.CHECKBOX -> viewModel.updateListItem(shopListItem)
            ShopListItemAdapter.EDIT -> editListItem(shopListItem)
        }


    }

    private fun editListItem(item: ShopListItem) {
        EditListItemDialog.showDialog(this, object : EditListItemDialog.Listener {
            override fun onClick(item: ShopListItem) {
                viewModel.updateListItem(item)
            }

        }, item)
    }
}