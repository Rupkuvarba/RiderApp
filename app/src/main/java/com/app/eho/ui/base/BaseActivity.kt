package com.app.eho.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.app.eho.R
import com.app.eho.utils.apptheme.ThemeUtil
import com.app.eho.utils.font.Fonts
import com.app.eho.utils.log.LogUtil
import com.app.eho.utils.network.NetUtils
import java.lang.Exception
import java.util.logging.Logger

abstract class BaseActivity : AppCompatActivity(){

    lateinit var mContext : Context;
    var flContent : FrameLayout? = null
    var rlConnectionView:RelativeLayout? = null
    var tvConnection : TextView ?= null

    var isInternetConnected : Boolean = true;

    var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        //ThemeUtil.setTransparentStatusBar(this)
        super.onCreate(savedInstanceState)
        mContext = this@BaseActivity
    }

    override fun setContentView(layoutResID: Int) {
        val nullParent: ViewGroup? = null
        val coordinatorLayout = layoutInflater.inflate(R.layout.activity_base, nullParent) as CoordinatorLayout
        initViews(coordinatorLayout)
        checkConnection()

        layoutInflater.inflate(layoutResID, flContent, true)
        super.setContentView(coordinatorLayout)
    }

    /**
     * Initialize BaseActivity Views
     *
     * @param view : parent use to initialize child views
     */
     open fun initViews(view: View) {
        flContent = view.findViewById<FrameLayout>(R.id.fl_content)
        val layoutConnection = view.findViewById<View>(R.id.layout_connection)
        rlConnectionView = view.findViewById<RelativeLayout>(R.id.rl_connection)
        tvConnection = layoutConnection.findViewById<TextView>(R.id.tv_connection)
    }

    //Net connection
    open fun checkConnection() {
        setNetConnection()
        showConnection()
    }

    fun setNetConnection() {
        isInternetConnected = NetUtils.isNetworkAvailable(mContext)
        LogUtil.displayLog("BaseActivity","NetConnection : setNetConnection "+isInternetConnected)
    }

    fun showConnection() {
        try {
            LogUtil.displayLog("BaseActivity","NetConnection : showConnection "+isInternetConnected+ " rlConnectionView: "+rlConnectionView)
            if (isInternetConnected) {
                rlConnectionView!!.visibility = View.GONE
            } else {
                rlConnectionView!!.visibility = View.VISIBLE
                tvConnection!!.text = resources.getString(R.string.no_internet_connection)
                //rlConnectionView!!.setBackgroundColor(mContext.getResources().getColor(R.color.red))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected abstract fun setupView(savedInstanceState: Bundle?)

    protected abstract fun initBinding()

    protected abstract fun initViewModel()

    protected open fun setupObservers() {
        /*viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })*/
    }

}