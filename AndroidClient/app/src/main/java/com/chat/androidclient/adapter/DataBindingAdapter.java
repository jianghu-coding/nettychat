package com.chat.androidclient.adapter;

import android.databinding.BindingAdapter;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by lps on 2018/12/28 16:39.
 * 此类暂不支持转为kotlin 类
 */
public class DataBindingAdapter {
    @BindingAdapter("textWatcher")
    public static void textWatcher(EditText et, TextWatcher watcher){
        et.addTextChangedListener(watcher);
    }
}
