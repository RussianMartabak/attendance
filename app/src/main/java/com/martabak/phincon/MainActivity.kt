package com.martabak.phincon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.martabak.phincon.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        val homeFragment = AbsenFragment()
        val historyFragment = HistoryFragment()
        val profileFragment = ProfileFragment()
        // bottom view WON'T WORK WITH VIEW BINDING !!!!!!!!!!!!!!!!!!
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        setCurrentFragment(homeFragment)

        // bottom view WON'T WORK WITH VIEW BINDING !!!!!!!!!!!!!!!!!!
        bottomNavigation.setOnItemSelectedListener {

            when(it.itemId) {
                R.id.home -> {
                    setCurrentFragment(homeFragment)

                }
                R.id.history -> {
                    setCurrentFragment(historyFragment)

                }
                R.id.profile -> {
                    setCurrentFragment(profileFragment)

                }
            }
            Log.d("zaky", "bottom invoked")
            true
        }


    }

    private fun setCurrentFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.FragmentStage, fragment)
            commit()
        }
    }
}