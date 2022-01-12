package online.appointcut.barberfragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import online.appointcut.LoginActivity
import online.appointcut.R
import online.appointcut.barberfragments.viewmodels.IncomeViewModel
import online.appointcut.databinding.FragmentIncomeBinding
import online.appointcut.network.ApcService
import java.util.*

class IncomeFragment : Fragment() {

    companion object {
        fun newInstance() = IncomeFragment()
    }

    private lateinit var viewModel: IncomeViewModel
    private lateinit var binding: FragmentIncomeBinding
    private var token: String = ""
    private var selectMonth = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set back button
        requireActivity().onBackPressedDispatcher.addCallback(this){
            val drawerLayoutBarber: DrawerLayout = requireActivity().findViewById(R.id.drawerLayoutBarber)
            if (drawerLayoutBarber.isDrawerOpen(GravityCompat.START)) {
                drawerLayoutBarber.closeDrawer(GravityCompat.START)
            } else {
                Navigation.findNavController(
                    requireActivity(), R.id.fragmentContainerView
                ).navigate(R.id.action_fragmentIncome_to_fragmentSchedule)
            }
        }

        token = activity?.intent?.getStringExtra(LoginActivity.USER_TOKEN) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncomeBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(IncomeViewModel::class.java)
        binding.approvedRecycler.layoutManager = LinearLayoutManager(requireActivity())

        updateMonth()
        updateDisplayWage()
        binding.nextMonth.setOnClickListener { addMonth(1) }
        binding.previousMonth.setOnClickListener { addMonth(-1) }
    }

    private fun addMonth(add: Int){
        selectMonth.add(Calendar.MONTH,add)
        updateMonth()
        updateDisplayWage()
    }
    private fun updateMonth(){
        binding.month.text = "${getMonth(selectMonth.get(Calendar.MONTH))}${selectMonth.get(Calendar.YEAR)}"
    }
    private fun updateDisplayWage(){
        lifecycleScope.launch{
            Log.d("Income", "$token,${selectMonth.get(Calendar.YEAR)},${selectMonth.get(Calendar.MONTH)}")
            val wage = ApcService.retrofitService.getEmployeeWage(token, selectMonth.get(Calendar.YEAR),selectMonth.get(Calendar.MONTH))
            binding.wageOutput.text = "Php $wage"
        }
    }

    private fun getMonth(monthIndex: Int): String{
        return when(monthIndex) {
            0 -> "Jan"
            1 -> "Feb"
            2 -> "Mar"
            3 -> "Apr"
            4 -> "May"
            5 -> "Jun"
            6 -> "Jul"
            7 -> "Aug"
            8 -> "Sep"
            9 -> "Oct"
            10 -> "Nov"
            11 -> "Dec"
            else -> ""
        }
    }
}