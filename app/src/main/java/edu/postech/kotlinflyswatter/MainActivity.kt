package edu.postech.kotlinflyswatter

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.math.abs
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var mSensorManager: SensorManager
    private lateinit var mAccel: Sensor

    private var mSensing = false

    private lateinit var mSensorEventListener: SensorEventListener

    private lateinit var mWorkerThread: HandlerThread
    private lateinit var mHandlerWorker: Handler

    // new variables for FlySwatter
    private lateinit var flyCanvas: FrameLayout
    private val flies: Queue<ImageView> = LinkedList()

    private lateinit var flyTimer: Timer
    private var previousValues = floatArrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSensorManager  = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccel          = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mWorkerThread   = HandlerThread("Worker Thread")
        mWorkerThread.start()

        mHandlerWorker = Handler(mWorkerThread.looper)

        // initialize new variable
        flyCanvas = findViewById(R.id.flyCanvas)

        findViewById<Button>(R.id.buttonStartStop).setOnClickListener {
            mSensing = !mSensing
            if (mSensing) {
                startSensing()
                findViewById<Button>(R.id.buttonStartStop).text = "Stop"
            } else {
                stopSensing()
                findViewById<Button>(R.id.buttonStartStop).text = "Start"
            }
        }
    }

    fun startSensing() {
        // C. create a fly repeatedly, with 300ms interval
        // Guide: also, use Timer and TimerTask, but save the timer to later cancel the task

        // D. destroy a fly when there is a significant diff between consecutive ACC values
        mSensorEventListener = object: SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                    val ts = event.timestamp
                    val values = event.values.clone()
                    // Step 1. calculate diff with previous acc data, if possible
                    // Step 2. if diff is bigger than 40, destroy a fly

                    // Step 3. save current values to `previousValues`

                }
            }

            override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }
        }
        mSensorManager.registerListener(mSensorEventListener, mAccel, 100 * 1000, mHandlerWorker)
    }

    fun stopSensing() {
        // E. stop creating a fly
        // Guide: cancel the timer

        mSensorManager.unregisterListener(mSensorEventListener, mAccel)
    }

    // A. define createFly()
    fun createFly() {
        val pixels = resources.displayMetrics.density.toInt()
        val canvasHeight = flyCanvas.height
        val canvasWidth = flyCanvas.width

        // Step 1. create an ImageView and set its size, position in the flyCanvas
        // Guide: the ImageView size should be 40dp
        // Guide: use apply()

        // Step 2. inject the view to the canvas and add it to the Queue
        // Guide: use synchronized when accessing the Queue

    }

    // B. define destroyFly()
    fun destroyFly() {
        // Step 1. check whether Queue is not empty & remove the first ImageView
        // Guide: use synchronized when accessing the Queue

        // Step 2. change the image to fly_catch, and destroy the first ImageView after 500ms
        // Guide: use Timer and TimerTask
    }
}
