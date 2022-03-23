package fr.isen.scamacca.androiderestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.scamacca.androiderestaurant.databinding.DishTemplateBinding
import fr.isen.scamacca.androiderestaurant.model.Dish

class MyAdapter(private val categories: List<Dish>, private val categoriesClickListener: (Dish) -> Unit): RecyclerView.Adapter<MyAdapter.CategoryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val itemBinding = DishTemplateBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.title.text = categories[position].title
        holder.price.text = categories[position].getFormatedPrice()
        if(!categories[position].getFirstPicture().isNullOrEmpty()) {
            Picasso.get().load(categories[position].getFirstPicture()).placeholder(R.drawable.wait).error(R.drawable.error).fit().into(holder.image)
        }else{
            Picasso.get().load(categories[position].getFirstPicture()).placeholder(R.drawable.error).error(R.drawable.error).fit().into((holder.image))
        }

        holder.layout.setOnClickListener {
            categoriesClickListener.invoke(categories[position])
        }
    }

    override fun getItemCount(): Int = categories.size

    class CategoryHolder(binding: DishTemplateBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.dishName
        val price = binding.dishPrice
        val image = binding.dishImage
        val layout = binding.root
    }
}