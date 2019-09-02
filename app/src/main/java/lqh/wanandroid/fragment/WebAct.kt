package lqh.wanandroid.fragment

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.act_web.*
import lqh.kframe.controller.BaseAct
import lqh.kframe.util.TipUtils
import lqh.kframe.util.WebUtils
import lqh.kframe.weight.statuslayout.StatusLayout
import lqh.wanandroid.R
import lqh.wanandroid.config.IntentKey
import lqh.wanandroid.databinding.ActWebBinding

class WebAct : BaseAct<ActWebBinding>() {

    companion object {
        fun startAct(context: Context, url: String) {
            val intent = Intent(context, WebAct::class.java)
            intent.putExtra(IntentKey.KEY_URL, url)
            context.startActivity(intent)
        }
    }

    private lateinit var url: String

    override fun getLayoutId(): Int {
        return R.layout.act_web
    }

    override fun initData() {
        statusLayout.switchStatusLayout(StatusLayout.NORMAL_STATUS)
        url = intent.getStringExtra(IntentKey.KEY_URL)

        WebUtils.initWebViewWithUrl(webView, url)

        // 获取网页标题
        webView.webChromeClient = object : WebChromeClient() {

            // 显示加载进度
            override fun onProgressChanged(webView: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progress.visibility = View.GONE
                } else {
                    progress.visibility = View.VISIBLE
                    progress.progress = newProgress
                }
            }

            override fun onReceivedTitle(webView: WebView?, s: String?) {
                super.onReceivedTitle(webView, s)
                if (!TextUtils.isEmpty(s)) {
                    binding.title = s
                }
            }

            override fun onJsAlert(
                webView: WebView?,
                url: String?,
                message: String,
                jsResult: JsResult
            ): Boolean {
                TipUtils.showToast(this@WebAct, message)
                jsResult.confirm()
                return true
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
