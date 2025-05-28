package com.stackmobile.irriga

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.stackmobile.irriga.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val CHANNEL_ID = "canal_id"
    private val idnotifica = 101
    private val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_inicio, R.id.nav_couve, R.id.nav_alface, R.id.nav_coentro, R.id.nav_cebolinha, R.id.nav_meteorologia
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //gerando notificação a partir do banco de dados
        val myRef = database.getReference("IrrigadorAuto/note")
        val getnote = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var texto = StringBuilder()
                createNotificationChannel()
                val not = dataSnapshot.getValue()
                texto.append(not)
                if (not == true) {
                    notifyUser()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        var stats3 = myRef.addValueEventListener(getnote).toString()

        val myRef33 = database.getReference("IrrigadorAuto/notev")
        val getnote33 = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var texto = StringBuilder()
                createNotificationChannel()
                val not = dataSnapshot.getValue()
                texto.append(not)
                if (not == true) {
                    notifyUser2()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        var stats33 = myRef33.addValueEventListener(getnote).toString()



    }

    // criando canal para notificação
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notificacao"
            val descriptionText = "notificaco descricao"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    //criando notificação resevatorio baixo
    private fun notifyUser() {
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notificação reservatorio")
            .setContentText("Ixi, o nível do reservatório está baixo!")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(idnotifica, mBuilder.build())
        } }
    //criando notificação reservatorio zerado
    private fun notifyUser2() {
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Notificação reservatorio vazio")
            .setContentText("Ixi, a água acabou!")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(idnotifica, mBuilder.build())
        } }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

  }