package com.kids.teeth.dentista.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.common.util.concurrent.ListenableFuture
import com.kids.teeth.dentista.R
import com.kids.teeth.dentista.databinding.FragmentCameraPreviewBinding
import java.io.File
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraPreviewFragment : Fragment() {
    private var _binding: FragmentCameraPreviewBinding? = null
    private val binding: FragmentCameraPreviewBinding get() = _binding!!

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector
    private var imageCapture: ImageCapture? = null
    private lateinit var imgCaptureExecutor: ExecutorService

    private var capturedImageUri: Uri? = null

    interface OnImageSavedListener {
        fun onImageSaved(imageUri: Uri)
        fun onError(exception: ImageCaptureException)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        imgCaptureExecutor = Executors.newSingleThreadExecutor()

        startCamera()

        binding.btnTakePhoto.setOnClickListener {
            imageCapture?.let { capture ->
                takePicture(capture, object : OnImageSavedListener {
                    override fun onImageSaved(imageUri: Uri) {
                        binding.btnTakePhoto.post {
                            Toast.makeText(
                                binding.root.context,
                                "id imagem: $imageUri",
                                Toast.LENGTH_LONG
                            ).show()

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                blinkPreview()
                            }

                            Toast.makeText(
                                binding.root.context,
                                "Foto tirada com sucesso",
                                Toast.LENGTH_LONG
                            ).show()

                            binding.btnTakePhoto.visibility = View.GONE
                            binding.btnSavePhoto.visibility = View.VISIBLE
                            binding.cameraPreview.visibility = View.GONE
                            binding.capturedImage.visibility = View.VISIBLE
                            binding.btnTakeAnotherPicture.visibility = View.VISIBLE
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        // Tratar erro ao salvar a imagem
                    }
                })
            }
        }




        // Ao clicar no botão "Salvar Foto"
        binding.btnSavePhoto.setOnClickListener {
            capturedImageUri?.let { imageUri ->
               val bundle = Bundle()
               bundle.putString("imageUrl", imageUri.toString())
               findNavController().navigate(R.id.action_CameraPreviewFragment_to_SignUpFragment, bundle)
            }
        }

        binding.btnTakeAnotherPicture.setOnClickListener{
            binding.capturedImage.visibility = View.GONE
            binding.cameraPreview.visibility = View.VISIBLE
            binding.btnTakePhoto.visibility = View.VISIBLE
            binding.btnSavePhoto.visibility = View.GONE
            binding.btnTakeAnotherPicture.visibility = View.GONE
        }
    }
    private fun startCamera() {
        cameraProviderFuture.addListener({
            imageCapture = ImageCapture.Builder().build()

            val cameraProvider = cameraProviderFuture.get()

            // Modifique esta linha para selecionar a câmera frontal
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(viewLifecycleOwner, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e("CameraPreview", "Falha ao abrir a câmera")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }




    private fun takePicture(imageCapture: ImageCapture, listener: OnImageSavedListener) {
        val fileName = "${System.currentTimeMillis()}.jpeg"
        val file = File(requireContext().externalMediaDirs[0], fileName)
        val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

        imageCapture.takePicture(
            outputFileOptions,
            imgCaptureExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val imageUri = file.toUri()
                    Log.i("CameraPreview", "Imagem Salva: $imageUri")

                    requireActivity().runOnUiThread {
                        binding.capturedImage.setImageURI(imageUri)
                    }
                    capturedImageUri = imageUri
                    listener.onImageSaved(imageUri)
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao salvar foto",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e("CameraPreview", "Exceção ao gravar arquivo: $exception")

                    listener.onError(exception)
                }
            }
        )
    }






    @RequiresApi(Build.VERSION_CODES.M)
    private fun blinkPreview() {
        binding.root.postDelayed({
            binding.root.foreground = ColorDrawable(Color.WHITE)
            binding.root.postDelayed({
                binding.root.foreground = null
            }, 50)
        }, 100)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
