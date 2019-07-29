package lqh.wanandroid

import android.graphics.Color
import kotlinx.android.synthetic.main.activity_main.*
import lqh.kframe.controller.BaseAct
import lqh.kframe.weight.statuslayout.StatusLayout

class MainActivity : BaseAct() {

    override fun getLayoutId() = R.layout.activity_main

    override fun initData() {

        setSwipeBackEnable(false)

        root.postDelayed({
            statusLayout.switchStatusLayout(StatusLayout.ERROR_STATUS)
        }, 3000)

        button.setOnClickListener(this)
        imageView.setOnClickListener(this)
        button.setBackgroundColor(Color.RED)
        button.setBackgroundResource(R.color.colorAccent)
    }

}
