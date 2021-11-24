package com.example.appointcut.fragments

import DataModels.DataModelBarberShop
import MyAdapters.MyAdapterBarberShop
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appointcut.R
import com.example.appointcut.adapters.ShopAdapter
import com.example.appointcut.models.Shop
import com.example.appointcut.network.NetworkJava.getShop
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentFindShop.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentFindShop : Fragment(), MyAdapterBarberShop.ItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("FragmentFindBarberShop", "onCreate started")
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    val drawerLayoutCustomer: DrawerLayout =
                        activity!!.findViewById(R.id.drawerLayoutCustomer)
                    if (drawerLayoutCustomer.isDrawerOpen(GravityCompat.START)) {
                        drawerLayoutCustomer.closeDrawer(GravityCompat.START)
                    } else {
                        activity!!.moveTaskToBack(true)
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FragmentFindBarberShop", "onCreateView started")
        Toast.makeText(requireContext(),"Using Kotlin FindShop",Toast.LENGTH_SHORT).show()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_find_barber_shop, container, false)
        val recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        try {
            var list = getShop()
            Log.d("FragmentShop", list.size.toString() + "")
            val adapter = ShopAdapter(
                list, Navigation
                    .findNavController(requireActivity(), R.id.fragmentContainerView)
            )
            recyclerView.adapter = adapter
        } catch (e: Exception) {
            Log.e("FindShopJava", e.toString(), e)
            Toast.makeText(context, "Unable to load Barbershops", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        //list.clear()
    }

    override fun onItemClick(dataModelBarberShop: DataModelBarberShop) {
//        NavController navController = (NavController) Navigation.findNavController(getActivity(),R.id.fragmentContainerView);
//        NavDirections action = FragmentFindBarberShopDirections
//                .actionFragmentFindBarberShopToFragmentBarberShopMap();
//        navController.navigate(action);
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment FragmentFindBarberShop.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): FragmentFindShop {
            return FragmentFindShop()
        }
    }
}