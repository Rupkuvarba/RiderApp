package com.app.eho.ui.modules.navigatedrawer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.eho.R
import com.app.eho.databinding.ActivityNavigateDrawerBinding
import com.app.eho.ui.base.BaseActivity

class NavigateDrawerActivity : BaseActivity() {

    lateinit var context: Context
    lateinit var viewModel: NavigateDrawerViewModel
    lateinit var activityBinding : ActivityNavigateDrawerBinding

    private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var navView: MaterialNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        initViewModel()
        setupView(savedInstanceState)
        setupObservers()
    }

    override fun initBinding() {
        activityBinding = ActivityNavigateDrawerBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
    }

    override fun initViewModel() {
        context = this
        viewModel = ViewModelProvider(this)[NavigateDrawerViewModel::class.java]
        //viewModel.setNetworkHelper(NetworkHelper(context))
        activityBinding.mViewModel = viewModel
    }

    override fun setupView(savedInstanceState: Bundle?) {
        setSupportActionBar(activityBinding.llAppBar.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), activityBinding.drawerLayout
        )

        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appBarConfiguration)
        activityBinding.navView.setupWithNavController(navController)

        // Show ItemStyle
        println("ItemStyle=${activityBinding.navView.getItemStyle()}")
    }

    override fun setupObservers() {
        super.setupObservers()
        // Event is used by the view model to tell the activity to launch another activity
        // view model also provided the Bundle in the event that is needed for the Activity

        /*viewModel.progressIn.observe(this, Observer {
            activityBinding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        })*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*when (item.itemId) {
            R.id.action_default -> {
                activityBinding.navView.setItemStyle(MaterialNavigationView.ITEM_STYLE_DEFAULT)
            }
            R.id.action_round_rect -> {
                activityBinding.navView.setItemStyle(MaterialNavigationView.ITEM_STYLE_ROUND_RECTANGLE)
            }
            R.id.action_round_right -> {
                activityBinding.navView.setItemStyle(MaterialNavigationView.ITEM_STYLE_ROUND_RIGHT)
            }
        }*/
        return false
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}

fun Context.navigateDrawerIntent(): Intent {
    return Intent(this, NavigateDrawerActivity::class.java)
}