package com.idmikael.newstestapp.ui.activities

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import com.idmikael.newstestapp.R
import com.idmikael.newstestapp.ui.fragments.AllNewsFragment
import com.idmikael.newstestapp.ui.fragments.NewsFromDatabase
import com.idmikael.newstestapp.ui.fragments.TopHeadlinesFragment
import com.idmikael.newstestapp.ui.view_model.AllNewsViewModel
import com.idmikael.newstestapp.ui.view_model.AllNewsViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_all_news.*
import kotlinx.android.synthetic.main.content_all_news.*
import javax.inject.Inject


class AllNewsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: AllNewsViewModelFactory
    private lateinit var viewModel: AllNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_news)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        AndroidInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(
                AllNewsViewModel::class.java)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        nav_view.setCheckedItem(item.itemId)

        when (item.itemId) {
            R.id.nav_all_news -> {
                tvTitleMain.text = getString(R.string.nav_all_news)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, AllNewsFragment())
                        .commit()
            }
            R.id.nav_top_headlines -> {
                tvTitleMain.text = getString(R.string.nav_top_headlines)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, TopHeadlinesFragment())
                        .commit()
            }
            R.id.nav_news_from_database -> {
                tvTitleMain.text = getString(R.string.nav_news_from_database)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, NewsFromDatabase())
                        .commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
