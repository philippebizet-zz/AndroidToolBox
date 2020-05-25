package fr.isen.bizet.androidtoolbox

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bluetooth_scan.*

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class BluetoothActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private var mScanning: Boolean = false
    private lateinit var adapter: DeviceRecyclerView
    private val devices = ArrayList<ScanResult>()



    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val isBLEEnabled: Boolean
        get() = bluetoothAdapter?.isEnabled == true

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_scan)

        bleTextFailed.visibility = View.GONE

        searchButton.setOnClickListener {
            when {
                isBLEEnabled -> {
                    //init scan
                    if (textView12.text == "Start scan") {
                        searchButton.setImageResource(android.R.drawable.ic_media_pause)
                        textView12.text = "Scan en cours "
                        initBLEScan()
                        initScan()
                    } else if (textView12.text == "Scan en cours ...") {
                        searchButton.setImageResource(android.R.drawable.ic_media_play)
                        textView12.text = "Start Scan "
                        progressBar.visibility = View.INVISIBLE
                        dividerBle.visibility = View.VISIBLE
                    }
                }
                bluetoothAdapter != null -> {
                    //ask for permission
                    val enableBTIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT)
                }
                else -> {
                    //device is not compatible with your device
                    bleTextFailed.visibility = View.VISIBLE
                }
            }
        }
        deviceListRV.adapter = DeviceRecyclerView(devices, ::onDeviceClicked)
        deviceListRV.layoutManager = LinearLayoutManager(this)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initScan() {
        progressBar.visibility = View.VISIBLE
        dividerBle.visibility = View.GONE

        handler = Handler()
        scanLeDevice(true)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun scanLeDevice(enable: Boolean) {
        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if (enable) {
                Log.w("BLE", "Scanning for devices")
                handler.postDelayed({
                    mScanning = false
                    stopScan(leScanCallback)
                }, SCAN_PERIOD)
                mScanning = true
                startScan(leScanCallback)
                adapter.clearResults()
                adapter.notifyDataSetChanged()
            } else {
                mScanning = false
                stopScan(leScanCallback)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val leScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.w("BLE", "${result.device}")
            runOnUiThread {
                adapter.addDeviceToList(result)
                adapter.notifyDataSetChanged()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initBLEScan() {
        adapter = DeviceRecyclerView(
            arrayListOf(),
            ::onDeviceClicked
        )
        deviceListRV.adapter = adapter
        deviceListRV.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        handler = Handler()

        scanLeDevice(true)
        deviceListRV.setOnClickListener {
            scanLeDevice(!mScanning)
        }
    }

    private fun onDeviceClicked(device: BluetoothDevice) {
        Toast.makeText(this, "Connected to the device", Toast.LENGTH_SHORT).show()
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStop() {
        super.onStop()
        scanLeDevice(false)
    }

    companion object {
        private const val SCAN_PERIOD: Long = 60000
        private const val REQUEST_ENABLE_BT = 44
    }
}