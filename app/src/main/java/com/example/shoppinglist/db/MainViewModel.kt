package com.example.shoppinglist.db

import androidx.lifecycle.*
import com.example.shoppinglist.entities.LibraryItem
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(database: MainDataBase): ViewModel() {
    private val dao = database.getDao()
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()

    val allListNamesItem: LiveData<List<ShopListNameItem>> = dao.getAllShoppingLists().asLiveData()

    val libraryItems = MutableLiveData<List<LibraryItem>>()

    fun getAllItemsFromList(listId: Int): LiveData<List<ShopListItem>>{
        return dao.getAllShopListItems(listId).asLiveData()
    }

    fun getAllLibraryItems(name: String) = viewModelScope.launch {
        libraryItems.postValue(dao.getAllLibraryItems(name))

    }

    fun insertNote(note: NoteItem) = viewModelScope.launch {   // используем корутины от viewModel
        dao.insertNote(note)
    }

    fun updateNote(note: NoteItem) = viewModelScope.launch {   // используем корутины от viewModel
        dao.updateNote(note)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {   // используем корутины от viewModel
        dao.deleteNote(id)
    }

    fun deleteLibraryItem(id: Int) = viewModelScope.launch {   // используем корутины от viewModel
        dao.deleteLibraryItem(id)
    }


    fun insertListName(nameItem: ShopListNameItem) = viewModelScope.launch {
        dao.insertListName(nameItem)
    }

    fun insertItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.insertItem(shopListItem)
        if (!isLibraryItemExists(shopListItem.name))
            dao.insertLibraryItem(LibraryItem(null, shopListItem.name))
    }


    fun deleteShopList(id: Int, deleteListFlag: Boolean) = viewModelScope.launch {   // используем корутины от viewModel
        if (deleteListFlag)
            dao.deleteShopListName(id)
        dao.deleteShopItemsByListId(id)
    }

    fun updateShopList(shopList: ShopListNameItem) = viewModelScope.launch {
        dao.updateShopList(shopList)
    }

    fun updateListItem(shopListItem: ShopListItem) = viewModelScope.launch {
        dao.upDateLisItem(shopListItem)
    }

    fun updateLibraryItem(item: LibraryItem) = viewModelScope.launch {
        dao.upDateLibraryItem(item)
    }

    private suspend fun isLibraryItemExists(name: String):Boolean {
        return dao.getAllLibraryItems(name).isNotEmpty()
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