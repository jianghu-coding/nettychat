package com.chat.androidclient.mvvm.view.custom

import android.app.Dialog
import android.content.Context
import com.chat.androidclient.R

/**
 * Created by lps on 2019/1/7 14:40.
 */
class LoadingDialog(context: Context) : Dialog(context) {
    init {
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)
    }
}