package org.tensorflow.lite.examples.objectdetection.utils.diffutils
import androidx.recyclerview.widget.DiffUtil

class ItemsDiffCallback<T>(
    private val mOld: List<T>,
    private val mNew: List<T>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int { return mOld.size }

    override fun getNewListSize(): Int { return mNew.size }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOld[oldItemPosition] == mNew[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = mOld[oldItemPosition]
        val new = mNew[newItemPosition]
        return old.toString().equals(new.toString())
    }

}