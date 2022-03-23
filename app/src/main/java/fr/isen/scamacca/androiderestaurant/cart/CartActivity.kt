package fr.isen.scamacca.androiderestaurant.cart

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.scamacca.androiderestaurant.BaseActivity
import fr.isen.scamacca.androiderestaurant.HomeActivity
import fr.isen.scamacca.androiderestaurant.detail.DetailFragment
import fr.isen.scamacca.androiderestaurant.R
import fr.isen.scamacca.androiderestaurant.databinding.ActivityCartBinding
import fr.isen.scamacca.androiderestaurant.login.LoginActivity
import fr.isen.scamacca.androiderestaurant.login.NetworkConstant
import org.json.JSONObject

private lateinit var binding: ActivityCartBinding

class CartActivity : BaseActivity(), CartCellInterface {

    private lateinit var cart: Cart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        cart = Cart.getCart(this)

        val fragment = CartItemsFragment(cart, this)
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()

        binding.orderButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, LoginActivity.REQUEST_CODE)
        }
    }

    override fun onDeleteItem(item: CartItem) {
        cart.items.remove(item)
        cart.save(this)
    }

    override fun onShowDetail(item: CartItem) {
        val fragment = DetailFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LoginActivity.REQUEST_CODE && resultCode == Activity.RESULT_FIRST_USER) {
            val sharedPreferences = getSharedPreferences(LoginActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val idUser = sharedPreferences.getInt(LoginActivity.ID_USER, -1)
            if(idUser != -1) {
                sendOrder(idUser)
            }
        }
    }

    private fun sendOrder(idUser: Int) {
        val message = cart.items.map { "${it.count}x ${it.dish.title}" }.joinToString("\n")

        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.PATH_ORDER

        val jsonData = JSONObject()
        jsonData.put(NetworkConstant.ID_SHOP, "1")
        jsonData.put(NetworkConstant.ID_USER, idUser)
        jsonData.put(NetworkConstant.MSG, message)

        var request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            { response ->
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Votre commande a bien été prise en compte")
                builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                    cart.clear()
                    cart.save(this)
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                builder.show()
            },
            { error ->
                error.message?.let {
                    Log.d("request", it)
                } ?: run {
                    Log.d("request", error.toString())
                    Log.d("request", String(error.networkResponse.data))
                }
            }
        )
        queue.add(request)
    }
}