package lqh.kframe.util

import android.content.Context
import android.text.TextUtils

/**
 * 功能：
 * -------------------------------------------------------------------------------------------------
 * 创建者：@author lqh
 * -------------------------------------------------------------------------------------------------
 * 创建日期：2019/6/26
 * -------------------------------------------------------------------------------------------------
 * 更新历史
 * 编号|更新日期|更新人|更新内容
 */
object SPUtils {

    /**
     * SharedPreferences 名称
     */
    var SP_NAME = "SP_NAME"

    var defaultBoolean = false
    var defaultInt = -1
    var defaultString = ""
    var defaultLong = -1L
    var defaultFloat = -1F

    /**
     * 配置 SP 文件名字，在 Application 中调用
     *
     * @param name
     */
    fun init(name: String) {
        SP_NAME = name
    }

    /**
     * 保存 Int 类型
     *
     * @param key 键值
     * @param data 数值
     */
    fun save(context: Context, key: String, data: Int) {
        if (!TextUtils.isEmpty(key)) {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putInt(key, data)
            editor.apply()
        }
    }

    /**
     * 保存 String 类型
     *
     * @param key 键值
     * @param data 数值
     */
    fun save(context: Context, key: String, data: String) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(data)) {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putString(key, data)
            editor.apply()
        }
    }

    /**
     * 保存 Long 类型
     *
     * @param key 键值
     * @param data 数值
     */
    fun save(context: Context, key: String, data: Long) {
        if (!TextUtils.isEmpty(key)) {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putLong(key, data)
            editor.apply()
        }
    }

    /**
     * 保存 Long 类型
     *
     * @param key 键值
     * @param data 数值
     */
    fun save(context: Context, key: String, data: Float) {
        if (!TextUtils.isEmpty(key)) {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putFloat(key, data)
            editor.apply()
        }
    }

    /**
     * 保存 Boolean 类型
     *
     * @param key 键值
     * @param data 数值
     */
    fun save(context: Context, key: String, data: Boolean) {
        if (!TextUtils.isEmpty(key)) {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.putBoolean(key, data)
            editor.apply()
        }
    }

    fun removeKey(context: Context, key: String) {
        if (!TextUtils.isEmpty(key)) {
            val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            val editor = sp.edit()
            editor.remove(key)
            editor.apply()
        }
    }

    fun getString(context: Context, key: String): String? {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getString(key, defaultString)
    }

    fun getInt(context: Context, key: String): Int {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(key, defaultInt)
    }

    fun getFloat(context: Context, key: String): Float {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getFloat(key, defaultFloat)
    }

    fun getLong(context: Context, key: String): Long {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getLong(key, defaultLong)
    }

    fun getBoolean(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(key, defaultBoolean)
    }

    fun hasKey(context: Context, key: String): Boolean {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.contains(key)
    }

    fun clear(context: Context) {
        val sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.clear()
        editor.apply()
    }
}