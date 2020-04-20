package suson.marco.noteable.ui

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import suson.marco.noteable.R
import suson.marco.noteable.data.Guitar
import java.util.*

class MainActivityViewModel(private val app: Application) : AndroidViewModel(app) {
    private val strings: MutableList<Int> = mutableListOf(
            R.raw.string_1_note_e,
            R.raw.string_2_note_b,
            R.raw.string_3_note_g,
            R.raw.string_4_note_d,
            R.raw.string_5_note_a,
            R.raw.string_6_note_e
    )

    private val noteNames = arrayOf("e", "B", "G", "D", "A", "E")

    private val guitar: Guitar = Guitar(strings)
    private var previousNote = 0
    private var currentNote = Random().nextInt(strings.size)

    init {
        setNextNote()
    }

    fun getNoteNames(): Array<String> = noteNames

    fun getCurrentNote(): Int = currentNote

    fun setNextNote() {
        previousNote = currentNote
        currentNote = Random().nextInt(strings.size)
    }

    fun setPreviousNote() {
        currentNote = previousNote
    }

    fun playNote(n: Int = currentNote) {
        val note = strings[n]
        val mediaPlayer: MediaPlayer? = MediaPlayer.create(app.applicationContext, note)
        mediaPlayer?.start() // no need to call prepare(); create() does that for you
    }

    fun changeStrings(strings: MutableList<Int>) {
        guitar.strings.clear()
        guitar.strings.addAll(strings)
    }
}