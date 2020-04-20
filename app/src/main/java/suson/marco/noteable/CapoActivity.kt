package suson.marco.noteable

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Marco on 3/1/2016.
 */
class CapoActivity : AppCompatActivity() {
    private var currentChoice: RadioButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_capo)
        currentChoice = findViewById<View>(R.id.no_capo) as RadioButton
        currentChoice!!.toggle()
    }
}