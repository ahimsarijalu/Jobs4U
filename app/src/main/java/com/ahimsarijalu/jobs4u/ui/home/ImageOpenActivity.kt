package com.ahimsarijalu.jobs4u.ui.home

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.SharedElementCallback
import androidx.viewpager.widget.PagerAdapter
import com.ahimsarijalu.jobs4u.R
import com.ahimsarijalu.jobs4u.databinding.ActivityImageOpenBinding
import com.ahimsarijalu.jobs4u.databinding.ItemImageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.igreenwood.loupe.Loupe
import com.igreenwood.loupe.extensions.createLoupe
import com.igreenwood.loupe.extensions.setOnViewTranslateListener

class ImageOpenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageOpenBinding

    private val urls: List<String> by lazy { intent.getStringArrayListExtra(ARGS_IMAGE_URLS) as List<String> }
    private val initialPos: Int by lazy { intent.getIntExtra(ARGS_IMAGE_POS, 0) }
    private var adapter: ImageAdapter? = null

    inner class ImageAdapter(var context: Context, private var urls: List<String>) :
        PagerAdapter() {

        private var loupeMap = hashMapOf<Int, Loupe>()
        private var views = hashMapOf<Int, ImageView>()
        private var currentPos = 0


        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val binding = ItemImageBinding.inflate(LayoutInflater.from(context))
            container.addView(binding.root)
            loadImage(binding.image, binding.container, position)
            views[position] = binding.image
            return binding.root
        }

        private fun loadImage(image: ImageView, container: ViewGroup, position: Int) {
            Glide.with(image.context)
                .load(urls[position])
                .onlyRetrieveFromCache(true)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        startPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean,
                    ): Boolean {
                        image.transitionName =
                            context.getString(R.string.shared_image_transition, position)

                        val loupe = createLoupe(image, container) {


                            setOnViewTranslateListener(
                                onStart = { },
                                onRestore = { },
                                onDismiss = { finishAfterTransition() }
                            )
                        }

                        loupeMap[position] = loupe

                        if (position == initialPos) {
                            setEnterSharedElementCallback(object : SharedElementCallback() {
                                override fun onMapSharedElements(
                                    names: MutableList<String>?,
                                    sharedElements: MutableMap<String, View>?,
                                ) {
                                    names ?: return
                                    val view = views[currentPos] ?: return
                                    val currentPosition: Int = currentPos
                                    view.transitionName = context.getString(
                                        R.string.shared_image_transition,
                                        currentPosition
                                    )
                                    sharedElements?.clear()
                                    sharedElements?.put(view.transitionName, view)
                                }
                            })
                            startPostponedEnterTransition()
                        }
                        return false
                    }

                })
                .into(image)
        }

        fun clear() {
            loupeMap.forEach {
                val loupe = it.value
                loupe.cleanup()
            }
            loupeMap.clear()
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun setPrimaryItem(container: ViewGroup, position: Int, obj: Any) {
            super.setPrimaryItem(container, position, obj)
            this.currentPos = position
        }

        override fun getCount() = urls.size

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageOpenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewPager()

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAfterTransition()
                adapter?.clear()
            }
        })
    }

    private fun initViewPager() {
        adapter = ImageAdapter(this, urls)
        binding.viewpager.adapter = adapter
        binding.viewpager.currentItem = initialPos
    }

    override fun onDestroy() {
        adapter = null
        super.onDestroy()
    }

    companion object {
        private const val ARGS_IMAGE_URLS = "ARGS_IMAGE_URLS"
        private const val ARGS_IMAGE_POS = "ARGS_IMAGE_POS"

        fun createIntent(context: Context, urls: ArrayList<String>, initialPos: Int): Intent {
            return Intent(context, ImageOpenActivity::class.java).apply {
                putExtra(ARGS_IMAGE_URLS, urls)
                putExtra(ARGS_IMAGE_POS, initialPos)
            }
        }
    }
}