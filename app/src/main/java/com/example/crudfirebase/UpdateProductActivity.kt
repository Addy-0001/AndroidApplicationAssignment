package com.example.crudfirebase

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.crudfirebase.model.Product
import com.example.crudfirebase.viewmodel.ProductViewModel

class UpdateProductActivity : AppCompatActivity() {

    private val productViewModel: ProductViewModel by viewModels()
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        product = intent.getParcelableExtra("product") ?: Product()

        val productNameInput: EditText = findViewById(R.id.productNameInput)
        val productDescriptionInput: EditText = findViewById(R.id.productDescriptionInput)
        val productPriceInput: EditText = findViewById(R.id.productPriceInput)
        val saveProductButton: Button = findViewById(R.id.saveProductButton)

        productNameInput.setText(product.name)
        productDescriptionInput.setText(product.description)
        productPriceInput.setText(product.price.toString())

        saveProductButton.setOnClickListener {
            product.name = productNameInput.text.toString()
            product.description = productDescriptionInput.text.toString()
            product.price = productPriceInput.text.toString().toDoubleOrNull() ?: 0.0

            productViewModel.updateProduct(product)

            finish() // Close the activity and go back to the previous one
        }
    }
}
