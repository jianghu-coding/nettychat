package com.chat.androidclient.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import java.io.ByteArrayOutputStream

/**
 * Created by lps on 2019/2/14 15:21.
 */
object ImageUtil {
    /**
     * @param bitmap
     * @param size 指定的大小 KB
     */
    fun compressBitmapToSize(bitmap: Bitmap?, size: Int): ByteArray {
        val baos = ByteArrayOutputStream()
        if (bitmap==null){return baos.toByteArray()}
        var options=100
        bitmap.compress(Bitmap.CompressFormat.JPEG,options,baos)
        while (baos.size()>size*1024){
            baos.reset()
            options-=5
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,baos)
        }
        if (!bitmap.isRecycled)bitmap.recycle()
        return baos.toByteArray()
        
    }
    /**
     * 质量压缩
     * 默认压缩到200 Kb
     */
    fun compressBitmapToSize(bitmap: Bitmap?): ByteArray {
        return compressBitmapToSize(bitmap, 200)
    }
    
    /**
     * 宽高压缩
     * @param url 路径
     * 宽度限制为1080，高度限制为1920的默认值
     */
    fun compressWH(url:String):Bitmap{
       return compressWH(url,1080,1920)
    }
    /**
     * 宽高压缩
     * @param url 路径
     * @param w 宽度限制
     * @param h 高度限制
     */
    fun compressWH(url:String,w:Int,h:Int) :Bitmap{
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds=true
        BitmapFactory.decodeFile(url,options)
        options.inSampleSize=calculateInSampleSize(options,w,h)
        options.inJustDecodeBounds=false
        return BitmapFactory.decodeFile(url, options)
    }
    
    private fun calculateInSampleSize(options: BitmapFactory.Options, w: Int, h: Int): Int {
        var height=options.outHeight
        var width=options.outWidth
        var insampleSize=1
        while (width>=w||height>=h){
            width= width.shr(1)
            height= height.shr(1)
           insampleSize= insampleSize.shl(1)
        }
        return insampleSize
    }
}