package com.stackmobile.irriga.ui.inicio

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.stackmobile.irriga.R
import com.stackmobile.irriga.databinding.FragmentInicioBinding

class InicioFragment : Fragment() {

    val database = FirebaseDatabase.getInstance()
    private val CHANNEL_ID = "canal_id"
    private val idnotifica = 101
    private lateinit var binding: FragmentInicioBinding


    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentInicioBinding.inflate(inflater, container, false)

        //nesta verificação o tanque deve estar vazio e chegar notfy para o user
        //logo os sensores estão em false (TANQUE BAIXO)
        val myRef5 = database.getReference("IrrigadorAuto/note")
        val getnote5 = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var texto = StringBuilder()
                val not = dataSnapshot.getValue()
                texto.append(not)
                if (not == true) {
                    binding.imageView2.setImageResource(R.drawable.tanb)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        var stats5 = myRef5.addValueEventListener(getnote5).toString()

        //nesta verificação o sensor de tanque medio,baixo estão em true e o alto em falso
        //Logo TANQUE MEDIO
        val myRef6 = database.getReference("IrrigadorAuto/neganote")
        val getnote6 = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var texto = StringBuilder()
               // createNotificationChannel()
                val not = dataSnapshot.getValue()
                texto.append(not)
                if (not == true) {
                    binding.imageView2.setImageResource(R.drawable.tan)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        var stats6 = myRef6.addValueEventListener(getnote6).toString()

        // Aqui o todos os sensores estão em true e a váriavel NOTE é false
        // Logo Tanque CHEIO
        val myRef8 = database.getReference("IrrigadorAuto/note2")
        val getnote = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var texto = StringBuilder()
                // createNotificationChannel()
                val not = dataSnapshot.getValue()
                texto.append(not)
                if (not == true) {
                    binding.imageView2.setImageResource(R.drawable.tanc)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        var stats3 = myRef6.addValueEventListener(getnote6).toString()

        return binding.root


    }
}
