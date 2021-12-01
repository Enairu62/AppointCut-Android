package online.appointcut.customerfragments.bottomsheets

import DataModels.DataModelSelectBarber
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import online.appointcut.R
import online.appointcut.adapters.BarberAdapter
import online.appointcut.models.Appointment
import online.appointcut.models.Barber
import online.appointcut.models.ShopService
import online.appointcut.network.ApcService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fragments.BottomSheetFragmentSelectServices
import kotlinx.coroutines.launch
import java.util.*

class FragmentSelectBarber(private val shopService: ShopService) : BottomSheetDialogFragment() {
    var recyclerView: RecyclerView? = null
    private val sharedViewModel: Appointment by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_select_barber, container, false)
        val btnBack = view.findViewById<View>(R.id.btnBack) as Button
        val btn = view.findViewById<View>(R.id.button) as Button
        val bottomSheetFragmentSelectServices = BottomSheetFragmentSelectServices()

        //set button listener
        btnBack.setOnClickListener {
            dismiss()
            bottomSheetFragmentSelectServices.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragmentSelectServices.tag
            )
        }
        //change category text
        btn.text = shopService.name

        //set recycler
        recyclerView = view.findViewById<View>(R.id.approvedRecycler) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = layoutManager
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val list = getBarbersWithService(shopService)
                val adapter =
                    BarberAdapter(list, this@FragmentSelectBarber::showSelectScheduleFragment)
                recyclerView!!.adapter = adapter
            }catch (e: Exception){
                Toast.makeText(context,"Unable to load Barbers", Toast.LENGTH_SHORT)
            }
        }

        return view
    }

    /**
     * Returns a list of Barbers that can perform given service
     * @param shopService The service that needs a barber
     * @return A list of barbers that can perform the service
     */
    private suspend fun getBarbersWithService(shopService: ShopService): List<Barber> {
        val employeeSpecialization = ApcService.retrofitService
            .getSpecializationWithService(shopService.id)
        val barbers: MutableList<Barber> = mutableListOf()

        for (e in employeeSpecialization) {
            barbers.add(
                ApcService.retrofitService.getBarber(e.employeeId)
            )
        }
        return barbers
    }

    private fun showSelectScheduleFragment(barber: Barber){
        dismiss()

        sharedViewModel.employeeId = barber.id
        sharedViewModel.employeeName = barber.fullName

        Log.d("FragmentSelectBarber", "Showing select schedule")
        FragmentSelectSchedule(barber).let{
            it.show(requireActivity().supportFragmentManager,it.tag)
        }
    }


    fun onItemClick(dataModelSelectBarber: DataModelSelectBarber?) {
        /*Fragment fragment =  FragmentHairCompleteInfo.newInstance(dataModelHair.getImage(), dataModelHair.getTitle(), dataModelHair.getDesc());
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constraintLayoutCustomer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();*/
    }
}