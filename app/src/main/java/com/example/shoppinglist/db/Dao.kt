package com.example.shoppinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.entities.NoteItem
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query ("SELECT * FROM NOTE_LIST")   // ссчитывание
    fun getAllNotes(): Flow<List<NoteItem>>     // при изменении в базе получаем поток корутины постоянно, после запуска функции

    @Query ("DELETE FROM NOTE_LIST WHERE id IS :id")   // удаление по id
    suspend fun deleteNote(id: Int)    // запускаем на корутине удаление

    @Insert  // запись
    suspend fun insertNote(note: NoteItem)     // чтобы запускать на корутине

    @Update  // запись
    suspend fun updateNote(note: NoteItem)



}