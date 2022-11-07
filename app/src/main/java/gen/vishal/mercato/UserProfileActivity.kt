package gen.vishal.mercato

import android.app.Dialog
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import gen.vishal.mercato.databinding.ActivityProfileBinding
import gen.vishal.mercato.databinding.ActivityUserProfileBinding
import java.io.File

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserProfileBinding
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var databaseReference : DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var user : User
    private lateinit var dialog : Dialog
    private lateinit var uid : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
         uid = firebaseAuth.currentUser?.uid.toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        if(uid.isNotEmpty()){

            getUserData()
        }

    }

    private fun getUserData() {

        showProgressBar()

        databaseReference.child(uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                user = snapshot.getValue(User::class.java)!!
                binding.tvFullName.setText(user.firstName + " " + user.lastName)
                binding.tvEdu.setText(user.education)
                binding.tvAdrs.setText(user.address)
                binding.tvMob.setText(user.mobile)
                getUserProfile()
            }

            override fun onCancelled(error: DatabaseError) {

                hideProgressBar()
                Toast.makeText(this@UserProfileActivity,"Failed to get user profile data", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun getUserProfile() {

        storageReference= FirebaseStorage.getInstance().reference.child("Users/$uid.jpg")
        val localFile = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {

            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.profileImage.setImageBitmap(bitmap)
            hideProgressBar()

        }.addOnFailureListener{

            hideProgressBar()
            Toast.makeText(this,"Failed to retrieve image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressBar(){
        dialog = Dialog(this)
      //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private fun hideProgressBar() {
        dialog.dismiss()
    }
}