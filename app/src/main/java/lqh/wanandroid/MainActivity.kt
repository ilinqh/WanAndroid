package lqh.wanandroid

import android.graphics.Color
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import lqh.kframe.controller.BaseAct
import lqh.kframe.weight.statuslayout.StatusLayout
import lqh.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseAct<ActivityMainBinding>() {

    override fun getLayoutId() = R.layout.activity_main

    override fun initData() {

        setSwipeBackEnable(false)

        binding.click = this

        binding.name = getString(R.string.app_name)

        button.setBackgroundColor(Color.RED)
        button.setBackgroundResource(R.color.colorAccent)
        statusLayout.switchStatusLayout(StatusLayout.NORMAL_STATUS)
    }

    override fun clickView(v: View) {
        when (v.id) {
//            R.id.imageView -> {
//                Toast.makeText(this, "ImageView", Toast.LENGTH_SHORT).show()
//            }
            R.id.button -> {
                SecondActivity.startAct(this@MainActivity)
            }
        }
    }

}
