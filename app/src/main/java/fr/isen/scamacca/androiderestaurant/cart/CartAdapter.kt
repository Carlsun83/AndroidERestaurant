package fr.isen.scamacca.androiderestaurant.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.scamacca.androiderestaurant.R
import fr.isen.scamacca.androiderestaurant.databinding.CartCellBinding

interface CartCellInterface {
    fun onDeleteItem(item: CartItem)
    fun onShowDetail(item: CartItem)
}

class CartAdapter(private val cart: Cart,
                    private val context: Context,
                    private val delegate: CartCellInterface
): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(binding: CartCellBinding): RecyclerView.ViewHolder(binding.root) {
        private val itemTitle: TextView = binding.cartItemTitle
        private val itemPrice: TextView = binding.cartItemPrice
        private val itemQuantity: TextView = binding.cartItemQuantity
        private val itemImageView: ImageView = binding.cartItemImageView
        private val deleteButton: ImageView = binding.cartItemDelete
        val layout = binding.root

        fun bind(item: CartItem, context: Context, delegate: CartCellInterface) {
            itemTitle.text = item.dish.title
            itemPrice.text = item.dish.getFormatedPrice()
            itemQuantity.text = "${context.getString(R.string.quantity)} ${item.count.toString()}"
            Picasso.get()
                .load(item.dish.getFirstPicture())
                .placeholder(R.drawable.logo)
                .into(itemImageView)
            deleteButton.setOnClickListener {
                delegate.onDeleteItem(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            CartCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cart.items[position]
        holder.layout.setOnClickListener {
            delegate.onShowDetail(item)
        }
        holder.bind(item, context, delegate)
    }

    override fun getItemCount(): Int {
        return cart.items.count()
    }
}