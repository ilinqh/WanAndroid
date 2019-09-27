package lqh.kframe.util

import android.app.Service
import android.os.Vibrator
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

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
object WebUtils {

    fun initWebViewWithUrl(webView: WebView, url: String) {
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        // 除去复制功能
        webView.setOnLongClickListener { true }

        val settings = webView.settings
        settings.apply {
            //支持javascript
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true

            // 设置可以支持缩放
            setSupportZoom(true)
            // 设置显示缩放按钮
            builtInZoomControls = true
            // 不显示 webView 缩放按钮
            displayZoomControls = false
            //扩大比例的缩放
            useWideViewPort = true
            //自适应屏幕
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            loadWithOverviewMode = true
            // 这里需要设置为true，才能让 webView 支持<meta>标签的viewport属性
            useWideViewPort = true
        }

        webView.loadUrl(url)
    }

    /**
     * WebView 长按监听，若有图片，则长按下载图片到本地
     */
    fun setOnLongClickListener(webView: WebView) {
        webView.setOnLongClickListener {
            val hitTestResult = webView.hitTestResult
            // 如果是图片资源或者带有图片链接的类型
            if (hitTestResult.type == WebView.HitTestResult.IMAGE_TYPE
                || hitTestResult.type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE
            ) {
                val vibrator =
                    webView.context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(30)
                true
            } else {
                false
            }
        }
    }
}