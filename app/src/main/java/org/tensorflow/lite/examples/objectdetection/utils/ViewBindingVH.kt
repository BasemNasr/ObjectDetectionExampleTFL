package org.tensorflow.lite.examples.objectdetection.utils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class ViewBindingVH<VB: ViewBinding> constructor(val binding: VB) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        inline fun <VB: ViewBinding> create(
            parent: ViewGroup,
            crossinline block: (inflater: LayoutInflater, container: ViewGroup, attach: Boolean) -> VB
        ) = ViewBindingVH(block(LayoutInflater.from(parent.context), parent, false))
    }}