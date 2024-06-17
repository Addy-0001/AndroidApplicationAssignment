package com.example.crudfirebase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.crudfirebase.model.Product
import com.example.crudfirebase.viewmodel.ProductViewModel

class AddProductActivity : AppCompatActivity() {

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val productNameInput: EditText = findViewById(R.id.productNameInput)
        val productDescriptionInput: EditText = findViewById(R.id.productDescriptionInput)
        val productPriceInput: EditText = findViewById(R.id.productPriceInput)
        val saveProductButton: Button = findViewById(R.id.saveProductButton)

        saveProductButton.setOnClickListener {
            val name = productNameInput.text.toString()
            val description = productDescriptionInput.text.toString()
            val price = productPriceInput.text.toString().toDoubleOrNull() ?: 0.0

            val newProduct = Product(name = name, description = description, price = price)
            productViewModel.addProduct(newProduct)

            finish() // Close the activity and go back to the previous one
        }
    }
}