package com.example.shoppinglist.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.core.text.getSpans
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityNewNoteBinding
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.fragments.NoteFragment
import com.example.shoppinglist.utils.HtmlManager
import com.example.shoppinglist.utils.MyTouchListener
import com.example.shoppinglist.utils.TimeManager
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
        init()
        onClickColorPicker()
    }


    private fun onClickColorPicker() {
        binding.imRed.setOnClickListener { setColor(R.color.cpRed) }
        binding.imOrange.setOnClickListener { setColor(R.color.cpOrange) }
        binding.imYellow.setOnClickListener { setColor(R.color.cpYellow) }
        binding.imGreen.setOnClickListener { setColor(R.color.cpGreen) }
        binding.imBlue.setOnClickListener { setColor(R.color.cpBlue) }
        binding.imPurple.setOnClickListener { setColor(R.color.cpPurple) }
    }

    private fun getNote() {
        val checkingNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (checkingNote != null) {
            note = checkingNote as NoteItem
            fillNote()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        binding.colorPicker.setOnTouchListener(MyTouchListener())
    }

    private fun fillNote() {
        binding.edTitle.setText(note?.title)
        binding.edDescription.setText(
            HtmlManager.getHtml(note!!.content).trim()
        ) // используем методы коныертирования spanned to html
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
            R.id.id_color -> {
                if (binding.colorPicker.visibility == View.GONE) {
                    openColorPicker()
                } else
                    closeColorPicker()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBoldToSelectedText() {
        val startPos = binding.edDescription.selectionStart
        val endPos = binding.edDescription.selectionEnd

        val styles = binding.edDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if (styles.isNotEmpty()) {
            binding.edDescription.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        binding.edDescription.text.setSpan(
            boldStyle,
            startPos,
            endPos,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.edDescription.text.trim()
        binding.edDescription.setSelection(startPos)

    }

    private fun setColor(colorId: Int) {
        val startPos = binding.edDescription.selectionStart
        val endPos = binding.edDescription.selectionEnd

        val styles =
            binding.edDescription.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)
        if (styles.isNotEmpty()) binding.edDescription.text.removeSpan(styles[0])

        binding.edDescription.text.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    this,
                    colorId
                )
            ), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

    }

    private fun setMyResult() {
        var editState = "new"
        val tempNote: NoteItem? = if (note == null) {
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
        return note?.copy(
            title = binding.edTitle.text.toString(),
            content = HtmlManager.toHtml(binding.edDescription.text)
        )
    }

    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            HtmlManager.toHtml(binding.edDescription.text),
            TimeManager.getCurrentTime(),
            ""
        )
    }



    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker() {
        val closeAnim = AnimationUtils.loadAnimation(this, R.anim.close_picker)
        closeAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
        binding.colorPicker.startAnimation(closeAnim)

    }


}