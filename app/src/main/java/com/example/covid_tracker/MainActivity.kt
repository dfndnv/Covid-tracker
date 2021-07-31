package com.example.covid_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var worldcasesTV:TextView
    lateinit var worldrecoveredTV:TextView
    lateinit var worlddeathsTV:TextView
    lateinit var countrycasesTV:TextView
    lateinit var countryrecoveredTV:TextView
    lateinit var countryrdeathsTV:TextView
    lateinit var stateRV:RecyclerView
    lateinit var stateRVadaptor: stateRVadaptor
    lateinit var stateList: List<StateModal>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        worldcasesTV = findViewById(R.id.idTVworldcases)
        worldrecoveredTV = findViewById(R.id.idTVworldrecovered)
        worlddeathsTV = findViewById(R.id.idTVworldDeaths)
        countrycasesTV = findViewById(R.id.idTVindiacases)
        countryrecoveredTV = findViewById(R.id.idTVindiarecovered)
        countryrdeathsTV = findViewById(R.id.idTVindiaDeaths)
        stateRV = findViewById(R.id.idRVstates)
        stateList = ArrayList<StateModal>()
        getstateinfo()
        getworldinfo()


    }
    private fun getstateinfo(){
        val url =  "https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this)
        val JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
            { response ->
                try{
                    val datobj=response.getJSONObject("data")
                    val summaryobj = datobj.getJSONObject("summary")
                    val cases:Int = summaryobj.getInt("total")
                    val recovered:Int = summaryobj.getInt("discharged")
                    val deaths:Int = summaryobj.getInt("deaths")

                    countrycasesTV.text = cases.toString()
                    countryrecoveredTV.text = recovered.toString()
                    countryrdeathsTV.text = deaths.toString()

                    val regionalArray = datobj.getJSONArray("regional")
                    for (i in 0 until regionalArray.length()){
                        val regionalObj = regionalArray.getJSONObject(i)
                        val stateName:String = regionalObj.getString("loc")
                        val cases:Int = regionalObj.getInt("totalConfirmed")
                        val deaths:Int = regionalObj.getInt("deaths")
                        val recovered:Int = regionalObj.getInt("discharged")

                        val statemodal = StateModal(stateName,recovered,deaths, cases)
                        stateList = stateList+statemodal
                    }
                    stateRVadaptor = stateRVadaptor(stateList)
                    stateRV.layoutManager = LinearLayoutManager(this)
                    stateRV.adapter = stateRVadaptor

                }catch (e:JSONException){
                    e.printStackTrace()
                }

            },
            { })

        queue.add(JsonObjectRequest)

    }
    private fun getworldinfo(){
        val url = "https://corona.lmao.ninja/v3/covid-19/all"
        val queue = Volley.newRequestQueue(this)
        val JsonObjectRequest =
            JsonObjectRequest(Request.Method.GET,url,null,{ response->
                try {
                    val worldCases: Int = response.getInt("cases")
                    val worldRecovered: Int = response.getInt("recovered")
                    val worldDeaths: Int = response.getInt("deaths")
                    worldrecoveredTV.text = worldRecovered.toString()
                    worldcasesTV.text = worldCases.toString()
                    worlddeathsTV.text = worldDeaths.toString()
                } catch (e:JSONException){
                    e.printStackTrace()
                }

            },
                { })
        queue.add(JsonObjectRequest)

    }
}