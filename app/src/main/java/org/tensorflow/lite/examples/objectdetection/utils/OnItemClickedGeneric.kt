package org.tensorflow.lite.examples.objectdetection.utils

interface OnItemClickedGeneric<T> {
    fun onItemClicked(data: T, type: String = "", position: Int?=null)
}