package com.project.cointerest

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.chart_view.*


class ChartView : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.project.cointerest.R.layout.chart_view)

        chart_webview.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
        }
        var str = intent.getStringExtra("coin")
        println(str)
        chart_name.text = str
        chart_webview.loadUrl("http://3.35.174.63/chart.php?coin=${str}")
        back_btn.setOnClickListener {
            finishAndRemoveTask()
        }
    }
}