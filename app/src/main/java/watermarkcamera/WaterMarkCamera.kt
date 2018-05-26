package watermarkcamera

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.support.annotation.IdRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kangning.rxancoroutines.BaseViewModel
import com.example.kangning.rxancoroutines.R
import kotlinx.android.synthetic.main.marker.view.*

/**
 * Created by kangning on 2018/5/25.
 */
class WaterMarkCamera : FrameLayout {

    private val customView: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.marker, null, false)
    }

    private val photos: RecyclerView by lazy {
        photoRecycler.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = waterMarkAdapter
        }
    }

    val waterMarkAdapter: WaterMarkAdapter by lazy {
        WaterMarkAdapter(R.layout.marker_image_item).apply {
            addData(0, ImageSource.LocalImageSource(R.drawable.ic_launcher_foreground))
            setOnItemChildClickListener { adapter, view, position ->
                when (adapter.data[position]) {
                    is ImageSource.LocalImageSource -> {

                    }
                    is ImageSource.CapturedImageSource -> {

                    }
                }
            }
            setOnItemChildClickListener { adapter, view, position ->

            }
        }
    }


    constructor(context: Context) : super(context) {
        this.addView(customView)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        this.addView(customView)
    }


    class WaterMarkAdapter(resourceId: Int) : BaseQuickAdapter<ImageSource, BaseViewHolder>(resourceId) {
        override fun convert(helper: BaseViewHolder, item: ImageSource) {
            when (item) {
                is ImageSource.CapturedImageSource -> {
                    helper.setImageBitmap(R.id.img, item.bitmap)
                }
                is ImageSource.LocalImageSource -> {
                    helper.setImageResource(R.id.img, item.id)
                    helper.setImageResource(R.id.close, R.drawable.close)
                }
            }
            helper.addOnClickListener(R.id.img)
            helper.addOnClickListener(R.id.close)
        }
    }

    private fun getDataSize() = waterMarkAdapter.data.size

    fun addCapturedImage(imageSource: ImageSource.CapturedImageSource) {
        waterMarkAdapter.remove(getDataSize() - 1)
        waterMarkAdapter.addData(imageSource)
    }

    fun removeImage(index: Int) {
        waterMarkAdapter.remove(index)
        if (getDataSize() == 0) {
            waterMarkAdapter.addData(ImageSource.LocalImageSource(R.drawable.ic_launcher_foreground))
        }
    }


    sealed class ImageSource(imageType: ImageType) {
        data class CapturedImageSource(val bitmap: Bitmap) : ImageSource(ImageType.CAPTURE)
        data class LocalImageSource(val id: Int) : ImageSource(ImageType.LOCAL)
    }


    enum class ImageType(type: Int) {
        LOCAL(0), CAPTURE(1)
    }

    //todo
    class ImageUtil {
        companion object {

            fun zipImage(): Bitmap {


            }
        }
    }
}