package online.appointcut.network

import online.appointcut.models.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import online.appointcut.viewmodels.SignUpViewModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*


const val BASE_URL = "http://appointcut.online"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApcServiceInterface {
    @GET("rest/token/{email}-{pw}-{type}")
    suspend fun getToken(
        @Path("email") email: String,
        @Path("pw") pw: String,
        @Path("type") type: String): User

    @GET("rest/shops")
    suspend fun getShops(): List<Shop>

    @GET("rest/shops/services/{id}")
    suspend fun getShopServices(@Path("id") id: Int): List<ShopService>

    @GET("rest/shopservices/{id}")
    suspend fun getShopService(@Path("id") id: Int): ShopService

    @GET("/rest/shopservices/appointment/{id}")
    suspend fun getAppointmentShopServiceId(@Path("id") id: Int): Int

    @GET("rest/barbers/withshopservice/{id}")
    suspend fun  getSpecializationWithService(@Path("id") id: Int):
            List<EmployeeSpecialization>

    @GET("rest/barbers/{id}")
    suspend fun getBarber(@Path("id") id: Int): List<Barber>

    @GET("rest/barbers/appointments/{id}-{month}-{year}")
    suspend fun getBarberAppointmentsForMonthYear(
        @Path("id") id: Int,
        @Path("month") month: Int,
        @Path("year") year: Int
    ): List<Appointment>

    @GET("rest/barbers/appointmentsview/{token}-{day}-{month}-{year}")
    suspend fun getBarberFullAppointments(
        @Path("token") token: String,
        @Path("day") day: String,
        @Path("month") month: String,
        @Path("year") year: String
    ): List<Appointment>?

    @GET("rest/barbers/schedule/{id}")
    suspend fun getBarberSchedule(@Path("id") barberId: Int): MutableList<Schedule>

    @POST("rest/appointments")
    suspend fun setAppointment(@Body apt: Appointment): Int

    @GET("rest/appointments/recordpayment/{token}-{aptid}-{shopid}-{amt}")
    suspend fun recordPayment(
        @Path("token") token: String,
        @Path("aptid") appointmentId: Int,
        @Path("shopid") shopId: Int,
        @Path("amt") amount: Int
    )

    @GET("rest/appointments/conflicts/{barberId}-{date}-{in}-{out}")
    suspend fun checkConflict(
        @Path("barberId") barberId: Int,
        @Path("date") date: String,
        @Path("in") timeIn: String,
        @Path("out") timeOut: String
    ): Int

    @POST("rest/register")
    suspend fun registerUser(@Body signUp: SignUpViewModel): Int

    @GET("rest/appointments/customer/{token}-{type}")
    suspend fun getCustomerAppointments(
        @Path("token") token: String,
        @Path("type") type: Int
    ):List<Appointment>

    @GET("rest/barbers/wage/{token}-{year}-{month}")
    suspend fun getEmployeeWage(
        @Path("token") token: String,
        @Path("year") year: Int,
        @Path("month") monthIndex: Int
    ): Float

    @GET("rest/appointments/cancel/{token}-{id}")
    suspend fun cancelAppointment(
        @Path("token") token: String,
        @Path("id") appointmentId: Int
    ): String

    @GET("rest/hairstyles")
    suspend fun getHairstyles(): List<HairStyle>

    @GET("rest/hairstyles/{id}")
    suspend fun getHairstyle(@Path("id") id: Int): HairStyle

    @GET("hairstyles/descriptions/{name}.json")
    suspend fun getHairstyleDescription(@Path("name") name: String): String

    @GET("http://worldtimeapi.org/api/timezone/Asia/Manila")
    suspend fun getDateTime(): DateTime
}

object ApcService {
    val retrofitService: ApcServiceInterface by lazy {
        retrofit.create(ApcServiceInterface::class.java)
    }
}