package com.example.contest

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.seller_ui_enroll_product.*
import kotlinx.android.synthetic.main.seller_ui_enroll_product.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class sellerUIEnrollProduct : Fragment() {
    private val mStorageRef = FirebaseStorage.getInstance()
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var imageUrl: Uri? = null

    val REQUEST_IMAGE_CAPTURE = 2
    lateinit var currentPhotoPath: String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.seller_ui_enroll_product, container, false)
        val data = database.getReference("productTodayDB")
        val imageData = mStorageRef.getReference("productImageDB")
        val dataMessage = database.getReference("userDB")
        // 사진 변경 버튼
        view.buttonChangeImage.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            startActivityForResult(Intent.createChooser(intent, "사용할 애플리케이션"), 1)
        }
        view.checkCtgrComplete.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                view.checkCtgrRaw.isChecked = false
            } else {
                view.checkCtgrRaw.isChecked = true
            }
        }
        view.checkCtgrRaw.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                view.checkCtgrComplete.isChecked = false
            } else {
                view.checkCtgrComplete.isChecked = true
            }
        }

        view.camerabutton.setOnClickListener {
            settingPermission(view.context)
            startCapture(view.context)
        }


        view.buttonEnroll.setOnClickListener {
            if (!view.checkCtgrComplete.isChecked && !view.checkCtgrRaw.isChecked) {
                view.textAlert.isVisible = true
                view.textAlert.setText("※ 제품 카테고리를 골라주세요!")
            } else if (view.textProductTitle.text.isEmpty() || view.textPrice.text.isEmpty() || view.textQuan.text.isEmpty() || view.textServing.text.isEmpty()) {
                view.textAlert.isVisible = true
                view.textAlert.setText("※ 제대로 입력해야 합니다!")
            } else if (imageUrl == null) {
                view.textAlert.isVisible = true
                view.textAlert.setText("※ 사진을 등록해주세요!")
            } else {
                var title = view.textProductTitle.text.toString()
                var price = Integer.parseInt(view.textPrice.text.toString())
                var serve = Integer.parseInt(view.textServing.text.toString())
                var quan = Integer.parseInt(view.textQuan.text.toString())
                var newProduct: productElement = productElement()
                var productId = SimpleDateFormat("yyyyMMdd").format(Date()) + userInfo.id + title
                var imageTitle = productId + ".png"
                imageData.child(imageTitle).putFile(imageUrl!!)

                if (view.checkCtgrComplete.isChecked) {
                    val dataMessage = database.getReference("userDB")
                    newProduct.setInfo(title, price, serve, productId, quan, "완제품", userInfo.timeClose)
                    dataMessage.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            for (uid in p0.children) {
                                if (uid.child("관심시장").value.toString() == userInfo.marketTitle) {
                                    if (uid.child("ctgr").child("완제품").value == true) {
                                        val title_m = "새로운 상품이 등록되었습니다"
                                        val body = "제품명 :" + title + " " + "가격 :" + price + " " + "마감시간 :" + userInfo.timeClose + " "
                                        val regToken = uid.child("token").value.toString()
                                        MessagePush().sendNotification(regToken, title_m, body)
                                    }
                                }
                            }
                        }
                    })
                } else {
                    newProduct.setInfo(title, price, serve, productId, quan, userInfo.ctgrForSeller, userInfo.timeClose)
                    dataMessage.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            for (uid in p0.children) {
                                if (uid.child("관심시장").value.toString() == userInfo.marketTitle) {
                                    if (uid.child("ctgr").child(userInfo.ctgrForSeller).value == true) {
                                        val regToken = uid.child("token").value.toString()
                                        val title_m = "새로운 상품이 등록되었습니다"
                                        val body = "제품명 :" + title + " " + "가격 :" + price + " " + "마감시간 :" + userInfo.timeClose + " "
                                        MessagePush().sendNotification(regToken, title_m, body)
                                    }
                                }
                            }
                        }
                    })
                }
                data.child(productId).setValue(newProduct.toMap())
                (activity as sellerUIMain).setSellerFrag(11)
            }
        }
        view.buttonCancel.setOnClickListener {
            (activity as sellerUIMain).setSellerFrag(11)
        }

        return view
    }

    fun settingPermission(context: Context) {
        var permis = object : PermissionListener {
            //            어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
            override fun onPermissionGranted() {
                Toast.makeText(context, "권한 허가", Toast.LENGTH_SHORT)
                        .show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(context, "권한 거부", Toast.LENGTH_SHORT)
                        .show()
                ActivityCompat.finishAffinity(requireActivity()) // 권한 거부시 앱 종료
            }
        }

        TedPermission.with(context)
                .setPermissionListener(permis)
                .setRationaleMessage("카메라 사진 권한 필요")
                .setDeniedMessage("카메라 권한 요청 거부")
                .setPermissions(
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA)
                .check()
    }

    fun startCapture(context: Context) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(context.packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile(context)
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            context,
                            "com.example.contest.fileprovider",
                            photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
                val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
                lateinit var exif: ExifInterface

                try {
                    exif = ExifInterface(currentPhotoPath)
                    var exifOrientation = 0
                    var exifDegree = 0

                    if (exif != null) {
                        exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_NORMAL)
                        exifDegree = exifOrientationToDegress(exifOrientation)
                    }
                    val image_bit=rotate(bitmap,exifDegree)
                    imageProduct.setImageBitmap(image_bit)
                    imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP)
                    imageUrl=getImageUri(requireContext(),image_bit)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                imageUrl = data?.data
                imageProduct.setImageURI(imageUrl)
                imageProduct.setScaleType(ImageView.ScaleType.CENTER_CROP)
            }
        }
    }

    private fun exifOrientationToDegress(exifOrientation: Int): Int {
        when (exifOrientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                Log.d("rotate", "rotate90")
                return 90
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                Log.d("rotate", "rotate180")
                return 180
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                Log.d("rotate", "rotate270")
                return 270
            }
            else -> {
                Log.d("rotate", "rotate0")
                return 0
            }

        }
    }

    private fun rotate(bitmap: Bitmap, degree: Int): Bitmap {
        Log.d("rotate", "init rotate")
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }


    private fun resize(
            context: Context,
            uri: Uri,
            resize: Int
    ): Bitmap? {
        var resizeBitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        try {
            BitmapFactory.decodeStream(
                    context.contentResolver.openInputStream(uri),
                    null,
                    options
            ) // 1번
            var width = options.outWidth
            var height = options.outHeight
            var samplesize = 1
            while (true) { //2번
                if (width / 2 < resize || height / 2 < resize) break
                width /= 2
                height /= 2
                samplesize *= 2
            }
            options.inSampleSize = samplesize
            val bitmap = BitmapFactory.decodeStream(
                    context.contentResolver.openInputStream(uri),
                    null,
                    options
            ) //3번
            resizeBitmap = bitmap
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return resizeBitmap
    }

}