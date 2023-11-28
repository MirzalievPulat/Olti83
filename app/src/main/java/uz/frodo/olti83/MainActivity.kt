package uz.frodo.olti83

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.squareup.picasso.Picasso
import uz.frodo.olti83.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var back = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    /*override fun onBackPressed() {
        if (back){
            super.onBackPressed()
            return
        }

        back = true
        Toast.makeText(applicationContext, "Double click to exit", Toast.LENGTH_SHORT).show()
        Handler(mainLooper).postDelayed({
                                        back = false
        },2000)
    }*/
}