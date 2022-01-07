package com.app.eho.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}