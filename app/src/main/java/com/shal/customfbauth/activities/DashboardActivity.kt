package com.shal.customfbauth.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.shal.customfbauth.R
import com.shal.customfbauth.utils.FirebaseUtils
import com.shal.customfbauth.viewmodels.GroceryViewModel


class DashboardActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    val model: GroceryViewModel by viewModels()

    companion object {
        val ACTIVITY_RESULT_REQUEST = 101
        val INTENT_GROCERY = "INTENT_GROCERY"
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        // Bottom Navigation View
        //val navViewBottom: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        // Drawer Navigation view
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        //navController.popBackStack(R.id.fragmentC, true);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_profile, R.id.nav_settings),
            drawerLayout)
        //this.setupActionBarWithNavController(navController, appBarConfigurationBottom)
        this.setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        //navViewBottom.setupWithNavController(navController)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun saveBill() {
        // add bill to the database

/*        for(oldGrocery in finalGroceryList){
            // delete the added grocery item from database
            FirebaseUtils.firestore.collection("grocery")
                .document(oldGrocery.id!!)
                .delete()
                .addOnSuccessListener { documentReference ->
                    Log.w("Dashboard", "document deleted ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w("Dashboard", "Error deleting document $e")
                }
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {

                FirebaseUtils.auth.signOut()

                val sharedPreferences =
                    this.getSharedPreferences(getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE)
                sharedPreferences.edit().putBoolean(getString(R.string.already_login), false)
                    .apply()

                model.deleteAllGroceries()
                startActivity(Intent(this, LoginActivity::class.java))
                return true
            }
            R.id.cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                return true
            }
            R.id.menu_refresh -> {
                Log.i("TAG", "Refresh Grocery list from menu")
                model.getGroceryFromFirebase()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /*   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
           super.onActivityResult(requestCode, resultCode, data)
           Log.i("DashboardAcivity", "Here on Activity Result $requestCode $resultCode $data")

           if (requestCode == ACTIVITY_RESULT_REQUEST) {
               model.insert(data!!.getParcelableExtra(INTENT_GROCERY))
               Toast.makeText(this, "Data has been inserted", Toast.LENGTH_SHORT).show()
           } else {
               Toast.makeText(this, "Unable to insert data", Toast.LENGTH_SHORT).show()
           }
       }*/

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}