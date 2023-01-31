package com.jorgesysl.picassookhttp3

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.jorgesysl.picassookhttp3.databinding.ActivityMainBinding
import java.io.File
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("Picasso", "Picasso start...")
        HttpClient.setUpCache(File(cacheDir.path, "ok_http"))

        var dirCache = File(cacheDir.path, "ok_http")
        if(dirCache.exists()){
            Log.i("Picasso", "dirCache EXISTE")
        }else{
            Log.e("Picasso", "dirCache NO EXISTE!")
        }

        try {
            val picassoBuilder = Picasso.Builder(this)
            picassoBuilder.downloader(OkHttp3Downloader(HttpClient().getClient()))
            Picasso.setSingletonInstance(picassoBuilder.build())
        } catch (e: IllegalStateException) {
            Log.e("Picasso", "ISE Picasso already initialized " + e.message)
        }

        if(dirCache.exists()){
            Log.i("Picasso", "2dirCache EXISTE")
        }else{
            Log.e("Picasso", "2dirCache NO EXISTE!")
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }




    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}