package com.example.shoppinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.entities.LibraryItem
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query ("SELECT * FROM NOTE_LIST")   // ссчитывание
    fun getAllNotes(): Flow<List<NoteItem>>     // при изменении в базе получаем поток корутины постоянно, после запуска функции


    @Query ("DELETE FROM NOTE_LIST WHERE id IS :id")   // удаление по id
    suspend fun deleteNote(id: Int)    // запускаем на корутине удаление

    @Insert  // запись
    suspend fun insertNote(note: NoteItem)     // чтобы запускать на корутине

    @Insert
    suspend fun insertListName(nameItem: ShopListNameItem) // добавить список

    @Insert
    suspend fun insertItem(shopListItem: ShopListItem)

    @Insert
    suspend fun insertLibraryItem(libraryItem: LibraryItem)


    @Query ("SELECT * FROM shopping_list_names")
    fun getAllShoppingLists(): Flow<List<ShopListNameItem>>

    @Query ("SELECT * FROM shopping_list_item WHERE lest_id LIKE :listId")
    fun getAllShopListItems(listId: Int): Flow<List<ShopListItem>>


    @Query ("SELECT * FROM library_item WHERE name LIKE :name")
    suspend fun getAllLibraryItems(name: String): List<LibraryItem>


    @Update  // запись
    suspend fun updateNote(note: NoteItem)

    @Update  // запись
    suspend fun updateShopList(shopListItem: ShopListNameItem)

    @Update
    suspend fun upDateLisItem(item: ShopListItem)

    @Update
    suspend fun upDateLibraryItem(item: LibraryItem)

    @Query ("DELETE FROM SHOPPING_LIST_NAMES WHERE id IS :id")   // удаление по id
    suspend fun deleteShopListName(id: Int)    // запускаем на корутине удаление

    @Query ("DELETE FROM library_item WHERE id IS :id")   // удаление по id
    suspend fun deleteLibraryItem(id: Int)    // запускаем на корутине удаление

    @Query ("DELETE FROM SHOPPING_LIST_ITEM WHERE id LIKE :id")   // удаление по id
    suspend fun deleteShopItemsByListId(id: Int)    // запускаем на корутине удаление





}