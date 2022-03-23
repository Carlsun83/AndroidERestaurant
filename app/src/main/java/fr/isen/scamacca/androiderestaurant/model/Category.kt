package fr.isen.scamacca.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(
    @SerializedName("name_fr") val name: String,
    @SerializedName("items") val dishes: List<Dish>
    ) : Serializable