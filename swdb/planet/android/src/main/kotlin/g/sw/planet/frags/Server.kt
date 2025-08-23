package g.sw.planet.frags

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

import g.sw.planet.R

class Server : Fragment()
{
    val SP_SERVER = "server"

    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_server, container, false).apply {
            sp = context?.getSharedPreferences("server", Context.MODE_PRIVATE)!!
            editor = sp.edit()

            val serverInput = findViewById<EditText>(R.id.server_input)
            serverInput.setText(sp.getString(SP_SERVER, "") ?: "")

            findViewById<Button>(R.id.server_input_confirm).setOnClickListener {
                editor.putString(SP_SERVER, serverInput.text.toString())
                editor.commit()
                Toast.makeText(context, R.string.server_saved, Toast.LENGTH_SHORT).show()
            }
        }
}
