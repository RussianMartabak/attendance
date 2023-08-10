package com.martabak.phincon

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.martabak.phincon.databinding.FragmentAbsenBinding
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AbsenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AbsenFragment : Fragment() {

    private var _binding: FragmentAbsenBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private var checkIn = true
    var selectedLokasi: Lokasi? = null



    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAbsenBinding.inflate(inflater, container, false)
        val view = binding.root
        auth = FirebaseAuth.getInstance()
        var currrentUser = auth.currentUser
        //TLDR the Lokasi and its boring details
        var lokasiListAll = mutableListOf<Lokasi>(Lokasi("PT. Phincon",
            "Office. 88 @Kasablanka Office Tower 18th Floor",
            R.drawable.phincon_office),
            Lokasi("Telkomsel Smart Office",
                "Jl. Jend. Gatot Subroto Kav. 52. Jakarta Selatan",
                R.drawable.telkom_office),
            Lokasi("Rumah",
                "Tangerang",
                R.drawable.rumah
                ))
        //REMEMBER: LIsts must be cloned. assigning it to var only give reference
        var activeLokasiList = lokasiListAll.toMutableList()

        //initiate custom listener for recyclerview here
        val recyclerClickListener = object : RecyclerViewClickListener {
            override fun recyclerViewItemClicked(pos: Int) {
                selectedLokasi = lokasiListAll[pos]
            }

        }

        val adapter = LokasiAdapter(activeLokasiList, recyclerClickListener)
        binding.LocationRecycler.adapter = adapter
        binding.LocationRecycler.layoutManager = LinearLayoutManager(activity)

        binding.checkButton.setOnClickListener {

            if (selectedLokasi != null) {

                //handle the rview
                if (checkIn){
                    activeLokasiList.clear()
                    activeLokasiList.add(selectedLokasi!!)
                    adapter.notifyDataSetChanged()
                    sendLogtoFS(selectedLokasi!!, currrentUser!!)
                    //UI stuff
                    binding.checkButton.setImageResource(R.drawable.checkout_circle)
                    binding.buttonText.text = "CHECK OUT"
                    checkIn = false

                    Log.d("zaky", "adapter change for checking in invoked")
                } else {
                    activeLokasiList.clear()
                    activeLokasiList.addAll(lokasiListAll)
                    adapter.notifyDataSetChanged()
                    sendLogtoFS(selectedLokasi!!, currrentUser!!)
                    //UI stuff
                    binding.checkButton.setImageResource(R.drawable.checkin_circle)
                    binding.buttonText.text = "CHECK IN"
                    checkIn = true
                    Log.d("zaky", "adapter change for checking out invoked")
                }


            }


        }

        return view
    }

    fun sendLogtoFS(lokasi : Lokasi, user : FirebaseUser) {
        val db = Firebase.firestore
        val currentTime = Calendar.getInstance().time
        val timeStamp = Timestamp(currentTime)
        val type : String = if (checkIn) "Check In" else "Check Out"
        val loc = lokasi.header

        val logData = hashMapOf(
            "waktu_absen" to timeStamp,
            "user_id" to user.uid,
            "lokasi" to loc,
            "tipe" to type
        )
        db.collection("absensi").add(logData)

        //use padStart to format clock (not here!!maybe in next fragment
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}