package com.hu.xiaobai.kprojectview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hu.xiaobai.view.ItemView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       val view : ItemView =  findViewById(R.id.item_view)
        view.setOnItemClickListener(object : ItemView.OnItemClickListener {
            override fun onItemClick(view:View){
                Toast.makeText(view.context,"点击了我",Toast.LENGTH_LONG).show()
            }
        })
    }
}
