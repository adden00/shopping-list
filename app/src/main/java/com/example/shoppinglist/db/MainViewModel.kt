package com.example.shoppinglist.db

import androidx.lifecycle.*
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShoppingListName
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

    val allListNames: LiveData<List<ShoppingListName>> = dao.getAllShoppingLists().asLiveData()

    fun insertListName(name: ShoppingListName) = viewModelScope.launch {
        dao.insertListName(name)
    }

    fun deleteShopList(id: Int) = viewModelScope.launch {   // используем корутины от viewModel
        dao.deleteShopListName(id)
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