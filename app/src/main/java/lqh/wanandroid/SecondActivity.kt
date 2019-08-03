package lqh.wanandroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*
import lqh.kframe.util.dp2px
import lqh.kframe.weight.bottom_tab.BottomTab

class SecondActivity : AppCompatActivity() {

    companion object {
        fun startAct(context: Context) {
            val intent = Intent(context, SecondActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val tabList: ArrayList<BottomTab> by lazy {
        ArrayList<BottomTab>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val imageView1 = ImageView(this)
        imageView1.setImageResource(R.color.colorPrimary)
        val params1 = LinearLayout.LayoutParams(
            64.dp2px(),
            64.dp2px()
        )
        params1.weight = 1f
        imageView1.layoutParams = params1
        tabList.add(BottomTab(imageView1))

        val imageView = ImageView(this)
        imageView.setImageResource(R.color.colorPrimaryDark)
        val params = LinearLayout.LayoutParams(
            40.dp2px(),
            40.dp2px()
        )
        params.weight = 1f
        imageView.layoutParams = params
        tabList.add(BottomTab(imageView))


        bottomTabLayout.setBottomTab(tabList)
    }
}
