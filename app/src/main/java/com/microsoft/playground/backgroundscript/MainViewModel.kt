package com.microsoft.playground.backgroundscript

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class MainViewModel(val model: Model) : BaseObservable() {

    private var ready = false
    private var called = false

    @Bindable
    fun isReady() : Boolean = ready

    fun setReady(isReady: Boolean) {
        if (ready != isReady) {
            ready = isReady;
            notifyPropertyChanged(BR.ready)
        }
    }

    @Bindable
    fun isCalled() : Boolean = called

    fun markCalled() : Boolean {
        if (!called) {
            called = true;
            notifyPropertyChanged(BR.called)
            setReady(false)
            return true
        }
        return false
    }

    fun callScript() {
        if (markCalled()) {
            model.call()
        }
    }

    interface Model {
        fun call()
    }
}