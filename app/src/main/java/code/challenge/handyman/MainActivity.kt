package code.challenge.handyman

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContent, ListFragment())
            .addToBackStack(null)
            .commit()

        initiateWorkManager()
    }

    private fun initiateWorkManager() {
        val manager: WorkManager = WorkManager.getInstance(applicationContext)
        val constraints: Constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest: PeriodicWorkRequest = PeriodicWorkRequest.Builder(
            WorkRequest::class.java, 24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        manager.enqueueUniquePeriodicWork("Work", ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }
}