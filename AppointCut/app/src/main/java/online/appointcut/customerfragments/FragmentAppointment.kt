package online.appointcut.customerfragments

import DataModels.DataModelAppointmentList
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.drawerlayout.widget.DrawerLayout
import online.appointcut.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.GravityCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import MyAdapters.MyAdapterAppointmentList
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import kotlinx.coroutines.launch
import online.appointcut.adapters.CustomerAppointmentAdapter
import online.appointcut.models.Appointment
import java.net.ConnectException
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentAppointment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentAppointment : Fragment() {
    private val listApproved = ArrayList<DataModelAppointmentList>()
    private val listCompleted = ArrayList<DataModelAppointmentList>()
    var approvedRecycler: RecyclerView? = null
    var completedRecycler: RecyclerView? = null

    private val sharedViewModel: Appointment by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    val drawerLayoutCustomer: DrawerLayout =
                        activity!!.findViewById(R.id.drawerLayoutCustomer)
                    val bottomNavCustomer =
                        activity!!.findViewById<View>(R.id.bottomNavCustomer) as BottomNavigationView
                    if (drawerLayoutCustomer.isDrawerOpen(GravityCompat.START)) {
                        drawerLayoutCustomer.closeDrawer(GravityCompat.START)
                    } else {
                        Navigation.findNavController(
                            activity!!, R.id.fragmentContainerView
                        ).navigate(R.id.action_fragmentAppointment_to_fragmentFindBarberShop)
                        bottomNavCustomer.visibility = View.VISIBLE
                        bottomNavCustomer.menu.setGroupCheckable(0, true, true)
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_appointment, container, false)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavCustomer =
            requireActivity().findViewById<View>(R.id.bottomNavCustomer) as BottomNavigationView
        bottomNavCustomer.visibility = View.GONE
        approvedRecycler = requireView().findViewById<View>(R.id.approvedRecycler) as RecyclerView
        completedRecycler = requireView().findViewById<View>(R.id.completedRecycler) as RecyclerView


        approvedRecycler!!.layoutManager = LinearLayoutManager(activity)
        completedRecycler!!.layoutManager = LinearLayoutManager(activity)

        val approved = Appointment().apply { userToken = sharedViewModel.userToken }
        val completed = Appointment().apply { userToken = sharedViewModel.userToken }
        approvedRecycler!!.adapter = MyAdapterAppointmentList(listCompleted)

        //Approved appointments
        lifecycleScope.launch {
            try{
                approved.getCustomerApproved()
                approvedRecycler!!.adapter = CustomerAppointmentAdapter(approved.list)
            }catch(e: ConnectException){
                Log.e("FragmentAppointment", "Error: $e", e)
                Toast.makeText(context,"Server Unreachable", Toast.LENGTH_SHORT)
                    .show()
            }catch(e: Exception){
                Log.e("FragmentAppointment", "Error: $e", e)
                Toast.makeText(context,"Unkown error", Toast.LENGTH_SHORT)
                    .show()
            }
            view.findViewById<ProgressBar>(R.id.approvedProgBar).visibility = View.GONE
        }

        //completed appointments
        lifecycleScope.launch {
            try{
                completed.getCustomerCompleted()
                completedRecycler!!.adapter = CustomerAppointmentAdapter(completed.list)
            }catch(e: ConnectException){
                Log.e("FragmentAppointment", "Error: $e", e)
                Toast.makeText(context,"Server Unreachable", Toast.LENGTH_SHORT)
                    .show()
            }catch(e: Exception){
                Log.e("FragmentAppointment", "Error: $e", e)
                Toast.makeText(context,"Unkown error", Toast.LENGTH_SHORT)
                    .show()
            }
            view.findViewById<ProgressBar>(R.id.completedProgBar).visibility = View.GONE
        }
    }
}