package fragments.kotlin

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.RecyclerView
import DataModels.DataModelSelectServices
import DataModels.ShopService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.appointcut.R
import fragments.BottomSheetFragmentSelectBarber
import androidx.recyclerview.widget.GridLayoutManager
import MyAdapters.MyAdapterSelectService
import android.view.View
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.appointcut.adapters.ShopServiceAdapter
import com.example.appointcut.databinding.FragmentBottomSheetSelectServicesBinding
import com.example.appointcut.network.ApcService
import fragments.kotlin.FragmentSelectService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSelectService.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSelectService(private val shopId: Int) : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        recyclerView.setLayoutManager(gridLayoutManager)

        viewLifecycleOwner.lifecycleScope.launch {
            val adapter = ShopServiceAdapter(ApcService.retrofitService.getShopServices(shopId))
            recyclerView.setAdapter(adapter)
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BottomSheetFragmentSelectServices.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(shopId:Int): FragmentSelectService {
            return FragmentSelectService(shopId)
        }
    }
}