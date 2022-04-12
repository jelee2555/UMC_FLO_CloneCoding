package com.example.flo_download

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo_download.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    val handler = Handler(Looper.getMainLooper()){
        setPage()
        true
    }

    var currentPage: Int = 0


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


        val panelAdapter = PanelVPAdapter(this)
        panelAdapter.addFragment(Panel1Fragment())
        panelAdapter.addFragment(Panel2Fragment())
        panelAdapter.addFragment(Panel3Fragment())

        binding.homePannelVp.adapter = panelAdapter
        binding.homePannelVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        //스크롤을 넘길 때 효과가 나타나지 않도록, Viewpager의 child를 가져와 onScrollMode 설정
        val child = binding.homeBannerViewpager.getChildAt(0)
        (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER


        val thread = Thread(PagerRunnable())
        thread.start()

        //인디케이터 생성
        TabLayoutMediator(binding.homePannelTb, binding.homePannelVp){
            tab, position ->
            binding.homePannelVp.setCurrentItem(tab.position)
        }.attach()
        return binding.root
    }

    //페이지 변경하기
    private fun setPage(){
        if(currentPage == 3){
            currentPage = 0
        }
        binding.homeBannerViewpager.setCurrentItem(currentPage, true)
        currentPage += 1

    }

    //2초마다 페이지 넘기기
    inner class PagerRunnable:Runnable{
        override fun run() {
            while(true){
                try{
                    Thread.sleep(2000)
                    handler.sendEmptyMessage(0)
                } catch (e :InterruptedException){
                    Log.d("interrupt", "interrupt 발생")
                }
            }
        }
    }
}