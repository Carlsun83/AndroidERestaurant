package fr.isen.scamacca.androiderestaurant.login

import java.io.Serializable

class Register(val data: User) {}

class User(val id: Int): Serializable {}