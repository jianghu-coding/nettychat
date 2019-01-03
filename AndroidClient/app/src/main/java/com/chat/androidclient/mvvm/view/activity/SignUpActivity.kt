package com.chat.androidclient.mvvm.view.activity

import android.view.View
import com.chat.androidclient.R
import com.chat.androidclient.databinding.ActivitySignUpBinding
import com.chat.androidclient.mvvm.viewmodel.SignUpVM

class SignUpActivity : BaseActivity<ActivitySignUpBinding, SignUpVM>() {
    override fun getViewModel() = SignUpVM(this)
    
    override fun getLayoutRes() = R.layout.activity_sign_up
    override fun onClick(v: View) {
        when (v.id) {
            R.id.back -> finish()
            R.id.signupbtn -> {
                mVM.signup(mDataBinding.etName.text.toString(),mDataBinding.etName.text.toString(),mDataBinding.etPass.text.toString())
            }
        }
    }
    
}
