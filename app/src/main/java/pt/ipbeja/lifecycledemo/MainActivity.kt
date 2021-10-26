package pt.ipbeja.lifecycledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import pt.ipbeja.lifecycledemo.utils.TAG

class MainActivity : AppCompatActivity() {

    // Reference: https://developer.android.com/reference/android/app/Activity.html

    private var job: Job? = null // for demo purposes. ignore

    private lateinit var counterView: TextView
    private var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        // Activity is being created but still not visible
        // Called when the activity is first created. This is where you should do all of your normal
        // static set up: create views, bind data to lists, etc. This method also provides you with a Bundle containing the activity's previously frozen state, if there was one.
        // Always followed by onStart().
        Log.i(TAG, "Lifecycle: onCreate called. Bundle available = ${savedInstanceState != null}")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupButtons()
    }

    override fun onStart() {
        // Activity is now visible but still not fully running
        // Called when the activity is becoming visible to the user.
        // Followed by onResume() if the activity comes to the foreground, or onStop() if it becomes
        // hidden

        // Could be used to start services or task (eg. GPS, listening to a web socket, etc.)


        super.onStart()
        Log.i(TAG, "Lifecycle onStart called")
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Invoked if a bundle exists (data registered on onSaveInstanceState)
        // Invoked between onStart and onResume lifecycle callbacks
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "Lifecycle onRestoreInstanceState called")
        this.counter = savedInstanceState.getInt("counter", 0)
        this.counterView.text = "$counter"
    }


    override fun onResume() {
        // Activity is running and visible and ready to interact with

        // Called when the activity will start interacting with the user. At this point your
        // activity is at the top of its activity stack, with user input going to it.
        // Always followed by onPause().

        super.onResume()
        Log.i(TAG, "Lifecycle onResume called")
        job?.cancel() // for demo purposes. ignore
    }

    override fun onPause() {
        // Activity is transitioning to a background state but still visible

        // Called when the activity loses foreground state, is no longer focusable or before
        // transition to stopped/hidden or destroyed state. The activity is still visible to user,
        // so it's recommended to keep it visually active and continue updating the UI.
        // Implementations of this method must be very quick because the next activity will not be
        // resumed until this method returns.
        // Followed by either onResume() if the activity returns back to the front, or onStop() if
        // it becomes invisible to the user.

        Log.i(TAG, "Lifecycle onPause called")
        super.onPause()
    }

    override fun onStop() {
        // Activity is transitioning to a stopped state and is no longer visible
        // Called when the activity is no longer visible to the user. This may happen either because
        // a new activity is being started on top, an existing one is being brought in front of this
        // one, or this one is being destroyed. This is typically used to stop animations and
        // refreshing the UI, etc.
        // Followed by either onRestart() if this activity is coming back to interact with the user,
        // or onDestroy() if this activity is going away.

        // Could be used to stop services to avoid wasting battery

        Log.i(TAG, "Lifecycle onStop called")
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save data we want to persist if this Activity is destroyed (on rotation, low memory, etc)
        // Invoked between onStop and onDestroy lifecycle callbacks unless the Activity was closed
        // by the user (eg. back pressed)
        Log.i(TAG, "Lifecycle onSaveInstanceState called.")
        super.onSaveInstanceState(outState)
        outState.putInt("counter", counter)
    }

    override fun onDestroy() {
        // The final call you receive before your activity is destroyed. This can happen either
        // because the activity is finishing (someone called Activity#finish on it), or because the
        // system is temporarily destroying this instance of the activity to save space. You can
        // distinguish between these two scenarios with the isFinishing() method.

        Log.i(TAG, "Lifecycle onDestroy called")
        super.onDestroy()
    }


    private fun setupButtons() {

        this.counterView = findViewById(R.id.counter)

        findViewById<Button>(R.id.increment).apply {
            setOnClickListener {
                incrementCounter()
            }
        }


        findViewById<Button>(R.id.second).apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            }
        }


        findViewById<Button>(R.id.transparent).apply {
            setOnClickListener {
                startActivity(Intent(this@MainActivity, TransparentActivity::class.java))

                // for demo purposes. ignore
                this@MainActivity.job = lifecycleScope.launch {
                    while (true) {
                        delay(1000)
                        if (job?.isActive == false) continue
                        incrementCounter()
                    }
                }
                // -------------------
            }
        }
    }

    private fun incrementCounter() {
        counter++
        counterView.text = "$counter"
    }

}