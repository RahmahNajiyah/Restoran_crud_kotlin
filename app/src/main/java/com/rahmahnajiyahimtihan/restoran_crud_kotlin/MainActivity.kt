package com.rahmahnajiyahimtihan.restoran_crud_kotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.rahmahnajiyahimtihan.restoran_crud_kotlin.adapter.MakananAdapter
import com.rahmahnajiyahimtihan.restoran_crud_kotlin.response.ResponseMakanan
import com.rahmahnajiyahimtihan.restoran_crud_kotlin.server.ConfigServer
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ambil data
        //make new method
        ambilData()
    }

    private fun ambilData() {
        ConfigServer().service.requestgetMakanan().enqueue(object : Callback<ResponseMakanan>{
            override fun onFailure(call: Call<ResponseMakanan>?, t: Throwable?) {
                toast(t.toString())
            }

            override fun onResponse(call: Call<ResponseMakanan>?, response: Response<ResponseMakanan>?) {
                if (response?.isSuccessful!!){
                    val pesan = response.body()?.pesan
                    val status = response.body()?.status

                    val data = response.body()?.datanya

                    val adapter = MakananAdapter(data!!)

                    recylerView.adapter = adapter
                    recylerView.layoutManager = LinearLayoutManager(this@MainActivity)

                    Log.d("data json makan", data.toString())
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.tambah -> startActivity(Intent(this, TambahActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }
}
