@file:JvmName("DishActivityKt")

package fr.isen.scamacca.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.scamacca.androiderestaurant.detail.DetailActivity
import fr.isen.scamacca.androiderestaurant.databinding.ActivityDishBinding
import fr.isen.scamacca.androiderestaurant.model.DataResult
import fr.isen.scamacca.androiderestaurant.model.Dish
import org.json.JSONObject

private lateinit var binding: ActivityDishBinding

class DishActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        binding = ActivityDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Title
        binding.categoryTitle.text = intent.getStringExtra("category")




        //Recycler View
        loadData(intent.getStringExtra("category")?: "")



    }

    private fun loadData(category: String){
        val postUrl = "http://test.api.catering.bluecodegames.com/menu"
        val requestQueue = Volley.newRequestQueue(this)

        val postData = JSONObject()
        postData.put("id_shop", "1")

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, postUrl, postData,{
            val gson: DataResult = Gson().fromJson(it.toString(), DataResult::class.java)
            gson.data.firstOrNull { it.name == category }?.dishes?.let { dishes ->
                displayCategories(dishes)
            }?: run{
                Log.e("DishActivity", "Pas de categorie trouvée")
            }

        } ,{
            Log.e("DishActivity", it.toString())
            })

        requestQueue.add(jsonObjectRequest)

    }

    private fun displayCategories(dishes : List<Dish>) {
        binding.dishRecycler.layoutManager = LinearLayoutManager(this)
        binding.dishRecycler.adapter = MyAdapter(dishes){
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dish",it)
            startActivity(intent)
        }
    }
}