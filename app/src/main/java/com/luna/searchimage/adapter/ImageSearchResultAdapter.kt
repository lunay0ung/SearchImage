package com.luna.searchimage.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luna.searchimage.R
import com.luna.searchimage.data.Image
import com.luna.searchimage.ui.search.ImageListFragment
import kotlinx.android.synthetic.main.image_list_item.view.*


class ImageSearchResultAdapter(
    private val context: Context,
    private val imageList: PagedList<Image>,
    private val imageClickListener: ImageListFragment.OnImageClicked
) : PagedListAdapter<Image, ImageSearchResultAdapter.ImageViewHolder>(IMAGE_COMPARATOR) {

    private val TAG = ImageSearchResultAdapter::class.java.simpleName


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_list_item, parent, false)

        return ImageViewHolder(view)
    }
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val image = getItem(position)
        holder.itemView.setOnClickListener {
            imageClickListener.onImageClicked(image!!)
        }
        image?.let { holder.bind(it) }
    }


    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val TAG = ImageViewHolder::class.java.simpleName
        private val thumb = view.thumbnail
        private val siteName = view.siteName
       /*
          private val title = view.title
        private val salePrice = view.price
        private val author = view.author
        private val publisher = view.publisher
        private val translator = view.translator
        private val currencyFormat = NumberFormat.getCurrencyInstance()
        private val status = view.status
        */


        fun bind(image: Image) {

            Log.d(TAG, ">>image url: ${image.imgUrl}")

            Glide.with(thumb.context)
                .load(image.thumbnailUrl)
                .placeholder(R.drawable.ic_defaultimage)
                .into(thumb)

            siteName.text = image.siteName
            /*
               val formattedSalePrice = currencyFormat.format(book.sale_price)
            val formattedPrice = currencyFormat.format(book.price)
            val authors = book.authors!!
                .joinToString(separator = ", ")

            title.text = book.title
            salePrice.text = formattedSalePrice+" (정가: ${formattedPrice})"
            publisher.text = book.publisher
            author.text = authors
            if(!book.translators.isNullOrEmpty()) {
                val translators = book.translators!!
                    .joinToString(separator = ", ")
                translator.text = "| $translators"
            }

            status.text = book.status
             */

        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
                oldItem.imgUrl == newItem.imgUrl
            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
                newItem.imgUrl == oldItem.imgUrl
        }
    }
}