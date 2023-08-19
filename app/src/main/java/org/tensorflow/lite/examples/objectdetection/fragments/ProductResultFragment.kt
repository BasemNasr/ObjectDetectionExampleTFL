package org.tensorflow.lite.examples.objectdetection.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.tensorflow.lite.examples.objectdetection.adapter.SearchResultAdapter
import org.tensorflow.lite.examples.objectdetection.databinding.FragmentProductResultBinding
import org.tensorflow.lite.examples.objectdetection.utils.OnItemClickedGeneric
import org.tensorflow.lite.examples.objectdetection.utils.network.WebService
import org.tensorflow.lite.examples.objectdetection.utils.network.response.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference


class ProductResultFragment : Fragment(), OnItemClickedGeneric<Any> {


    private var _fragmentResultBinding: FragmentProductResultBinding? = null

    private val fragmentResultBinding
        get() = _fragmentResultBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private val args: ProductResultFragmentArgs by navArgs()
    private val resultAdapter by lazy { SearchResultAdapter(WeakReference(this.requireContext())) }


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
        resultAdapter.setOnItemClickListener(this@ProductResultFragment)
        fragmentResultBinding.rvCategories.apply {
            adapter = resultAdapter
        }
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
                        resultAdapter.clearList()
                        resultAdapter.submitList(searchResponse.images?: emptyList())
                    } else {
                        Toast.makeText(requireContext(),"Connection Failed",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<SearchResponse?>, t: Throwable) {
                    Toast.makeText(requireContext(),"Connection Failed",Toast.LENGTH_LONG).show()
                }
            })

    }

    override fun onItemClicked(data: Any, type: String, position: Int?) {
        when(data){
            (data is SearchResponse.Image) ->{
                val url = (data as SearchResponse.Image).link
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(url)
                startActivity(i)
            }
        }


    }
}