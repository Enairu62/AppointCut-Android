package online.appointcut.barberfragments

import DataModels.DataModelSchedule
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import online.appointcut.R
import online.appointcut.adapters.BarberAppointmentAdapter
import online.appointcut.models.Appointment
import online.appointcut.network.ApcService
import devs.mulham.horizontalcalendar.HorizontalCalendar
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener
import kotlinx.coroutines.launch
import online.appointcut.LoginActivity
import online.appointcut.databinding.DialogHairstyleBinding
import online.appointcut.dialog.HairstyleDialog
import java.net.ConnectException
import java.util.*


class FragmentSchedule : Fragment() {
    private val list = ArrayList<DataModelSchedule>()
    private var selectedDate = GregorianCalendar()
    private var appointments = listOf<Appointment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    val drawerLayoutBarber: DrawerLayout =
                        activity!!.findViewById(R.id.drawerLayoutBarber)
                    if (drawerLayoutBarber.isDrawerOpen(GravityCompat.START)) {
                        drawerLayoutBarber.closeDrawer(GravityCompat.START)
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        val btnDateRange = view.findViewById<View>(R.id.btnDateRange) as Button

        /* starts before 6 month from now */
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.MONTH, -6)

        /* ends after 6 month from now */
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.MONTH, 6)


        // on below line we are setting up our horizontal calendar view and passing id our calendar view to it.
        val horizontalCalendar = HorizontalCalendar.Builder(
            view,
            R.id.calendarView
        ) // on below line we are adding a range
            // as start date and end date to our calendar.
            .range(startDate, endDate) // on below line we are providing a number of dates
            // which will be visible on the screen at a time.
            .datesNumberOnScreen(7)
            .configure() // starts configuration.
            .formatTopText("MMM\nEE\ndd") // default to "MMM".
            .sizeTopText(12F)
            .showBottomText(false)
            .colorTextTop(Color.GRAY, Color.WHITE)
            .colorTextMiddle(Color.BLACK, Color.WHITE)
            .colorTextBottom(Color.GRAY, Color.WHITE)
            .end() // ends configuration.
            // at last we are calling a build method
            // to build our horizontal recycler view.
            .build()
        horizontalCalendar.refresh()
        horizontalCalendar.calendarListener = object : HorizontalCalendarListener() {
            override fun onDateSelected(date: Calendar, position: Int) {
                // on below line we are printing date
                // in the logcat which is selected.
                Log.d("TAG", "CURRENT DATE IS ${date.get(Calendar.MONTH)} ${date.get(Calendar.DATE)} ${position}")
                selectedDate = date as GregorianCalendar
                updateAppointmentsList()
            }
        }

        updateAppointmentsList()
        val recyclerView = view.findViewById<View>(R.id.approvedRecycler) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        val adapter = BarberAppointmentAdapter(appointments)
        recyclerView.adapter = adapter

        return view
    }

    private val itemClickListener = BarberAppointmentAdapter.ItemClickListener{
        HairstyleDialog(it.hairStyle!!).show(parentFragmentManager,null)
    }

    private fun updateAppointmentsList() {
        view?.findViewById<RecyclerView>(R.id.approvedRecycler)?.adapter = null
        viewLifecycleOwner.lifecycleScope.launch {
            val token = activity?.intent?.getStringExtra(LoginActivity.USER_TOKEN) ?: ""
            val day = (selectedDate.get(Calendar.DATE)).toString()
            val month = (selectedDate.get(Calendar.MONTH) + 1).toString()
            val year = selectedDate.get(Calendar.YEAR).toString()
            Log.d("FragmentSchedule", "$token $day $month $year")

            try {
                appointments =
                    ApcService.retrofitService.getBarberFullAppointments(token, day, month, year)?:listOf<Appointment>()
                Log.d("FragmentSchedule","Retrieved appointments")
                for(apt in appointments){
                    apt.getHairstyle()
                }
                val adapter = BarberAppointmentAdapter(appointments)
                adapter.setOnItemClickListener(itemClickListener)
                view?.findViewById<RecyclerView>(R.id.approvedRecycler)?.adapter = adapter
            }catch(e: ConnectException){
                Log.e("FragmentSchedule" , "${e}", e)
                Toast.makeText(requireContext(),"Unable to Connect to server",Toast.LENGTH_LONG)
                    .show()
            }catch(e: Exception){
                Log.e("FragmentSchedule" , "${e}", e)
                Toast.makeText(requireContext(),"Unknown error occurred",Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}