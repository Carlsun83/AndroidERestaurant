package fr.isen.scamacca.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ingredient(
    @SerializedName("name_fr") val name_fr: String
) : Serializable {
}