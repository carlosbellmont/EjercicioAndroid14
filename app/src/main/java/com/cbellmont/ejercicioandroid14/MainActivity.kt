package com.cbellmont.ejercicioandroid14

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbellmont.ejercicioandroid14.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter : PersonajesAdapter
    private lateinit var model :MainActivityViewModel

    enum class TipoPersonaje {
        TODOS, BUENOS, MALOS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createRecyclerView()
        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.pbLoading.visibility = View.VISIBLE
            when (checkedId) {
                binding.rbTodos.id -> cargarPersonajes(TipoPersonaje.TODOS)
                binding.rbBuenos.id -> cargarPersonajes(TipoPersonaje.BUENOS)
                binding.rbMalos.id -> cargarPersonajes(TipoPersonaje.MALOS)
            }
        }
        cargarPersonajes(TipoPersonaje.TODOS)
    }

    private fun cargarPersonajes(tipo: TipoPersonaje) {
        CoroutineScope(Dispatchers.IO).launch {
            val personajes = when(tipo) {
                TipoPersonaje.TODOS -> model.getPersonajes()
                TipoPersonaje.BUENOS -> model.getPersonajesBuenos()
                TipoPersonaje.MALOS -> model.getPersonajesMalos()
            }
            mostarPersonajes(personajes)
        }
    }

    private suspend fun mostarPersonajes(personajes: List<Personaje>) {
        withContext(Dispatchers.Main) {
            adapter.updatePersonajes(personajes)
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun createRecyclerView() {
        adapter = PersonajesAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }



}