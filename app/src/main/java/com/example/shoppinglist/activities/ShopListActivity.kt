package com.example.shoppinglist.activities

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
import com.example.shoppinglist.db.ShoppingListNameAdapter
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem

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
        if (item.itemId == R.id.save_item)
            addNewShopItem()
        return super.onOptionsItemSelected(item)
    }

    private fun addNewShopItem() {
        if (edItem?.text.toString().isEmpty()) return
        val item = ShopListItem(null, edItem?.text.toString(), null, false, shopListNameItem?.id!!, 0)
        edItem?.setText("")
        viewModel.insertItem(item)

    }

    private fun listItemObserver() {
        viewModel.getAllItemsFromList(shopListNameItem?.id!!).observe(this) {
            adapter?.submitList(it)
            binding.tvIsEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun initRcView(){
        adapter = ShopListItemAdapter(this)
        binding.rcView.layoutManager = LinearLayoutManager(this )
        binding.rcView.adapter = adapter
    }

    private fun expandActionView() : MenuItem.OnActionExpandListener{
        return object : MenuItem.OnActionExpandListener{
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

    private fun init(){
        shopListNameItem = intent.getSerializableExtra(SHOP_LIST_NAME) as ShopListNameItem
//        binding.tvIsEmpty.text = shopListNameItem?.name.toString()\

    }

    companion object {
        const val SHOP_LIST_NAME = "shop_list_name"
    }

    override fun onClick(shopListItem: ShopListItem) {
        viewModel.updateListItem(shopListItem)
    }
}