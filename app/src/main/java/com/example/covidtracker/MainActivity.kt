package com.example.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    lateinit var countryCasesTV:TextView
    lateinit var countryRecoveredTV:TextView
    lateinit var countryDeathTV:TextView
    lateinit var stateRV:RecyclerView
    lateinit var stateRVAdapter: StateRVAdapter
    lateinit var stateList: List<StateModal>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countryCasesTV=findViewById(R.id.idTVCases)
        countryRecoveredTV=findViewById(R.id.idRevState)
        countryDeathTV=findViewById(R.id.idDeath)
        stateRV=findViewById(R.id.idRvStates)
        stateList=ArrayList<StateModal>()
        getStateInfo()
    }

    private fun getStateInfo(){
        val url ="https://api.rootnet.in/covid19-in/stats/latest"
        val queue = Volley.newRequestQueue(this@MainActivity)
        val request =
            JsonObjectRequest(Request.Method.GET,url,null,{ response ->
                try {
                    val dataObj = response.getJSONObject( "data")
                    val summaryObj = dataObj.getJSONObject("summary")
                    val cases:Int = summaryObj.getInt("total")
                    val recovered:Int = summaryObj.getInt("discharged")
                    val deaths:Int = summaryObj.getInt("deaths")

                    countryCasesTV.text=cases.toString()
                    countryRecoveredTV.text=recovered.toString()
                    countryDeathTV.text=deaths.toString()

                    val regionalArray = dataObj.getJSONArray("regional")
                    for (i in 0 until regionalArray.length()){
                        val  regionalObj = regionalArray.getJSONObject(i)
                        val stateName: String = regionalObj.getString("loc")
                        val cases:Int = regionalObj.getInt("totalConfirmed")
                        val deaths:Int = regionalObj.getInt("deaths")
                        val recovered: Int = regionalObj.getInt("discharged")

                        val stateModal = StateModal(stateName,recovered,deaths,cases)
                        stateList=stateList+stateModal

                    }
                    stateRVAdapter= StateRVAdapter(stateList)
                    stateRV.layoutManager = LinearLayoutManager(this)
                    stateRV.adapter = stateRVAdapter



                }catch (e: JSONException){
                    e.printStackTrace()
                }
            },{ error ->
                {
                Toast.makeText(this,"Fail to get data",Toast.LENGTH_SHORT).show()
            }
            })
        queue.add(request)

    }
}