package org.tensorflow.lite.examples.objectdetection.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import org.tensorflow.lite.examples.objectdetection.R
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentCameraBinding
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentProductResultBinding
import org.tensorflow.lite.examples.objectdetection.network.WebService
import org.tensorflow.lite.examples.objectdetection.network.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductResultFragment : Fragment() {


    private var _fragmentResultBinding: FragmentProductResultBinding? = null

    private val fragmentResultBinding
        get() = _fragmentResultBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val args: ProductResultFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentResultBinding = FragmentProductResultBinding.inflate(inflater, container, false)

        return fragmentResultBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentResultBinding.tvSearchResultTitle.text = "Search Result For : ${args.query}"
        search(args.query)
    }

    private fun search(query: String) {
        WebService.getAPI().search(query=query).enqueue(object : Callback<SearchResponse?> {
                override fun onResponse(
                    call: Call<SearchResponse?>,
                    response: Response<SearchResponse?>
                ) {
                    val searchResponse: SearchResponse? = response.body()
                    Log.v("SearchResult","${searchResponse?.images?.size}")
                    if (searchResponse != null) {
                    } else {
                        Toast.makeText(requireContext(),"Connection Failed",Toast.LENGTH_LONG).show()

                    }
                }

                override fun onFailure(call: Call<SearchResponse?>, t: Throwable) {
                    Toast.makeText(requireContext(),"Connection Failed",Toast.LENGTH_LONG).show()
                }
            })

    }
}