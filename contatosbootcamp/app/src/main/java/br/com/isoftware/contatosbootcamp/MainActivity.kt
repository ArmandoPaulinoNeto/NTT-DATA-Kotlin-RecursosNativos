package br.com.isoftware.contatosbootcamp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    val REQUEST_CONTECT = 0
    //LinearLayoutManager
    val LINAER_LAYOUT_VERTICAL = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CONTECT
            )

        }else{
            setContects()
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CONTECT) setContects()
    }
    private fun setContects() {
        val contactList: ArrayList<Contact> = ArrayList()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        null)

        if(cursor != null) {
            while (cursor.moveToNext()) {
                contactList.add(
                    Contact(
                        cursor.getString(
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                        ),
                        cursor.getString(
                            cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        )
                    )
                )
            }
            cursor.close()
            val adapter = ContactsAdapter(contactList)
            val contactsRecyclerView = findViewById<RecyclerView>(R.id.contacts_recycler_view)

            contactsRecyclerView.layoutManager = LinearLayoutManager(this, LINAER_LAYOUT_VERTICAL, false)
            contactsRecyclerView.adapter = adapter
        }
    }
}