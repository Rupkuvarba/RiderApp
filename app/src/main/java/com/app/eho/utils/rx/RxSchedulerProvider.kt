package com.app.eho.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxSchedulerProvider : SchedulerProvider {

    override fun computation(): Scheduler = Schedulers.computation()

    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}
