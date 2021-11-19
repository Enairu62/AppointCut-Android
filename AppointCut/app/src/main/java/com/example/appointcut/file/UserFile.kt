package com.example.appointcut.file

import DataModels.User.Companion.AuthStatus
import DataModels.User
import android.R
import android.content.Context
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.OutputStreamWriter

object UserFile {
    private const val fileName = "user"

    /**
     * saves to file the user data
     * @param context context of the activity
     * @param user user to be written to file
     */
    fun save(context: Context, user: User):Boolean{
        //convert to json
        val json = JSONObject()
        user.apply {
            json.put("authStatus",authStatus)
            json.put("firstName",firstName)
            json.put("lastName",lastName)
            json.put("token",token)
        }
        //convert to string
        val jsonString = json.toString()
        try{
            //write string to file
            OutputStreamWriter(context.openFileOutput(fileName,Context.MODE_PRIVATE)).use {
                it.write(jsonString)
                Log.d("UserFile", jsonString)
                it.close()
                return true
            }
        }catch (e: IOException){
            Log.e("Token","Unable to write to token file", e)
            return false
        }
    }

    /**
     * retrieves saved user data
     * @param context context of the activity
     * @return User data saved in file
     */
    fun read(context: Context):User?{
        var user: User?
        try{
            //get the file
            val line = context.openFileInput(fileName).bufferedReader().readLine()
            //convert file to json
            val json = JSONObject(line)
            //set authStatus
            lateinit var authStatus: AuthStatus
            when(json.get("authStatus")){
                "BARBER" -> authStatus = AuthStatus.BARBER
                "CUSTOMER" -> authStatus = AuthStatus.CUSTOMER
            }
            //create the user class
            user = User(authStatus,json.getString("firstName"),
                json.getString("lastName"),json.getString("token"))
        }catch (e: FileNotFoundException){
            Log.d("UserFile", "File not found while trying to read user data")
            user = null
        }catch (e: Exception){
            Log.e("UserFile", "error reading user $e")
            user = null
        }
        return user
    }

    /**
     * deletes user data saved in file
     * @param context current activity context
     * @returns true if the data has been deleted, false otherwise
     */
    fun delete(context: Context): Boolean{
        Log.d("UserFile", "Deleting saved user data")
        return File(context.filesDir, fileName).delete()
    }
}