package online.appointcut.models

import com.squareup.moshi.Json

class EmployeeSpecialization(
    @Json(name = "EmployeeSpecializationID") val id: Int,
    @Json(name = "shopServicesID") val shopServiceId: Int,
    @Json(name = "employeeID") val employeeId: Int
)