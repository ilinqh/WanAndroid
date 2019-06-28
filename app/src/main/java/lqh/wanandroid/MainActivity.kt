package lqh.wanandroid

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import lqh.kframe.util.dp2px

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            imageView.radius = 32.dp2px().toFloat()
        }
        button.setBackgroundColor(Color.RED)
        button.setBackgroundResource(R.color.colorAccent)

        var info = packageManager.getPackageInfo(this.packageName, 0)
        var name1 = info.packageName
        Log.e("NAME1", "name1 -> $name1")

        var name2 = packageName
        Log.e("NAME2", "name2 -> $name2")
    }
}
