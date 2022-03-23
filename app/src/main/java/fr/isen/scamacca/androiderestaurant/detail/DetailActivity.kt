package fr.isen.scamacca.androiderestaurant.detail

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import fr.isen.scamacca.androiderestaurant.BaseActivity
import fr.isen.scamacca.androiderestaurant.R
import fr.isen.scamacca.androiderestaurant.ViewPagerAdapter
import fr.isen.scamacca.androiderestaurant.cart.Cart
import fr.isen.scamacca.androiderestaurant.cart.CartItem
import fr.isen.scamacca.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.scamacca.androiderestaurant.model.Dish

private lateinit var binding: ActivityDetailBinding

class DetailActivity : BaseActivity() {

    var quantity: Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Title
        val dish = intent.getSerializableExtra("dish") as? Dish
        binding.dishTiltle.text = dish?.title

        binding.listIngredients.text = dish?.ingredients?.map { it.name_fr }?.joinToString(", ")

        val list = dish?.getAllPictures()?.toList()

        val adapter = ViewPagerAdapter(
            supportFragmentManager
        )

        val basePrice: Double = dish!!.getPrice()

        binding.price.text = basePrice.toString()




        binding.plusBtn.setOnClickListener {
            quantity++
            var price = quantity * basePrice
            binding.price.text = price.toString()
        }

        binding.minusBtn.setOnClickListener {
            if (quantity > 1) {
                quantity--
            }
            var price = quantity * basePrice
            binding.price.text = price.toString()
        }

        binding.addCart.setOnClickListener {
            addToCart(dish,quantity)
        }

        if (!list.isNullOrEmpty()){
            for (i in 1..list.size) {
                val fragment =
                    DetailFragment.newInstance(
                        list[i - 1]
                    )
                adapter.addFragment(fragment)
            }
        }

        binding.carroussel.adapter = adapter
    }

    private fun addToCart(dish : Dish, quantity : Int){
        val cart =
            Cart.getCart(this)
        cart.addItem(
            CartItem(
                dish,
                quantity
            )
        )
        cart.save(this)
        refreshMenu(cart)
        Snackbar.make(
            binding.root,getString(
                R.string.cart_validation), Snackbar.LENGTH_LONG).show()
    }


    private fun refreshMenu(cart: Cart){
        invalidateOptionsMenu()
    }
}