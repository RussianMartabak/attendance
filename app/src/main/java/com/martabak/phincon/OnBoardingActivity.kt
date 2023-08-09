package com.martabak.phincon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.martabak.phincon.databinding.ActivityOnboardingBinding

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOnboardingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val fragmentList = listOf<Fragment>(AbsensiFragment(), AttendanceFragment(), MaskerFragment())
        val viewPager2 = binding.viewPager2

        binding.signupButton.setOnClickListener {
            val intent : Intent = Intent(this@OnBoardingActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }

            override fun getItemCount(): Int {
                return 3
            }
        }




    }
}