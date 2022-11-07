package gen.vishal.mercato

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import android.widget.Toast
import androidx.appcompat.resources.Compatibility.Api21Impl.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import com.google.firebase.auth.FirebaseAuth
import gen.vishal.mercato.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {


    private lateinit var binding:ActivityRegisterBinding
    private lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener{
            val email = binding.editTextTextEmailAddress2.text.toString()
            val pass = binding.editTextTextPassword2.text.toString()
            val confirmPass = binding.editTextTextPassword1.text.toString()
            if(email.isNotEmpty()&& pass.isNotEmpty()&& confirmPass.isNotEmpty()){
                if(pass == confirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener{
                        if(it.isSuccessful){
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(this,"password is not matching",Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this,"Empty Fields Are Not Allowed !!",Toast.LENGTH_SHORT).show()
            }
        }


    }
}