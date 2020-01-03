package com.food.review

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*






class HomeFragment(val applicationContext: Context) : Fragment() {

    var images:Array<Int> = arrayOf(R.drawable.slider1,R.drawable.slider2,R.drawable.slider3)
    var adapter: PagerAdapter =SliderAdapter(applicationContext,images)
    @Nullable
    override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{
        val inflate = inflater.inflate(R.layout.fragment_home,container,false)
        //viewpager.adapter= this.adapter
        return inflate
    }

}