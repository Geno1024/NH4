package g.sw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import g.sw.planet.R

class PlanetActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planet)
    }

    override fun onSupportNavigateUp(): Boolean
    {
        return true
//        val navController = findNavController()
    }
}
