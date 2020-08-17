package com.cbellmont.ejercicioandroid14

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay


class MainActivityViewModel  : ViewModel() {

    private var personajes = mutableListOf<Personaje>()

    suspend fun getPersonajes(): List<Personaje> {
        delay(3000)
        if (personajes.isEmpty()) {
            personajes = loadData()
        }
        return personajes
    }

    suspend fun getPersonajesBuenos(): List<Personaje> {
        delay(3000)
        if (personajes.isEmpty()) {
            personajes = loadData()
        }
        return personajes.filter { it.esBueno }.apply { shuffled() }
    }

    suspend fun getPersonajesMalos(): List<Personaje> {
        delay(3000)
        if (personajes.isEmpty()) {
            personajes = loadData()
        }
        return personajes.filter { !it.esBueno }.apply { shuffled() }
    }

    private fun loadData() : MutableList<Personaje>{
        val personaje1 = Personaje("Aragorn", "Humano", R.mipmap.aragorn, true)
        val personaje2 = Personaje("Gandalf", "Mago", R.mipmap.gandalf, true)
        val personaje3 = Personaje("Boromir", "Humano", R.mipmap.boromir, true)
        val personaje4 = Personaje("Legolas", "Elfo", R.mipmap.legolas, true)
        val personaje5 = Personaje("Orco Feo", "Orco", R.mipmap.orco, false)
        val personaje6 = Personaje("Smagu", "Dragon", R.mipmap.smagu, false)

        return mutableListOf(personaje1,personaje2,personaje3,personaje4,personaje5,personaje6).apply { shuffle() }
    }


}