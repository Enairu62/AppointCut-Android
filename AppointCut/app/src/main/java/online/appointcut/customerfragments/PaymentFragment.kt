package online.appointcut.customerfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import online.appointcut.BuildConfig
import online.appointcut.customerfragments.viewmodels.PaymentViewModel
import online.appointcut.databinding.PaymentFragmentBinding
import online.appointcut.models.Appointment
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.cancel.OnCancel
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.error.OnError
import com.paypal.checkout.order.*
import kotlinx.coroutines.launch
import online.appointcut.network.ApcService

class PaymentFragment : Fragment() {

    companion object {
        fun newInstance() = PaymentFragment()
    }

    private lateinit var viewModel: PaymentViewModel
    private val sharedViewModel: Appointment by activityViewModels()
    private lateinit var binding: PaymentFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PaymentFragmentBinding.inflate(LayoutInflater.from(inflater.context),container,false)

        //paypal settings
        val config = CheckoutConfig(
            application = requireActivity().application,
            clientId = "Ae8JGFX_akDuJezkYzEzMhgxt1ygHnnfxeoqSxw3zgTcCyYBS6aHjI7ysukXWBvedMr9UjnSakQ2cFHv",
            environment = Environment.SANDBOX,
            returnUrl = "${BuildConfig.APPLICATION_ID}://paypalpay",
            currencyCode = CurrencyCode.PHP,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(loggingEnabled = true)
        )
        PayPalCheckout.setConfig(config)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)

        //apply sharedViewModel contents
        sharedViewModel.apply{
            binding.shopName.text = shopName
            binding.service.text = serviceName
            binding.time.text = timeIn
            binding.due.text = amountDue.toString()
            binding.date.text = date
        }

        //paypal button settings
        binding.paypalButton.setup(
            createOrder = CreateOrder { createOrderActions ->
                val order = Order(
                    intent = OrderIntent.CAPTURE,
                    appContext = AppContext(
                        userAction = UserAction.PAY_NOW
                    ),
                    purchaseUnitList = listOf(
                        PurchaseUnit(
                            amount = Amount(
                                currencyCode = CurrencyCode.PHP,
                                value = "${sharedViewModel.amountDue}"
                            ),
                            description = "${sharedViewModel.serviceName}" +
                                    "_${sharedViewModel.shopName}_${sharedViewModel.shopId}"
                        )
                    )
                )
                createOrderActions.create(order)
            },
            onPaymentApproval,onCancel, onError
        )
    }

    //paypal listeners
    private val onPaymentApproval = OnApprove{ captureOrderResult ->
        //Log the result
        Log.i("CaptureOrder", "CaptureOrderResult: $captureOrderResult")
        //thank the customer
        Toast.makeText(context,"Thank you!", Toast.LENGTH_LONG).show()
        lifecycleScope.launch {
            ApcService.retrofitService.recordPayment(
                sharedViewModel.userToken,
                sharedViewModel.id,
                sharedViewModel.shopId,
                sharedViewModel.amountDue
            )
        }
        //navigate back home
        requireActivity().viewModelStore.clear()
        val action = PaymentFragmentDirections.actionPaymentFragmentToFragmentFindBarberShop()
        findNavController().navigate(action)


    }
    private val onCancel = OnCancel{
        Toast.makeText(context,"Advance Payment Cancelled",Toast.LENGTH_SHORT).show()
        val action = PaymentFragmentDirections.actionPaymentFragmentToFragmentFindBarberShop()
        findNavController().navigate(action)
    }
    private val onError = OnError{
        binding.paypalButton.visibility = View.VISIBLE
        Log.d("PaymentFrament", "Error: $it")
        Toast.makeText(context,"An Error Occured",Toast.LENGTH_SHORT).show()
    }

}