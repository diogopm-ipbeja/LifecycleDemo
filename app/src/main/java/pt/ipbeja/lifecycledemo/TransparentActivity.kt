package pt.ipbeja.lifecycledemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import pt.ipbeja.lifecycledemo.utils.TAG

class TransparentActivity : AppCompatActivity() {
    

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "Lifecycle: onCreate called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transparent)
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "Lifecycle onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "Lifecycle onResume called")
    }

    override fun onPause() {
        Log.i(TAG, "Lifecycle onPause called")
        super.onPause()
    }

    override fun onStop() {
        Log.i(TAG, "Lifecycle onStop called")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, "Lifecycle onDestroy called")
        super.onDestroy()
    }

}