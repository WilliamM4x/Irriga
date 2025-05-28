package com.stackmobile.irriga.ui.alface

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.stackmobile.irriga.databinding.FragmentAlfaceBinding

class AlfaceFragment : Fragment() {

    val database = FirebaseDatabase.getInstance()
    var pieChart : PieChart? = null

    private lateinit var binding: FragmentAlfaceBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlfaceBinding.inflate(inflater, container, false)

        //Aqui pegamos o que está sendo inflado no
        // localizamos no layout o Pie que vai ser modificado
        pieChart = binding.piechart5

        //aqui iniciamos a escuta no banco de dados
        // com isso verificamos o valor a ser enviado apk
        getStats()


        pieChart?.description?.text = ""
        //hollow pie chart
        pieChart?.setTouchEnabled(true)
        pieChart?.setDrawEntryLabels(false)
        pieChart?.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart?.setUsePercentValues(true)
        pieChart?.isRotationEnabled = true
        pieChart?.setDrawEntryLabels(false)
        pieChart?.legend?.orientation = Legend.LegendOrientation.VERTICAL
        pieChart?.legend?.isWordWrapEnabled = true


        return binding.root

    }
    private fun getStats() {

        // pega a umidade no firebase e gera dados para o gráfico
        val myRef3 = database.getReference("IrrigadorAuto/mediaalface")
        val getevenet = object : ValueEventListener {
            override fun onDataChange(dataSnapshot1: DataSnapshot) {
                val um1 = dataSnapshot1.getValue().toString().toFloat()
                val um2 = ( 100 - um1)

                val piechartentry = ArrayList<PieEntry>()
                piechartentry.add(PieEntry(um1,"Umidade"))
                piechartentry.add(PieEntry(um2, "Seco"))
                val piedataset = PieDataSet(piechartentry, "")
                val data= PieData(piedataset)

                val colors: ArrayList<Int> = ArrayList()
                colors.add(Color.parseColor("#4DD0E1"))
                colors.add(Color.GRAY)
                pieChart!!.data=data

                //criar furo no centro
                pieChart!!.holeRadius = 65f
                pieChart!!.transparentCircleRadius = 70f
                pieChart!!.isDrawHoleEnabled = true
                pieChart!!.setHoleColor(Color.parseColor("#8EDA91"))

                // configuração para porcentagem
                data.setValueFormatter(PercentFormatter())
                piedataset.sliceSpace = 3f
                piedataset.colors = colors
                pieChart!!.data = data
                data.setValueTextSize(15f)
                pieChart!!.setExtraOffsets(5f, 10f, 5f, 5f)
                pieChart!!.animateY(1400, Easing.EaseInOutQuad)
                pieChart!!.setDrawCenterText(true);
                pieChart!!.centerText = "Solo"

                pieChart!!.invalidate()

            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        var st = myRef3.addValueEventListener(getevenet).toString()
    }

    }