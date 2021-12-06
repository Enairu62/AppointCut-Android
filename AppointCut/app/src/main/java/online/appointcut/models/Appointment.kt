package online.appointcut.models

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import online.appointcut.network.ApcService
import com.squareup.moshi.Json
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.*

class Appointment(): ViewModel() {
    @Json(name = "Date") lateinit var _rawDate: String
    var date: String
        get(){
            return _rawDate.substringBefore("T")
        }
        set(s: String){
            _rawDate = s;
        }
    @Json(name = "TimeIn") lateinit var timeIn: String
    @Json(name = "TimeOut") lateinit var timeOut: String
    @Json(name = "CustomerID") var customerID: Int? = 0
    @Json(name = "CustomerName") var customerName: String? = ""
    @Json(name = "ShopID") var shopId = 0
    @Json(name = "EmployeeID") var employeeId = 0
    @Json(name = "ShopServicesID") var shopServiceId = 0
    @Json(name = "HaircutID") var haircutId: Int? = 0
    @Json(name = "appStatusID")var appStatusId = 1
    @Json(name = "Service") var serviceName: String = ""
    @Json(name = "ShopName") var shopName = ""

    var id = 0
    var serviceDuration: Int = 0
    var amountDue = 0
    var employeeName = ""
    var userToken = ""

    var list = mutableListOf<Appointment>()

    constructor(date: String,timeIn: String, timeOut: String): this(){
        this._rawDate = date
        this.timeIn = timeIn
        this.timeOut = timeOut
    }

    /**
     * Sets the appointment to the server and sets the appointment ID after
     * @param context The associated context
     * @param callback Method to be called after finishing
     */
    suspend fun setAppointment(context: Context, callback: () -> Unit){
        try {
            val appointmentId = ApcService.retrofitService.setAppointment(this@Appointment)
            id = appointmentId
            Log.d("Appointment", "Appointment ID: $id")
            callback()
        }catch (e: SocketTimeoutException){
            Log.e("Appointment", "Server timed out", e)
            Toast.makeText(context, "Server timed out", Toast.LENGTH_SHORT).show()
        }catch(e: ConnectException) {
            Log.e("Appointment", "Error occurred trying to set appointment", e)
            Toast.makeText(context, "Server unreachable", Toast.LENGTH_SHORT).show()
        }catch (e: Exception) {
            Log.e("Appointment", "Error occurred trying to set appointment", e)
            Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun getCustomerApproved(){
        list = mutableListOf<Appointment>().apply {
            addAll(ApcService.retrofitService.getCustomerApprovedAppointments(userToken))
        }
    }

    suspend fun getCustomerCompleted(){
        list = mutableListOf<Appointment>().apply {
            addAll(ApcService.retrofitService.getCustomerCompletedAppointments(userToken))
        }
    }
}