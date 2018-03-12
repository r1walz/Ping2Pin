package com.sdsmdg.rohit.sms

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MyAdapter
    var myList = ArrayList<List<String>>()
    var ctx: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.READ_SMS), 69)
        else initialise()
    }

    private fun initialise() {
        val cursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null)
        if (cursor.moveToFirst())
            do {
                val title = cursor.getString(2)
                val msgData = cursor.getString(12)
                myList.add(listOf(title, msgData))
            } while (cursor.moveToNext())
        cursor.close()

        addSentBox();

        adapter = MyAdapter(ctx, myList) { it ->
            val pattern = Regex("\\d+\\.\\d+ *, *\\d+\\.\\d+", RegexOption.IGNORE_CASE)
            val matchedValue = pattern.find(it[1])?.value
            if (matchedValue != null) {
                val mapIntent = Intent(this@MainActivity, MapsActivity::class.java)
                val coordinateArray = matchedValue.split(",")
                mapIntent.putExtra("lat", coordinateArray[0].toDouble())
                mapIntent.putExtra("lng", coordinateArray[1].toDouble())
                startActivity(mapIntent)
            }
        }
        msgRV.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        msgRV.layoutManager = layoutManager
        msgRV.setHasFixedSize(true)
    }

    private fun addSentBox() {
        val cursor = contentResolver.query(Uri.parse("content://sms/sent"), null, null, null, null)
        if (cursor.moveToFirst())
            do {
                val title = cursor.getString(2)
                val msgData = cursor.getString(12)
                myList.add(listOf(title, msgData))
            } while (cursor.moveToNext())
        cursor.close()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            69 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                    initialise()
                else
                    Toast.makeText(ctx, resources.getString(R.string.permission), Toast.LENGTH_LONG).show()
            }
        }
    }
}
