package gen.vishal.mercato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.nextButton)

        button.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        val buttons = findViewById<Button>(R.id.btn_register)

        buttons.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }


//        val butt = findViewById<Button>(R.id.btn_register)
//
//        butt.setOnClickListener {
//            startActivity(Intent(this,RegisterActivity::class.java))
//        }

    }
}