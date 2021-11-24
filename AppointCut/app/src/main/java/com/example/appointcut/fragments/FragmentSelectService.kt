package com.example.appointcut.fragments

import com.example.appointcut.models.ShopService
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appointcut.R
import com.example.appointcut.adapters.ShopServiceAdapter
import com.example.appointcut.databinding.FragmentBottomSheetSelectServicesBinding
import com.example.appointcut.fragments.bottomsheets.FragmentSelectBarber
import com.example.appointcut.network.ApcService
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fragments.BottomSheetFragmentSelectBarber
import kotlinx.coroutines.launch



class FragmentSelectService(private val shopId: Int) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //Inflate the layout for this fragment
        val binding = FragmentBottomSheetSelectServicesBinding.inflate(inflater)
            inflater.inflate(R.layout.fragment_bottom_sheet_select_services, container, false)
        val btnBack = binding.btnBack
        val btnNext = binding.btnNext
        val bottomSheetFragmentSelectBarber: BottomSheetDialogFragment =
            BottomSheetFragmentSelectBarber()
        btnBack.setOnClickListener { dismiss() }
        btnNext.setOnClickListener {
            dismiss()
            bottomSheetFragmentSelectBarber.show(
                requireActivity().supportFragmentManager,
                bottomSheetFragmentSelectBarber.tag
            )
        }
        val recyclerView: RecyclerView = binding.recyclerView
        val gridLayoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager

        viewLifecycleOwner.lifecycleScope.launch {
            val adapter = ShopServiceAdapter(ApcService.retrofitService.getShopServices(shopId),
                this@FragmentSelectService::showSelectBarberFragment)
            recyclerView.adapter = adapter
        }
        return binding.root
    }

    /**
     * Callback function to be used by recycler items to show Select Barber Fragment
     * @param name Name of the service clicked by user
     */
    private fun showSelectBarberFragment(shopService: ShopService) {
        Log.d("FragmentSelectService","select barber with ${shopService.name} service")
        dismiss()
        val fragmentSelectBarber = FragmentSelectBarber(shopService)
        fragmentSelectBarber
            .show(requireActivity().supportFragmentManager,fragmentSelectBarber.tag)
    }
}