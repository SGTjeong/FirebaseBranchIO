package com.example.firebasebranchio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.branch.referral.Branch
import io.branch.referral.validators.IntegrationValidator

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        IntegrationValidator.validate(this)

        Branch.sessionBuilder(this).withCallback { referringParams, error ->
            Log.e(MY_TAG, "onInitFinished")
            if (error == null) {
                val path = referringParams?.getString("\$deeplink_path")
                Log.e(MY_TAG, "success : $path")
            } else {
                Log.e(MY_TAG, "error : ${error.message}")
            }
        }.withData(this.intent?.data).init()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.e(MY_TAG, "onNewIntent")
        this.intent = intent

        Branch.sessionBuilder(this).withCallback { referringParams, error ->
            Log.e(MY_TAG, "onInitFinished")
            if (error == null) {
                val path = referringParams?.getString("\$deeplink_path")
                Log.e(MY_TAG, "success : $path")
            } else {
                Log.e(MY_TAG, "error : ${error.message}")
            }
        }.reInit()
    }
}