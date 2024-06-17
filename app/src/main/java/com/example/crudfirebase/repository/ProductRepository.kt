package com.example.crudfirebase.repository

import android.util.Log
import com.example.crudfirebase.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductRepository {
    private val database = FirebaseDatabase.getInstance().getReference("products")

    fun addProduct(product: Product, onResult: (Boolean) -> Unit) {
        product.id = database.push().key ?: ""
        Log.d("errrrror", "i am called")
        database.child(product.id).setValue(product).addOnCompleteListener {
            if(it.isSuccessful){
                onResult(it.isSuccessful)
            }
            else{
                Log.d("errrrror", it.exception.toString())
                onResult(false)
            }
        }
    }

    fun getProducts(onResult: (List<Product>) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<Product>()
                snapshot.children.forEach {
                    it.getValue(Product::class.java)?.let { product ->
                        products.add(product)
                    }
                }
                onResult(products)
            }

            override fun onCancelled(error: DatabaseError) {
                onResult(emptyList())
            }
        })
    }

    fun updateProduct(product: Product, onResult: (Boolean) -> Unit) {
        database.child(product.id).setValue(product).addOnCompleteListener {
            onResult(it.isSuccessful)
        }
    }

    fun deleteProduct(productId: String, onResult: (Boolean) -> Unit) {
        database.child(productId).removeValue().addOnCompleteListener {
            onResult(it.isSuccessful)
        }
    }
}
