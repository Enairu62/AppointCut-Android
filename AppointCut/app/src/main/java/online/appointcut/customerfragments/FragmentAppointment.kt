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
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentAppointment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentAppointment : Fragment() {
    private val listApproved = ArrayList<DataModelAppointmentList>()
    private val listPending = ArrayList<DataModelAppointmentList>()
    private val listCompleted = ArrayList<DataModelAppointmentList>()
    var emptyApproved: TextView? = null
    var emptyPending: TextView? = null
    var emptyCompleted: TextView? = null
    var approvedRecycler: RecyclerView? = null
    var recyclerView2: RecyclerView? = null
    var completedRecycler: RecyclerView? = null



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

    /**
     * Called immediately after [.onCreateView]
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     * @param view The View returned by [.onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        declareViews()
        displayAppointmentMethod()
    }


    override fun onResume() {
        super.onResume()
        buildListDataApproved()
        buildListDataPending()
        buildListDataCompleted()
        emptyApprovedLabel()
        emptyPendingLabel()
        emptyCompletedLabel()
    }

    private fun declareViews() {
        val bottomNavCustomer =
            requireActivity().findViewById<View>(R.id.bottomNavCustomer) as BottomNavigationView
        bottomNavCustomer.visibility = View.GONE
        approvedRecycler = requireView().findViewById<View>(R.id.approvedRecycler) as RecyclerView
        completedRecycler = requireView().findViewById<View>(R.id.completedRecycler) as RecyclerView
    }

    private fun buildListDataApproved() {
        //example of empty list
        val profilePic = intArrayOf(R.drawable.cavalry_barbershop)
        listApproved.add(
            DataModelAppointmentList(
                profilePic[0],
                "28 Cavalry",
                "Haircut",
                "November 10, 2021 / 10:00 am - 11:00 am"
            )
        )
    }

    private fun buildListDataPending() {
        val profilePic = intArrayOf(R.drawable.cavalry_barbershop)
        //listPending.add(new DataModelAppointmentList(profilePic[0],"28 Cavalry", "Hair Color","November 16, 2021 / 12:00 pm - 01:00 pm"));
    }

    private fun buildListDataCompleted() {
        val profilePic = intArrayOf(R.drawable.cavalry_barbershop)
        listCompleted.add(
            DataModelAppointmentList(
                profilePic[0],
                "28 Cavalry",
                "Massage",
                "November 7, 2021 / 03:00 pm - 04:00 pm"
            )
        )
        listCompleted.add(
            DataModelAppointmentList(
                profilePic[0],
                "28 Cavalry",
                "Haircut",
                "October 19, 2021 / 9:30 am - 10:30 am"
            )
        )
    }

    private fun emptyApprovedLabel() {
        if (listApproved.isEmpty()) {
            approvedRecycler!!.visibility = View.GONE
            emptyApproved!!.visibility = View.VISIBLE
        } else {
            approvedRecycler!!.visibility = View.VISIBLE
            emptyApproved!!.visibility = View.INVISIBLE
        }
    }

    private fun emptyPendingLabel() {
        if (listPending.isEmpty()) {
            recyclerView2!!.visibility = View.GONE
            emptyPending!!.visibility = View.VISIBLE
        } else {
            recyclerView2!!.visibility = View.VISIBLE
            emptyPending!!.visibility = View.INVISIBLE
        }
    }

    private fun emptyCompletedLabel() {
        if (listCompleted.isEmpty()) {
            completedRecycler!!.visibility = View.GONE
            emptyCompleted!!.visibility = View.VISIBLE
        } else {
            completedRecycler!!.visibility = View.VISIBLE
            emptyCompleted!!.visibility = View.INVISIBLE
        }
    }

    private fun displayAppointmentMethod() {
        val layoutManager1 = LinearLayoutManager(activity)
        val layoutManager2 = LinearLayoutManager(activity)
        val layoutManager3 = LinearLayoutManager(activity)
        approvedRecycler!!.layoutManager = layoutManager1
        recyclerView2!!.layoutManager = layoutManager2
        completedRecycler!!.layoutManager = layoutManager3
        val adapter1 = MyAdapterAppointmentList(listApproved)
        val adapter2 = MyAdapterAppointmentList(listPending)
        val adapter3 = MyAdapterAppointmentList(listCompleted)
        approvedRecycler!!.adapter = adapter1
        recyclerView2!!.adapter = adapter2
        completedRecycler!!.adapter = adapter3
        buildListDataApproved()
        buildListDataPending()
        buildListDataCompleted()
        emptyApprovedLabel()
        emptyPendingLabel()
        emptyCompletedLabel()
    }
}