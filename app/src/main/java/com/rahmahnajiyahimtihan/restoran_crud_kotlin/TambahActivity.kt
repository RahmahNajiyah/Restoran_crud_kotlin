package com.rahmahnajiyahimtihan.restoran_crud_kotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.rahmahnajiyahimtihan.restoran_crud_kotlin.response.ResponseInsert
import com.rahmahnajiyahimtihan.restoran_crud_kotlin.server.ConfigServer
import kotlinx.android.synthetic.main.activity_tambah.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TambahActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            R.id.insert -> insertServer()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun insertServer() {

        // checking
        //ambil inputan user

        val et_nama = et_Name.text.toString()
        val et_harga = et_Price.text.toString()
        val et_restoran = et_Restoran.text.toString()

        //check inputan user
        if (et_nama == "" && et_harga == "" && et_restoran == ""){

            Toast.makeText(this, "tidak blh kosong", Toast.LENGTH_LONG).show()
        }
        else{
            //insert server

            //ambil configurasi
            val config = ConfigServer().service

            //ambil request
            val request = config.requestInsert(et_nama, et_harga, et_restoran)

            //get response
            request.enqueue(object : Callback<ResponseInsert>{
                override fun onFailure(call: Call<ResponseInsert>?, t: Throwable?) {

                    Toast.makeText(this@TambahActivity,t.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<ResponseInsert>?, response: Response<ResponseInsert>?) {

                    //eksekusi response
                    if (response?.isSuccessful!!){//klo null save gk kuat 1 pake ? maka tambah dgn !!

                        val status = response.body()?.status
                        val pesan  = response.body()?.pesan

                        if (status == 1){
                            //pindah Main activity
                            startActivity(Intent(this@TambahActivity,MainActivity::class.java))
                        }
                        else{
                            toast("pesan")
                        }
                    }

                }
            })

        }
    }
}
