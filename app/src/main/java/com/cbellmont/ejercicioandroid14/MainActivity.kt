package com.cbellmont.ejercicioandroid14

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var adapter : PersonajesAdapter
    private lateinit var model :MainActivityViewModel

    enum class TipoPersonaje {
        TODOS, BUENOS, MALOS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createRecyclerView()
        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            pbLoading.visibility = View.VISIBLE
            when (checkedId) {
                rbTodos.id -> cargarPersonajes(TipoPersonaje.TODOS)
                rbBuenos.id -> cargarPersonajes(TipoPersonaje.BUENOS)
                rbMalos.id -> cargarPersonajes(TipoPersonaje.MALOS)
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
            pbLoading.visibility = View.GONE
        }
    }

    private fun createRecyclerView() {
        adapter = PersonajesAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }



}