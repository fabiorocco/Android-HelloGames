package com.example.rocco.hellogames

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.activity_description.*


class DescriptionActivity : AppCompatActivity() {
    var game = Games()

    val baseURL = "https://androidlessonsapi.herokuapp.com/api/"

    val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

    val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(jsonConverter)
        .build()

    val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)
    val callback = object : Callback<Games> {
        override fun onFailure(call: Call<Games>?, t: Throwable?) {
            // Code here what happens if calling the WebService fails
            Log.d("TAG", "WebService call failed")
        }
        override fun onResponse(call: Call<Games>?,
                                response: Response<Games>?) {
            // Code here what happens when WebService responds
            if (response != null) {
                Log.d("TAG", response.toString())
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        game = responseData
                        textViewName.text = game.name
                        textViewType.text = game.type
                        textViewNbPlayers.text = game.players.toString()
                        textViewYear.text = game.year.toString()

                        textViewDescription.text = game.description_en
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val gameId = intent.getIntExtra("gameId", -1)

        service.DescGame(gameId).enqueue(callback)

        Log.d("TAG", "id :")
    }
}