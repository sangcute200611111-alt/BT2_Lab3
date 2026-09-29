package com.example.studentprofilemanager

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.studentprofilemanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentStudent = Student("2415053122237", "Võ Văn Sang", "24T2", "sang.vv@spkt.udn.vn", 3.5)

    private val editProfileLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                @Suppress("DEPRECATION")
                val updatedStudent = result.data?.getSerializableExtra("UPDATED_STUDENT") as? Student
                updatedStudent?.let {
                    currentStudent = it
                    bindData(currentStudent)
                    Toast.makeText(this, "Đã cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindData(currentStudent)

        binding.btnEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java).apply {
                putExtra("STUDENT", currentStudent)
            }
            editProfileLauncher.launch(intent)
        }
    }

    private fun bindData(student: Student) {
        binding.tvName.text = student.name
        binding.tvDetails.text = "MSSV: ${student.id} | Lớp: ${student.className}"
        binding.tvGpaBadge.text = "GPA: ${student.gpa}"
    }
}