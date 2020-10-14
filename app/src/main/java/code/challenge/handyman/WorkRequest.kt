package code.challenge.handyman

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import code.challenge.handyman.models.HandyMan
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkRequest (context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters){

    override fun doWork(): Result {

        val database = HandyDatabase.getDatabase(applicationContext)
        val dao = database.handyDao()

        val dbList = dao.getOrderedHandyMen()
        val remoteList = arrayListOf<HandyMan>()

        val service = CeleroService.create().listMen()
        service.enqueue(object : Callback<List<HandyMan>> {
            override fun onResponse(call: Call<List<HandyMan>>, response: Response<List<HandyMan>>) {

                for (item in response.body()!!){
                    //Add to the database regardless if current content exists
                    //As we want to update current content anyway
                    val job = SupervisorJob()
                    val uiScope = CoroutineScope(Dispatchers.IO + job)

                    uiScope.launch {
                        dao.insert(item)
                        println(item.identifier)
                    }

                    //This list will serve as a check down below
                    remoteList.add(item)
                }
            }

            override fun onFailure(call: Call<List<HandyMan>>, t: Throwable) {

            }
        })

        //filter through both lists to check to see if an item needs to be deleted on the local database
        val difference = dbList.filter { !remoteList.contains(it) }
        for (item in difference){
            dao.delete(item.identifier)
        }

        return Result.success()
    }
}