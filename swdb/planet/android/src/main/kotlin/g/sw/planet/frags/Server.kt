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
    val SP_USERNAME = "username"
    val SP_PASSWORD = "password"

    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_server, container, false).apply {
            sp = context?.getSharedPreferences("server", Context.MODE_PRIVATE)!!
            editor = sp.edit()

            val star = findViewById<EditText>(R.id.server_star)
            val username = findViewById<EditText>(R.id.server_username)
            val password = findViewById<EditText>(R.id.server_password)
            star.setText(sp.getString(SP_SERVER, "") ?: "")
            username.setText(sp.getString(SP_USERNAME, "") ?: "")
            password.setText(sp.getString(SP_PASSWORD, "") ?: "")

            findViewById<Button>(R.id.server_input_confirm).setOnClickListener {
                editor.putString(SP_SERVER, star.text.toString())
                editor.putString(SP_USERNAME, username.text.toString())
                editor.putString(SP_PASSWORD, password.text.toString())
                editor.commit()
                Toast.makeText(context, R.string.server_saved, Toast.LENGTH_SHORT).show()
            }
        }
}
