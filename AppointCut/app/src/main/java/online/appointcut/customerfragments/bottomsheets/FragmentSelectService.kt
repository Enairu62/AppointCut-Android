package online.appointcut.customerfragments.bottomsheets

import online.appointcut.models.ShopService
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import online.appointcut.R
import online.appointcut.adapters.ShopServiceAdapter
import online.appointcut.databinding.FragmentBottomSheetSelectServicesBinding
import online.appointcut.models.Appointment
import online.appointcut.network.ApcService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fragments.BottomSheetFragmentSelectBarber
import kotlinx.coroutines.launch



class FragmentSelectService(private val shopId: Int) : BottomSheetDialogFragment() {
    private val sharedViewModel: Appointment by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        val binding = FragmentBottomSheetSelectServicesBinding.inflate(inflater)
            inflater.inflate(R.layout.fragment_bottom_sheet_select_services, container, false)
        val btnBack = binding.btnBack
        val bottomSheetFragmentSelectBarber: BottomSheetDialogFragment =
            BottomSheetFragmentSelectBarber()
        btnBack.setOnClickListener { dismiss() }
        val recyclerView: RecyclerView = binding.servicesRecycler
        val gridLayoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager

        viewLifecycleOwner.lifecycleScope.launch {
            try{
                val adapter = ShopServiceAdapter(
                    ApcService.retrofitService.getShopServices(shopId),
                    this@FragmentSelectService::showSelectBarberFragment
                )
                recyclerView.adapter = adapter
            }catch(e: Exception){
                Toast.makeText(context,"Unable to load Services", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    /**
     * Callback function to be used by recycler items to show Select Barber Fragment
     * @param name Name of the service clicked by user
     */
    private fun showSelectBarberFragment(shopService: ShopService) {
        Log.d("FragmentSelectService","select barber with ${shopService.name} service")
        //close this dialog
        dismiss()
        //set ViewModel
        sharedViewModel.apply{
            shopServiceId = shopService.id
            amountDue = shopService.price
            serviceDuration = shopService.duration
            serviceName = shopService.name
        }

        //show next dialog
        val fragmentSelectBarber = FragmentSelectBarber(shopService)
        fragmentSelectBarber
            .show(requireActivity().supportFragmentManager,fragmentSelectBarber.tag)
    }
}