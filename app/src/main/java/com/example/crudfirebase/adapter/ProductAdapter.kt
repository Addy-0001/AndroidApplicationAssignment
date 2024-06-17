package com.example.crudfirebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.crudfirebase.R
import com.example.crudfirebase.model.Product
import com.squareup.picasso.Picasso

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(ProductDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val productDescription: TextView = itemView.findViewById(R.id.productDescription)
        private val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)

        fun bind(product: Product) {
            productName.text = product.name
            productDescription.text = product.description
            productPrice.text = product.price.toString()
            Picasso.get().load(product.imageUrl).into(productImageView)
        }
    }
}

class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
