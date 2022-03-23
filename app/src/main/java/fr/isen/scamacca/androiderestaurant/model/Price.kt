package fr.isen.scamacca.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Price(
    @SerializedName("id") val id: String,
    @SerializedName("price") val price: String
):Serializable {
}