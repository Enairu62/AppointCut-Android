package online.appointcut.customerfragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import online.appointcut.R
import online.appointcut.adapters.HairStyleRecyclerViewAdapter
import online.appointcut.dialog.HairstyleDialog
import online.appointcut.customerfragments.viewmodels.HairStyleViewModel
import online.appointcut.models.Appointment

/**
 * A fragment representing a list of Items.
 */
class HairStyleFragment : Fragment() {
    private val sharedViewModel: Appointment by activityViewModels()

    private var columnCount = 1
    private var vm = HairStyleViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_customer_hairstyle, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                vm.getHairStyles()
                vm.hairstyles.observe(viewLifecycleOwner){
                    adapter = HairStyleRecyclerViewAdapter(it).apply{
                        setItemClickListener(itemClickListener)
                    }

                }
            }
        }
        return view
    }

    val itemClickListener = HairStyleRecyclerViewAdapter.ItemClickListener{
        val setHairstyleDialog = HairstyleDialog(it).apply{
            positiveText = "Set Hairstyle"
        }
        setHairstyleDialog.onPositiveButton = DialogInterface.OnClickListener { dialogInterface, i ->
            sharedViewModel.haircutId = it.id
            sharedViewModel.hairStyle =  it
            Toast.makeText(requireContext(),"${it.name} has been set with ID: ${it.id}", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(
                HairStyleFragmentDirections.actionHairStyleFragmentToFragmentFindBarberShop()
            )
        }
        setHairstyleDialog.show(parentFragmentManager,null)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            HairStyleFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}