package com.idmikael.newstestapp.ui.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import com.idmikael.newstestapp.data.repository.NewsRepository
import com.idmikael.newstestapp.ui.fragments.AllNewsFragment
import com.idmikael.newstestapp.ui.fragments.NewsFromDatabase
import com.idmikael.newstestapp.ui.fragments.TopHeadlinesFragment
import com.idmikael.newstestapp.utils.Event
import javax.inject.Inject

class AllNewsViewModel@Inject constructor(
        private val repository: NewsRepository): ViewModel() {
    private val _navigateToFragment = MutableLiveData<Event<Fragment>>()

    val navigateToFragment : LiveData<Event<Fragment>>
        get() = _navigateToFragment


    fun userClicksOnNavigationDrawer(type: Int) {

        when(type){
            1 -> {
                _navigateToFragment.value = Event(AllNewsFragment()) // Trigger the event by setting a new Event as a new value
            }
            2 -> {
                _navigateToFragment.value = Event(TopHeadlinesFragment())
            }
            3 -> {
                _navigateToFragment.value = Event(NewsFromDatabase())
            }
        }

    }

}