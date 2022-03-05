package com.example.shoppinglist.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import androidx.core.text.getSpans
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityNewNoteBinding
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.fragments.NoteFragment
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getNote()
    }

    private fun getNote() {
        val checkingNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (checkingNote != null) {
            note = checkingNote as NoteItem
            fillNote()
        }
    }

    private fun fillNote() {
        binding.edTitle.setText(note?.title)
        binding.edDescription.setText(note?.content)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {  // нажатия на вернюю панель
        when (item.itemId) {
            R.id.id_save -> {
                setMyResult()
            }
            android.R.id.home -> {
                finish()
            }
            R.id.id_bold -> {
                setBoldToSelectedText()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldToSelectedText() {
        val startPos = binding.edDescription.selectionStart
        val endPos = binding.edDescription.selectionEnd

        val styles = binding.edDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()){
            binding.edDescription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        binding.edDescription.text.setSpan(boldStyle, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.edDescription.text.trim()
        binding.edDescription.setSelection(startPos)

    }

    private fun setMyResult(){
        var editState = "new"
        val tempNote: NoteItem? = if (note == null){
            createNewNote()
        } else {
            editState = "update"
            updateNote()
        }
        val intent = Intent()
        intent.putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
        intent.putExtra(NoteFragment.EDIT_STATE_KEY, editState)

        setResult(Activity.RESULT_OK, intent)
        finish()

    }

    private fun updateNote(): NoteItem? {
        return note?.copy(title = binding.edTitle.text.toString(),
        content = binding.edDescription.text.toString())
    }

    private fun createNewNote(): NoteItem{
        return NoteItem(null, binding.edTitle.text.toString(), binding.edDescription.text.toString(), getCurrentTime(), "")
    }

    private fun getCurrentTime(): String{
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)

    }


}