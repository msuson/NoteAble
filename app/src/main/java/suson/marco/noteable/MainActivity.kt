package suson.marco.noteable

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import suson.marco.noteable.databinding.ActivityMainBinding
import suson.marco.noteable.ui.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private var readyForResponse = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val viewModel: MainActivityViewModel = ViewModelProviders.of(this)
                .get(MainActivityViewModel::class.java)

        val noteNames = viewModel.getNoteNames()

        // set the proper note for each string
        val stringButtons = arrayOf(binding.string1, binding.string2,
                binding.string3, binding.string4, binding.string5, binding.string6)
        for (i in noteNames.indices) {
            stringButtons[i].setOnClickListener {
                if (readyForResponse && i == viewModel.getCurrentNote()) {
                    val toast = Toast.makeText(this@MainActivity, noteNames[i], Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -190)
                    toast.show()
                    binding.centerImage.setImageResource(R.drawable.correct_response2)
                } else if (readyForResponse) {
                    binding.centerImage.setImageResource(R.drawable.incorrect_response2)
                }
                viewModel.playNote(i)
            }
        }

        // set current note to previous note
        binding.prevButton.setOnClickListener {
            readyForResponse = false
            binding.centerImage.setImageResource(R.drawable.sound_hole)
            viewModel.setPreviousNote()
        }

        // play the current note
        binding.playButton.setOnClickListener {
            readyForResponse = true
            binding.centerImage.setImageResource(R.drawable.active2)
            viewModel.playNote()
        }

        // set current note to a new note
        binding.nextButton.setOnClickListener {
            readyForResponse = false
            binding.centerImage.setImageResource(R.drawable.sound_hole)
            viewModel.setNextNote()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.testmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.capo -> {
                val capoIntent = Intent(this@MainActivity, CapoActivity::class.java)
                startActivity(capoIntent)
                return true
            }
            R.id.headstock -> {
                val headIntent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(headIntent)
                return true
            }
        }
        return true
    }
}