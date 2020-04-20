package lqh.wanandroid.fragment.mine

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.frg_mine.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lqh.kframe.controller.BaseFragment
import lqh.kframe.util.LogUtils
import lqh.kframe.weight.statuslayout.StatusLayout
import lqh.wanandroid.R
import lqh.wanandroid.adapter.TestAdapter
import lqh.wanandroid.coroutines.GitHubServiceApi
import lqh.wanandroid.coroutines.TestUtils
import lqh.wanandroid.coroutines.User
import lqh.wanandroid.databinding.FrgHomeBinding
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-09-02
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
class MineFrg : BaseFragment<FrgHomeBinding>() {

    private val COLOR_LIST =
        arrayListOf(R.color.colorAccent, R.color.colorPrimary, R.color.color_666666)

    private val data by lazy {
        arrayListOf<Int>()
    }

    private val adapter by lazy {
        TestAdapter(data)
    }

    val gitHubServiceApi by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            //添加对 Deferred 的支持
//            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(GitHubServiceApi::class.java)
    }

    override fun initData() {

        statusLayout.switchStatusLayout(StatusLayout.NORMAL_STATUS)

        for (i in 0 until 20) {
            data.add(COLOR_LIST[i % COLOR_LIST.size])
        }

        GlobalScope.launch {
            try {
                val user : User = gitHubServiceApi.getUser("savage-lin")
                LogUtils.e("mainScope$user")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val headerView =
            LayoutInflater.from(mContext).inflate(R.layout.header_view, recyclerView, false)
        headerView.setOnClickListener {
//            TestUtils.addShortcut(mContext)
        }
        val headerView1 =
            LayoutInflater.from(mContext).inflate(R.layout.header_view, recyclerView, false)
        headerView1.setBackgroundColor(Color.BLUE)
        adapter.addHeaderView(headerView)
        adapter.addHeaderView(headerView1)
        adapter.setHeaderAndEmpty(true)

        adapter.setOnItemClickListener { _, view, position ->
            LogUtils.e("adapter position -> ${recyclerView.getChildAdapterPosition(view)}")
            LogUtils.e("position -> $position")
            LogUtils.e("headerView Count -> ${adapter.headerLayoutCount}")
        }

        recyclerView.adapter = adapter
        adapter.emptyView = emptyView
    }

    override fun getLayoutId(): Int {
        return R.layout.frg_mine
    }

    override fun clickView(v: View) {
        when (v.id) {
        }
    }
}