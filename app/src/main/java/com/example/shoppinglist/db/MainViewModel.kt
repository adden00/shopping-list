package com.example.shoppinglist.db

import androidx.lifecycle.*
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(database: MainDataBase): ViewModel() {
    private val dao = database.getDao()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()

    fun insertNote(note: NoteItem) = viewModelScope.launch {   // используем корутины от viewModel
        dao.insertNote(note)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {   // используем корутины от viewModel
        dao.updateNote(note)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {   // используем корутины от viewModel
        dao.deleteNote(id)
    }

    val allListNamesItem: LiveData<List<ShopListNameItem>> = dao.getAllShoppingLists().asLiveData()

    fun insertListName(nameItem: ShopListNameItem) = viewModelScope.launch {
        dao.insertListName(nameItem)
    }

    fun insertItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.insertItem(shopListItem)
    }

    fun deleteShopList(id: Int) = viewModelScope.launch {   // используем корутины от viewModel
        dao.deleteShopListName(id)
    }

    fun updateShopList(shopListItem: ShopListNameItem) = viewModelScope.launch {
        dao.updateShopList(shopListItem)
    }

    class MainViewModelFactory(private val database: MainDataBase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress ("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}