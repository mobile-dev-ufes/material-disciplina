package com.example.introducaonavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.introducaonavigation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbarDrawerAndNavButtom()
    }

    fun setToolbarDrawerAndNavButtom() {
        val navHostFrag = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFrag.navController
        binding.navigationDrawer.setupWithNavController(navController)
        binding.bottomNavMenu.setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.toolbar.inflateMenu(R.menu.menu_toolbar)
        binding.toolbar.setOnMenuItemClickListener({
            if(it.itemId == R.id.search){
                Toast.makeText(this, "Search", Toast.LENGTH_LONG).show()
            } else if (it.itemId == R.id.info) {
                Toast.makeText(this, "Info", Toast.LENGTH_LONG).show()
            }
            true
        })
    }

}