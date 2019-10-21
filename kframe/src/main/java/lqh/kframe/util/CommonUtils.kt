package lqh.kframe.util

import android.content.Context
import com.yanzhenjie.permission.AndPermission
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * 功能：通用工具类
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/10/17
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
object CommonUtils {

    /**
     * 判断是否有用权限
     *
     * @return true - 获取权限成功， false - 获取权限失败
     */
    suspend fun checkPermission(context: Context, vararg permissions: String) =
        suspendCancellableCoroutine<Boolean> { continuation ->
            AndPermission.with(context)
                .runtime()
                .permission(permissions)
                .onGranted {
                    continuation.resume(true)
                }
                .onDenied {
                    continuation.resume(false)
                }
                .start()
        }

}