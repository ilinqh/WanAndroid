package lqh.kframe.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * 功能：图片工具类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019-09-25
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
object ImageUtils {

    /**
     * 根据 URL 下载图片
     *
     * @param context 上下文
     * @param url 图片 URL 地址
     */
    suspend fun downloadImage(context: Context, url: String) = withContext(Dispatchers.IO) {
        val success: Boolean
        val file: File?
        val fileName = "${System.currentTimeMillis()}.jpg"
        val bitmap: Bitmap?

        try {
            // 系统相册目录
            val galleryPath = (Environment.getExternalStorageDirectory().toString()
                    + File.separator + Environment.DIRECTORY_DCIM
                    + File.separator + (AppUtils.getApplicationName(context) ?: "Camera"))
            val parentFile = File(galleryPath)
            if (!parentFile.exists()) {
                parentFile.mkdir()
            }
            file = File(parentFile, fileName)

            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = false
            val inputStream = getImageStream(url)
            bitmap = BitmapFactory.decodeStream(inputStream, null, options)
            val outStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()
            success = true

            if (success && bitmap != null) {
                //通知相册更新
                withContext(Dispatchers.Main) {
                    MediaStore.Images.Media.insertImage(
                        context.contentResolver,
                        bitmap,
                        file.toString(),
                        null
                    )
                    val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                    val uri = Uri.fromFile(file)
                    intent.data = uri
                    context.sendBroadcast(intent)
                    Toast.makeText(context, "图片已保存到手机相册", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "图片失败，请稍后再试", Toast.LENGTH_SHORT).show()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 获取网络图片 InputStream
     *
     * @return inputStream
     */
    private fun getImageStream(path: String): InputStream? {
        val url = URL(path)
        val conn = url.openConnection() as HttpURLConnection
        conn.connectTimeout = 5 * 1000
        conn.requestMethod = "GET"
        return if (conn.responseCode == HttpURLConnection.HTTP_OK) {
            conn.inputStream
        } else {
            null
        }
    }
}


