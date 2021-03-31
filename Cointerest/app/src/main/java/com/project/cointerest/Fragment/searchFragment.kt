package com.project.cointerest.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.cointerest.Adapter.SearchFragmentRecyclerAdapter
import com.project.cointerest.CoinData
import com.project.cointerest.R
import kotlinx.android.synthetic.main.fragment_search.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException


class searchFragment : Fragment() {

    var coin_list_KRW = arrayListOf<CoinData>()
    var coin_list_BTC = arrayListOf<CoinData>()
    var coin_list_ALL = arrayListOf<CoinData>()
    lateinit var My_recyclerView : RecyclerView



    fun JsonMake(){

        println("데어터를 가져 오는 중...")
        //val url = "http://3.35.174.63/market.php"
        val url = "https://api.upbit.com/v1/market/all"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val js = response?.body()?.string()
                println(js)
                //baseTextView.text = body
                try {
                    println("체크11111")
                    val coinInfo = JSONArray(js)

                    //val jsonArray = coinInfo.optJSONArray("trade_price")


                    var i = 0
                    while (i < coinInfo.length()) {
                        val jsonObject = coinInfo.getJSONObject(i)
                        val market_name = jsonObject.getString("market")
                        val korean_name = jsonObject.getString("korean_name")
                        //val english_name = jsonObject.getString("english_name")
                        val arr = market_name.split("-")

                        println("체크메이드")
                       // println(coin_list[i].kor_name)
                        if(arr[0] == "KRW"){
                            coin_list_KRW.add(CoinData(korean_name, "", arr[1], arr[0]))
                        }
                        else if(arr[0] == "BTC"){
                            coin_list_BTC.add(CoinData(korean_name, "", arr[1], arr[0]))
                        }
                        coin_list_ALL.add(CoinData(korean_name, "", arr[1], arr[0]))
                        i++
                    }
                } catch (e: JSONException) {
                    println("error")
                    println(e.printStackTrace())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Request Fail")
            }
        })
        return
    }


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        JsonMake()

/*        search_KRW_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                println("!!!")
            }
            else{
                println("@@@")
            }
        }*/

        var rootView = inflater.inflate(R.layout.fragment_search, container, false)
        My_recyclerView = rootView.findViewById(R.id.search_content_view!!)as RecyclerView
        My_recyclerView.layoutManager = LinearLayoutManager(requireContext())
        My_recyclerView.adapter = SearchFragmentRecyclerAdapter(requireContext(),coin_list_ALL)

        return rootView







    }




}