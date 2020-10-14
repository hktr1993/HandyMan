package code.challenge.handyman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import code.challenge.handyman.models.HandyMan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.handy_item.view.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFragment : Fragment(R.layout.fragment_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = listOf<HandyMan>()
        val database = HandyDatabase.getDatabase(context)
        val dao = database.handyDao()
        val service = CeleroService.create().listMen()

        val job = SupervisorJob()
        val uiScope = CoroutineScope(Dispatchers.IO + job)

        rv.layoutManager = LinearLayoutManager(context)

        uiScope.launch {

            val listCheck = dao.getOrderedHandyMen().size
            println(dao.getOrderedHandyMen().size)
            if(listCheck > 0) {
                list = dao.getOrderedHandyMen()
                withContext(Dispatchers.Main){ rv.adapter = HandyManAdapter(list)}
            } else {
                //A network request will be made if this is the first time a user has opened the app
                //Otherwise data will just come from the local database
                service.enqueue(object : Callback<List<HandyMan>> {
                    override fun onResponse(
                        call: Call<List<HandyMan>>,
                        response: Response<List<HandyMan>>
                    ) {
                        list = response.body()!!
                        uiScope.launch {

                            for (item in response.body() as List<HandyMan>){
                                println("idd "+ item.identifier)
                                dao.insert(item)
                            }
                        }

                        rv.adapter = HandyManAdapter(response.body())
                    }

                    override fun onFailure(call: Call<List<HandyMan>>, t: Throwable) {
                        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                        println(t.message)
                    }
                })
            }
        }

        ItemClickSupport.addTo(rv).setOnItemClickListener { recyclerView, position, v ->
            val detailFragment = HandyDetailFragment.newInstance(list[position])
            fragmentManager!!.beginTransaction()
                .replace(R.id.fragmentContent, detailFragment)
                .addToBackStack(null)
                .commit()
        }

    }

    class HandyManAdapter(private val dates: List<HandyMan?>?) : RecyclerView.Adapter<HandyManAdapter.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.handy_item, parent, false))
        }

        override fun getItemCount(): Int {
            return dates!!.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = dates?.get(position)
            holder.itemView.name.text = item!!.name
            holder.itemView.phoneNumber.text = item.phoneNumber
            Picasso.get().load(item.profilePicture.medium).into(holder.itemView.img)
        }

        inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    }
}