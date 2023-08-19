package org.tensorflow.lite.examples.objectdetection.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.tensorflow.lite.examples.objectdetection.databinding.RecyclerItemSearchResultBinding
import org.tensorflow.lite.examples.objectdetection.utils.OnItemClickedGeneric
import org.tensorflow.lite.examples.objectdetection.utils.ViewBindingVH
import org.tensorflow.lite.examples.objectdetection.utils.diffutils.DiffUtilCallBack
import org.tensorflow.lite.examples.objectdetection.utils.diffutils.ItemsDiffCallback
import org.tensorflow.lite.examples.objectdetection.utils.network.response.SearchResponse
import java.lang.ref.WeakReference

class SearchResultAdapter
constructor(
    private val context: WeakReference<Context>
) : RecyclerView.Adapter<ViewBindingVH<RecyclerItemSearchResultBinding>>() {

    private lateinit var onItemClicked: OnItemClickedGeneric<Any>


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewBindingVH<RecyclerItemSearchResultBinding> =
        ViewBindingVH.create(parent, RecyclerItemSearchResultBinding::inflate)

    override fun onBindViewHolder(
        holder: ViewBindingVH<RecyclerItemSearchResultBinding>,
        position: Int
    ) {
        val data = mNewList[position]

        holder.binding.apply {
            tvCategoryName.text = data.title
            Glide.with(holder.itemView).load(data.imageUrl).into(ivCategoryImage)
            parentLayout.setOnClickListener {
                onItemClicked.onItemClicked(data,"click", position)
            }
        }
    }

    private val callback by lazy { DiffUtilCallBack(this) }
    private val mNewList by lazy { arrayListOf<SearchResponse.Image>() }

    fun submitList(list: List<SearchResponse.Image>) {
        updateListItems(list)
    }

    private fun updateListItems(list: List<SearchResponse.Image>) {
        val diffCallback = ItemsDiffCallback(mNewList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mNewList.clear()
        mNewList.addAll(list)
        diffResult.dispatchUpdatesTo(callback)
    }

    fun clearList() {
        mNewList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = mNewList.size


    fun setOnItemClickListener(listener: OnItemClickedGeneric<Any>) { onItemClicked = listener }

}
