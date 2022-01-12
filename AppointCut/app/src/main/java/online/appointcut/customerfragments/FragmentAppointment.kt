package online.appointcut.customerfragments

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import online.appointcut.R
import online.appointcut.adapters.CustomerAppointmentAdapter
import online.appointcut.customerfragments.bottomsheets.FragmentSelectBarber
import online.appointcut.customerfragments.bottomsheets.FragmentSelectService
import online.appointcut.customerfragments.dialogs.AppointmentDialog
import online.appointcut.databinding.FragmentAppointmentBinding
import online.appointcut.models.Appointment
import online.appointcut.network.ApcService
import java.net.ConnectException
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentAppointment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentAppointment : Fragment() {
    var approvedRecycler: RecyclerView? = null
    var completedRecycler: RecyclerView? = null

    private val sharedViewModel: Appointment by activityViewModels()
    private lateinit var approved: Appointment
    private lateinit var binding: FragmentAppointmentBinding


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
        binding = FragmentAppointmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bottomNavCustomer =
            requireActivity().findViewById<View>(R.id.bottomNavCustomer) as BottomNavigationView
        bottomNavCustomer.visibility = View.GONE
        approvedRecycler = requireView().findViewById<View>(R.id.approvedRecycler) as RecyclerView
        completedRecycler = requireView().findViewById<View>(R.id.completedRecycler) as RecyclerView
        binding.btnApproved.setOnClickListener { showAppointmentType("approved") }
        binding.btnCancelled.setOnClickListener { showAppointmentType("cancelled") }
        binding.btnCompleted.setOnClickListener { showAppointmentType("completed") }
        binding.btnMissed.setOnClickListener { showAppointmentType("missed") }

        approvedRecycler!!.layoutManager = LinearLayoutManager(activity)
        completedRecycler!!.layoutManager = LinearLayoutManager(activity)
        binding.cancelledRecycler.layoutManager = LinearLayoutManager(activity)
        binding.missedRecycler.layoutManager = LinearLayoutManager(activity)

        approved = Appointment().apply { userToken = sharedViewModel.userToken }
        val completed = Appointment().apply { userToken = sharedViewModel.userToken }
        val cancelled  = Appointment().apply{ userToken = sharedViewModel.userToken }
        val missed = Appointment().apply{ userToken = sharedViewModel.userToken }


        loadApproved()
        //region load other appointments
        lifecycleScope.launch {
            try{
                completed.getCustomerAppointments(Appointment.COMPLETED)
                cancelled.getCustomerAppointments(Appointment.CANCELLED)
                missed.getCustomerAppointments(Appointment.NO_SHOW)

                completedRecycler!!.adapter = CustomerAppointmentAdapter(completed.list)
                binding.missedRecycler.adapter = CustomerAppointmentAdapter(missed.list)
                binding.cancelledRecycler.adapter = CustomerAppointmentAdapter(
                    cancelled.list,
                    this@FragmentAppointment::reschedule
                )
            }catch(e: ConnectException){
                Log.e("FragmentAppointment", "Error: $e", e)
                Toast.makeText(context,"Server Unreachable", Toast.LENGTH_SHORT)
                    .show()
            }catch(e: Exception){
                Log.e("FragmentAppointment", "Error: $e", e)
                Toast.makeText(context,"Unkown error", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.completedProgBar.visibility = View.GONE
            binding.cancelledProgBar.visibility = View.GONE
            binding.missedProgBar.visibility = View.GONE
        }
        //endregion
    }

    private fun loadApproved() {
        //Approved appointments
        lifecycleScope.launch {
            try{
                approved.getCustomerAppointments(Appointment.APPROVED)
                approvedRecycler!!.adapter = CustomerAppointmentAdapter(approved.list,::cancelHandler)
            }catch(e: ConnectException){
                Log.e("FragmentAppointment", "Error: $e", e)
                Toast.makeText(context,"Server Unreachable", Toast.LENGTH_SHORT)
                    .show()
            }catch(e: Exception){
                Log.e("FragmentAppointment", "Error: $e", e)
                Toast.makeText(context,"Unkown error", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.approvedProgBar.visibility = View.GONE
        }
    }

    /**
     * Callback for customer cancellations
     */
    private fun cancelHandler (appointment: Appointment) {
        AppointmentDialog(appointment).also {
            it.message = "Are you sure you want to cancel the following appointment?"

            it.onPositiveAnswer = {
                lifecycleScope.launch {
                    try {
                        val response = ApcService.retrofitService.cancelAppointment(
                            sharedViewModel.userToken,
                            appointment.id
                        )
                        when (response) {
                            "SUCCESS" -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Appointment cancelled",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                loadApproved()
                            }
                            "TIME" -> {
                                Toast.makeText(
                                    requireContext(),
                                    "Can't cancel appointments 5 hours before",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }catch(e: Exception){
                        Log.e("CancelAppointment", "Error $e",e)
                        Toast.makeText(
                            requireContext(),
                            "An error occurred",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }.show(parentFragmentManager, "Cancel Appointment")
    }

    /**
     * Callback for rescheduling cancelled Appointment
     */
    private fun reschedule(appointment: Appointment){

        AppointmentDialog(appointment).also {
            it.message = "Reschedule the following appointment?"
            it.positiveText = "Reschedule"

            it.onPositiveAnswer = {
                lifecycleScope.launch {
                    try {
                        //get the shop service
                        val shopServiceId = ApcService.retrofitService.getAppointmentShopServiceId(appointment.id)
                        val service = ApcService.retrofitService
                            .getShopService(shopServiceId)


                        //change the shared viewModel
                        sharedViewModel.apply {
                            appointment.let {
                                customerName = it.customerName
                                this.shopServiceId = shopServiceId
                                haircutId = it.haircutId
                                appStatusId = it.appStatusId
                                serviceName = it.serviceName
                                shopName = it.shopName
                                shopId = it.shopId
                            }
                        }

                        //show the barber select bottom sheet
                        val fragmentSelectBarber = FragmentSelectBarber(service)
                        fragmentSelectBarber.show(
                            requireActivity().supportFragmentManager,
                            fragmentSelectBarber.tag
                        )
                    }catch(e: Exception){
                        Log.e("CancelAppointment", "Error $e",e)
                        Toast.makeText(
                            requireContext(),
                            "An error occurred",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            }
        }.show(parentFragmentManager, "Reschedule Appointment")
    }

    /**
     * Shows the layout of indicated appointment type
     * @param type the layout to be shown
     */
    private fun showAppointmentType(type: String){
        Log.d("FragmentAppointment", "Show Layout: $type")
        //gone everything
        View.GONE.let{
            binding.apply {
                approvedLayout.visibility = it
                completedLayout.visibility = it
                cancelledLayout.visibility = it
                missedLayout.visibility = it
            }
        }

        //visible the layout
        View.VISIBLE.let{
            binding.apply {
                when(type){
                    "approved" ->
                        approvedLayout.visibility = it
                    "completed" ->
                        completedLayout.visibility = it
                    "cancelled" ->
                        cancelledLayout.visibility = it
                    "missed" ->
                        missedLayout.visibility = it
                }
            }
        }

    }
}