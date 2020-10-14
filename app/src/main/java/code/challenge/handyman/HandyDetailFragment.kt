package code.challenge.handyman

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import code.challenge.handyman.models.HandyMan
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_handy_detail.*

private const val ARG_PARAM1 = "param1"

class HandyDetailFragment : Fragment(R.layout.fragment_handy_detail) {
    private var param1: HandyMan? = null
    lateinit var database: HandyDatabase
    lateinit var dao: HandyDao
    lateinit var list: List<HandyMan>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Picasso.get().load(param1!!.profilePicture.large).into(imageView)

        name.text = param1?.name
        phoneNumber.text = param1?.phoneNumber
        state.text = param1?.location?.address?.state
        city.text = param1?.location?.address?.city + ","
        street.text = param1?.location?.address?.street
        postalCode.text = param1?.location?.address?.postalCode
        country.text = param1?.location?.address?.country
        serviceReason.text = param1?.serviceReason

        val pictureSplit = param1?.problemPictures
        if(param1!!.problemPictures.isNotEmpty()) {
            for (pic in param1!!.problemPictures) {
                val imageView = ImageView(context)
                imageView.adjustViewBounds = true
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.maxHeight = 500
                Picasso.get().load(pic).into(imageView)
                problemPictures.addView(imageView)
            }
        }

        mapsIntent.setOnClickListener{
            val gMapsIntentURI = Uri.parse("http://maps.google.com/maps?daddr=${param1!!.location.coordinate.latitude},${param1!!.location.coordinate.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gMapsIntentURI)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(requireContext().packageManager)?.let {
                startActivity(mapIntent)
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: HandyMan) =
            HandyDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}