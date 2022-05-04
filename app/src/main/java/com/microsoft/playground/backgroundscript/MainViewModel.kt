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

    fun markCalled() {
        if (!called) {
            called = true;
            notifyPropertyChanged(BR.called)
            setReady(false)
        }
    }

    fun callScript() {
        model.call()
    }

    interface Model {
        fun call()
    }
}