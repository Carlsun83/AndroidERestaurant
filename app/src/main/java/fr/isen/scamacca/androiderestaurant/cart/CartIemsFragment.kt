package fr.isen.scamacca.androiderestaurant.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.scamacca.androiderestaurant.databinding.FragmentCartItemsBinding

class CartItemsFragment(private val cart: Cart, private val delegate: CartCellInterface) : Fragment(), CartCellInterface {

    lateinit var binding: FragmentCartItemsBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCartItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            reloadData(it)
        }
    }

    private fun reloadData(context: Context) {
        binding.cartItemRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.cartItemRecyclerView.adapter = CartAdapter(cart, context, this)
    }

    override fun onDeleteItem(item: CartItem) {
        context?.let {
            cart.items.remove(item)
            cart.save(it)
            reloadData(it)
        }
    }

    override fun onShowDetail(item: CartItem) {
        delegate.onShowDetail(item)
    }
}