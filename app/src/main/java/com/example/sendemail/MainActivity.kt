package com.example.sendemail

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    companion object {
        val SP_ADDRESS = "sp_email"
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission()
        }

        //稍微做一下保活
        val component = ComponentName(packageName, "com.example.sendemail.MailService")
        packageManager.setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)

        initView()
        initListener()

    }

    /**
     * 初始化按钮点击事件
     */
    private fun initListener() {
        button.setOnClickListener {
            changeEmailAddress()
        }
    }

    /**
     * 初始化视图，读取sp
     */
    private fun initView() {
        email.setText(sharedPreferences.getString(SP_ADDRESS, ""))
    }

    /**
     * 请求相关权限
     */
    private fun requestPermission() {

        if (ContextCompat.checkSelfPermission(application, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            val permissionArray = arrayOf(Manifest.permission.RECEIVE_SMS)
            ActivityCompat.requestPermissions(this, permissionArray, 0)
        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        startActivity(intent)
    }

    /**
     * 更改邮箱地址
     */
    private fun changeEmailAddress() {
        sharedPreferences.edit().putString(SP_ADDRESS, email.text.toString()).apply()
    }

}
