package com.example.crudfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.crudfirebase.adapter.ProductAdapter
import com.example.crudfirebase.viewmodel.ProductViewModel
import com.google.android.material.snackbar.Snackbar

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

        productViewModel.products.observe(this, { products ->
            productAdapter.submitList(products)
        })

        productViewModel.fetchProducts()

        findViewById<Button>(R.id.addProductButton).setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        // Add swipe to delete functionality
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // We don't want to support move actions in this example
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val product = productAdapter.currentList[position]
                productViewModel.deleteProduct(product.id)
                Snackbar.make(recyclerView, "${product.name} deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        productViewModel.addProduct(product)
                    }.show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
