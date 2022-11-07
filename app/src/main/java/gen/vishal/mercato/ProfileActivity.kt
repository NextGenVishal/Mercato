package gen.vishal.mercato

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import gen.vishal.mercato.databinding.ActivityProfileBinding
import android.icu.util.Calendar
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Calendar.getInstance

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var databaseReference : DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri : Uri
    private lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.saveBtn.setOnClickListener {

            showProgressBar()
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val education = binding.etEdu.text.toString()
            val address = binding.etAdrs.text.toString()
            val mobile = binding.etMob.text.toString()

            val user = User(firstName,lastName,education,address,mobile)
            if (uid != null){

                databaseReference.child(uid).setValue(user).addOnCompleteListener{

                    if(it.isSuccessful){
                        uploadProfilePic()


                    }else{
                        hideProgressBar()
                        Toast.makeText(this,"Failed to update profile", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun uploadProfilePic() {

        imageUri = Uri.parse("android.resources://$packageName/${R.drawable.opp}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+firebaseAuth.currentUser?.uid+".jpg")
        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(this,"Profile successfully updated",Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(this,"Failed to upload image",Toast.LENGTH_SHORT).show()
        }


    }
    private fun showProgressBar(){
        dialog = Dialog(this)
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }
}

