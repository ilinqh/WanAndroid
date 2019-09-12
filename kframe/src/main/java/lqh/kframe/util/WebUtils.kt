package lqh.kframe.util

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
        //支持javascript
        settings.javaScriptEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true

        // 设置可以支持缩放
        settings.setSupportZoom(true)
        // 设置显示缩放按钮
        settings.builtInZoomControls = true
        // 不显示 webView 缩放按钮
        settings.displayZoomControls = false

        //扩大比例的缩放
        settings.useWideViewPort = true

        //自适应屏幕
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.loadWithOverviewMode = true
        // 这里需要设置为true，才能让 webView 支持<meta>标签的viewport属性
        settings.useWideViewPort = true

        webView.loadUrl(url)
    }
}