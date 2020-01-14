package com.food.review

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.slider_image_item.view.*


class SliderAdapter(private val context: Context) : PagerAdapter() {
    private var inflater: LayoutInflater? = null
    private val headings= arrayOf("OFFER OF THE DAY","SPECIALTY OF THE HOUSE","CUSTOMERS CHOISE")
    private val images = arrayOf(R.drawable.slider1, R.drawable.slider2, R.drawable.slider3)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {

        return view === `object`
    }

    override fun getCount(): Int {

        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater!!.inflate(R.layout.slider_image_item, null)
        view.slider_image.setImageResource(images[position])
        view.headingLabel.text=headings[position]

        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }

}