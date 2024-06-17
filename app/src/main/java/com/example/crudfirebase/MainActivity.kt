package com.example.crudfirebase

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudfirebase.adapter.ProductAdapter
import com.example.crudfirebase.model.Product
import com.example.crudfirebase.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        productAdapter = ProductAdapter()

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = productAdapter

        productViewModel.products.observe(this, {products -> productAdapter.submitList(products)})

        productViewModel.fetchProducts()

        // Example add product button click
        findViewById<Button>(R.id.addProductButton).setOnClickListener {
            val newProduct =
                Product(name = "New Product", description = "Description", price = 9.99)
            productViewModel.addProduct(newProduct)
        }
    }
}
