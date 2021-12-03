package online.appointcut.customerfragments

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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import online.appointcut.R
import online.appointcut.adapters.ShopAdapter
import online.appointcut.models.Appointment
import online.appointcut.network.ApcService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


class FragmentFindShop : Fragment(), MyAdapterBarberShop.ItemClickListener {
    private val sharedViewModel: Appointment by activityViewModels()

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
        sharedViewModel.customerID = requireActivity().intent.getIntExtra("userId",-1)
        sharedViewModel.customerName = requireActivity().intent.getStringExtra("fullName")?:""
        sharedViewModel.userToken = requireActivity().intent.getStringExtra("userToken")?:""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FragmentFindBarberShop", "onCreateView started")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_find_barber_shop, container, false)
        val recyclerView = view.findViewById<View>(R.id.approvedRecycler) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        val bottomNavCustomer = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavCustomer)
        bottomNavCustomer.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val list = ApcService.retrofitService.getShops()
                Log.d("FragmentShop", list.size.toString() + "")
                val adapter = ShopAdapter(
                    list, Navigation
                        .findNavController(requireActivity(), R.id.fragmentContainerView),
                    sharedViewModel
                )
                recyclerView.adapter = adapter
            } catch (e: Exception) {
                Log.e("FindShopJava", e.toString(), e)
                Toast.makeText(context, "Unable to load Barbershops", Toast.LENGTH_SHORT).show()
            }
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
}