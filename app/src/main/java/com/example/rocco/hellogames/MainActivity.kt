package com.example.rocco.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val gamesList = arrayListOf<Games>()

    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"

    val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(jsonConverter)
        .build()

    val explicitIntent = Intent(activity, DescriptionActivity::class.java)

    val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)
    val callback = object : Callback<List<Games>> {
        override fun onFailure(call: Call<List<Games>>?, t: Throwable?) {
            // Code here what happens if calling the WebService fails
            Log.d("TAG", "WebService call failed")
        }
        override fun onResponse(call: Call<List<Games>>?,
                                response: Response<List<Games>>?) {
            // Code here what happens when WebService responds
            if (response != null) {
                Log.d("TAG", response.toString())
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        gamesList.addAll(responseData)
                        textViewTopLeft.text = gamesList[0].name
                        textViewTopRight.text = gamesList[1].name
                        textViewBotLeft.text = gamesList[2].name
                        textViewBotRight.text = gamesList[3].name

                        textViewTopLeft.setOnClickListener {
                            gamesList[0].id
                        }
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        service.ListGames().enqueue(callback)

    }
}
