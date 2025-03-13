package com.example.doyouloveme

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.res.Configuration
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private lateinit var editTextProduct: EditText
    private lateinit var btnAdd: Button
    private val shoppingList = mutableListOf("Хлеб", "Молоко", "Яйца")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale("ru") // Устанавливаем русский язык
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        editTextProduct = findViewById(R.id.editTextProduct)
        btnAdd = findViewById(R.id.btnAdd)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(shoppingList)
        recyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            val product = editTextProduct.text.toString().trim()
            if (product.isNotEmpty()) {
                shoppingList.add(product)
                adapter.notifyItemInserted(shoppingList.size - 1)
                editTextProduct.text.clear() // Очищаем поле ввода
            }
        }
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}