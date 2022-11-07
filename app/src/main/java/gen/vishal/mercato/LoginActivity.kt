package gen.vishal.mercato


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth;
import gen.vishal.mercato.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

//        val registered: TextView = findViewById(R.id.tv_ht_account)
//
//        registered.setOnClickListener {
//            val intent = Intent(this,RegisterActivity::class.java)
//            startActivity(intent) }

            binding.btnLogin.setOnClickListener{
                val email = binding.editTextTextEmailAddress3.text.toString()
                val pass = binding.editTextTextPassword.text.toString()
                if(email.isNotEmpty() && pass.isNotEmpty()){
                        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener{
                            if(it.isSuccessful){
                                val intents = Intent(this,ProfileActivity::class.java)
                                startActivity(intents)
                            }else{
                                Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }


                }else{
                    Toast.makeText(this,"Empty Fields Are Not Allowed !!", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
