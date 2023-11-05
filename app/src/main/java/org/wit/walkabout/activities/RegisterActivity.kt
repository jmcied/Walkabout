package org.wit.walkabout.activities

import android.os.Bundle
import android.util.Patterns
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.wit.walkabout.R
import org.wit.walkabout.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,View.OnKeyListener {

    private lateinit var mBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.fullNameET.onFocusChangeListener = this
        mBinding.emailET.onFocusChangeListener = this
        mBinding.passwordET.onFocusChangeListener = this
        mBinding.confirmPasswordET.onFocusChangeListener = this
    }


        private fun validateFullName(): Boolean {
            var errorMessage: String? = null
            val value: String = mBinding.fullNameET.text.toString()
            if (value.isEmpty()) {
                errorMessage = "Full name is required"
            }
            if(errorMessage != null){
                mBinding.fullNameTIL.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }
            return errorMessage == null
        }

        private fun validateEmail(): Boolean {
            var errorMessage: String? = null
            val value: String = mBinding.emailET.text.toString()
            if (value.isEmpty()) {
                errorMessage = "Email is required"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                errorMessage = "Email address is invalid"
            }
            if(errorMessage != null){
                mBinding.emailTIL.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }
            return errorMessage == null
        }

        private fun validatePassword(): Boolean {
            var errorMessage: String? = null
            val value: String = mBinding.passwordET.text.toString()
            if (value.isEmpty()) {
                errorMessage = "Password is required"
            } else if (value.length < 6) {
                errorMessage = "Password must be at least 6 characters long"
            }

            if(errorMessage != null){
                mBinding.passwordTIL.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }
            return errorMessage == null
        }

        private fun validateConfirmPassword(): Boolean {
            var errorMessage: String? = null
            val value: String = mBinding.confirmPasswordET.text.toString()
            if (value.isEmpty()) {
                errorMessage = "Confirm password is required"
            } else if (value.length < 6) {
                errorMessage = "Confirm password must be at least 6 characters long"
            }
            if(errorMessage != null){
                mBinding.confirmPasswordTIL.apply {
                    isErrorEnabled = true
                    error = errorMessage
                }
            }
            return errorMessage == null
        }

    private fun valdatePasswordAndConfirmPassword(): Boolean {
        var errorMessage: String? = null
        val password: String = mBinding.passwordET.text.toString()
        val confirmPassword: String = mBinding.confirmPasswordET.text.toString()
        if(password != confirmPassword) {
            errorMessage = "Passwords don't match!!"
        }
        if(errorMessage != null){
            mBinding.confirmPasswordTIL.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    override fun onClick(v: View?) {

    }
        override fun onFocusChange(view: View?, hasFocus: Boolean) {
            if(view != null){
                when(view.id){
                    R.id.fullNameET -> {
                        if (hasFocus){
                            if (mBinding.fullNameTIL.isErrorEnabled){
                                mBinding.fullNameTIL.isErrorEnabled = false
                            }

                        }else {
                            validateFullName()
                        }
                    }
                    R.id.emailET -> {
                        if (hasFocus){
                            if (mBinding.emailTIL.isErrorEnabled){
                                mBinding.emailTIL.isErrorEnabled = false
                            }

                        }else {
                            validateEmail()
                        }
                    }
                    R.id.passwordET -> {
                        if (hasFocus){
                            if (mBinding.passwordTIL.isErrorEnabled){
                                mBinding.passwordTIL.isErrorEnabled = false
                            }

                        }else {
                            validatePassword()
                        }

                    }
                    R.id.confirmPasswordET -> {
                        if (hasFocus){
                            if (mBinding.confirmPasswordTIL.isErrorEnabled){
                                mBinding.confirmPasswordTIL.isErrorEnabled = false
                            }

                        }else {
                            validateConfirmPassword()
                        }

                    }
                }
            }
        }

        override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
            return false
        }


}
