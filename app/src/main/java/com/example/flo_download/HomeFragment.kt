package com.example.flo_download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo_download.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeAlbumLILACIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerViewpager.adapter = bannerAdapter
        binding.homeBannerViewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL     //좌우 스크롤

        val panelAdapter = BannerVPAdapter(this)
        panelAdapter.addFragment(Panel1Fragment())
        panelAdapter.addFragment(Panel2Fragment())
        panelAdapter.addFragment(Panel3Fragment())
        binding.homePannelVp.adapter = panelAdapter
        binding.homePannelVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.homePannelTb, binding.homePannelVp){
            tab, position ->
            binding.homePannelVp.setCurrentItem(tab.position)
        }.attach()
        return binding.root
    }
}