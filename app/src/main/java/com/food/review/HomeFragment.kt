package com.food.review

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_home.*






class HomeFragment(val applicationContext: Context) : Fragment() {

     @Nullable
    override fun onCreateView(@NonNull inflater:LayoutInflater, @Nullable container:ViewGroup?, @Nullable savedInstaceState:Bundle?):View?{
         val inflate = inflater.inflate(R.layout.fragment_home,container,false)
         val viewPager = inflate.findViewById(R.id.viewpager) as ViewPager
         val adapter = SliderAdapter(applicationContext)
         viewPager.adapter=adapter

        return inflate
    }

}