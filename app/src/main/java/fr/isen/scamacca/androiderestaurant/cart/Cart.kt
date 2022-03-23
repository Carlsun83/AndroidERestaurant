package fr.isen.scamacca.androiderestaurant.cart

import android.content.Context
import com.google.gson.GsonBuilder
import fr.isen.scamacca.androiderestaurant.model.Dish
import java.io.File
import java.io.Serializable

class Cart (val items: MutableList<CartItem>) : Serializable {
    var quantity: Int = 0
        get() {
            return if(items.count() > 0) {
                items
                    .map { it.count }
                    .reduce { acc, i -> acc + i }
            } else {
                0
            }
        }

    fun addItem(item: CartItem) {
        val existingItem = items.firstOrNull {
            it.dish.title == item.dish.title
        }
        existingItem?.let {
            existingItem.count += item.count
        } ?: run {
            items.add(item)
        }
    }

    fun clear() {
        items.clear()
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
        jsonFile.writeText(GsonBuilder().create().toJson(this))
        updateCounter(context)
    }

    private fun updateCounter(context: Context) {
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, quantity)
        editor.apply()
    }

    companion object {
        fun getCart(context: Context): Cart {
            val jsonFile = File(context.cacheDir.absolutePath + CART_FILE)
            return if(jsonFile.exists()) {
                val json = jsonFile.readText()
                GsonBuilder().create().fromJson(json, Cart::class.java)
            } else {
                Cart(mutableListOf())
            }
        }

        const val CART_FILE = "Cart.json"
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }

}

class CartItem(val dish: Dish, var count: Int): Serializable {}