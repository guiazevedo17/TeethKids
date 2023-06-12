package com.kids.teeth.dentista.fragment

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.messaging.ktx.messaging
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.dao.AddressesDao
import com.kids.teeth.dentista.databinding.FragmentSignUpBinding
import com.kids.teeth.dentista.model.Address
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.concurrent.ExecutorService
import com.kids.teeth.dentista.fragment.CameraPreviewFragment


class SignUpFragment : Fragment(){

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!

    private lateinit var db: FirebaseFirestore
    private lateinit var functions: FirebaseFunctions
    private lateinit var auth: FirebaseAuth

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector

    private var imageCapture: ImageCapture? = null
    private lateinit var imgCaptureExecutor: ExecutorService

    private var capturedImageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding
            .inflate(
                inflater,
                container,
                false
            )

        return binding.root
        }

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance(Firebase.app)
        auth = FirebaseAuth.getInstance(Firebase.app)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = this.arguments
        val resume = args?.getString("resume")
        val imgUrl = args?.getString("imageUrl")

        if(imgUrl != null) {
            capturedImageUri = Uri.parse(imgUrl)
            binding.ivProfilePictureSignUp.setImageURI(capturedImageUri)
        }

        val btnBorder = GradientDrawable()
        btnBorder.cornerRadius = 300f

        binding.ivProfilePictureSignUp.background = btnBorder

        binding.ivProfilePictureSignUp.setOnClickListener {
            cameraProviderResult.launch(android.Manifest.permission.CAMERA)
        }

        binding.btnAddressSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_SignUpFragment_to_AddressesListSignUpFragment)
        }

        binding.btnResumeSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_SignUpFragment_to_ResumeFragment)
        }

        binding.btnConcludeSignUp.setOnClickListener {
            val Name = binding.etNameSignUp.text.toString()
            val Phone = binding.etPhoneSignUp.text.toString()
            val Email = binding.etEmailSignUp.text.toString()
            val Password = binding.etPasswordSignUp.text.toString()
            val PasswordConfirm = binding.etConfPasswordSignUp.text.toString()
            val Resume = resume ?: ""

            if (confirmPassword(Password, PasswordConfirm)) {
                if (fieldNotNull(Name, Phone, Email, Password, PasswordConfirm, Resume)) {
                    auth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener { register ->
                        if (register.isSuccessful) {
                            val snackbar = Snackbar.make(view, "Sucesso ao cadastrar usuário!", Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.BLUE)
                            snackbar.show()
                            val uid = auth.currentUser?.uid
                            if (uid != null) {
                                storeFcmToken(Name, Phone, Email, Password, Resume, uid)
                                AddressesDao.clearAll()
                                capturedImageUri?.let { it1 -> uploadImage(it1) }
                                findNavController().navigate(R.id.action_SignUpFragment_to_SignInFragment)
                            }
                            clearFields()
                        }
                    }
                } else {
                    val snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.show()
                }
            } else {
                val snackbar = Snackbar.make(view, "As senhas não correspondem!", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
        }


    }

    private fun abrirPreview() {
        findNavController().navigate(R.id.action_SignUpFragment_to_CameraPreviewFragment)
    }



    private val cameraProviderResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            abrirPreview()
        } else {
            Snackbar.make(binding.root, "Você não concedeu permissão", Snackbar.LENGTH_INDEFINITE).show()
        }
    }


    // Limpa os campos
    private fun clearFields(){
        binding.etNameSignUp.setText("")
        binding.etEmailSignUp.setText("")
        binding.etPhoneSignUp.setText("")
        binding.etPasswordSignUp.setText("")
        binding.etConfPasswordSignUp.setText("")
    }

    // Verifica compatibilidade das senhas
    private fun confirmPassword(Password: String, PasswordConfirm: String): Boolean {

        if(Password == PasswordConfirm) {
            return true
        }
        return false
    }

    // Verifica se existe algum campo nulo
    private fun fieldNotNull(Name: String, Phone: String, Email: String, Password: String, PasswordConfirm: String, Resume: String): Boolean {

        if(Name.isEmpty() || Phone.isEmpty() || Email.isEmpty() || Password.isEmpty() || PasswordConfirm.isEmpty() || Resume.isEmpty()){
            return false
        }
        return true
    }

    private fun registerAccount(Name: String, Phone: String, Email: String, Password: String, Resume: String, Addresses: List<Address>, Uid: String, Availability: Boolean,Fcmtoken: String) {

        functions = Firebase.functions("southamerica-east1")


        val dentist = hashMapOf(
            "name" to Name,
            "phone" to Phone,
            "email" to Email,
            "password" to Password,
            "resume" to Resume,
            "addresses" to Addresses,
            "availability" to Availability,
            "fcmToken" to Fcmtoken,
            "userId" to Uid
        )

        functions.getHttpsCallable("setUser")
            .call(dentist)
            .addOnSuccessListener { result ->
                val resposta :String? = result.data.toString()
                Log.d("setUserResult","Result : ${resposta}")
            }
    }

    private fun storeFcmToken(Name: String, Phone: String, Email: String, Password: String, Resume: String,Uid: String){
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            // guardar esse token.
            var fcmToken = task.result
            registerAccount(Name,Phone,Email,Password,Resume,AddressesDao.searchAll(), Uid,false,fcmToken)

        })
    }

    private fun uploadImage(imageUri: Uri) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        // Referência para o arquivo
        val imageRef: StorageReference = storageRef.child("${imageUri.lastPathSegment}")

        // Upload
        val uploadTask = imageRef.putFile(imageUri)

        // Verificação do progresso de Upload
        uploadTask.addOnProgressListener { taskSnapshot ->
            val progress = (100 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
        }.addOnSuccessListener {
            Log.i("ImageUpload", "Imagem Carregada com Sucesso")
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                val imageUrl = downloadUri.toString()
                ImageHelper.imageUrl = imageUrl
            }
        }.addOnFailureListener { exception ->
            Log.e("ImageUploadError", "Imagem não foi carregada corretamente: $exception")
        }
    }


}