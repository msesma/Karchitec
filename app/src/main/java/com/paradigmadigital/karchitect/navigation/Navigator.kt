package com.paradigmadigital.karchitect.navigation

import android.support.v7.app.AppCompatActivity
import javax.inject.Inject


class Navigator
@Inject
constructor(
        val activity: AppCompatActivity
) {


//    private val containerId: Int
//    private val fragmentManager: FragmentManager
//    @Inject
//    fun NavigationController(mainActivity: MainActivity): ??? {
//        this.containerId = R.id.container
//        this.fragmentManager = mainActivity.getSupportFragmentManager()
//    }
//
//    fun navigateToSearch() {
//        val searchFragment = SearchFragment()
//        fragmentManager.beginTransaction()
//                .replace(containerId, searchFragment)
//                .commitAllowingStateLoss()
//    }
//
//    fun navigateToRepo(owner: String, name: String) {
//        val fragment = RepoFragment.create(owner, name)
//        val tag = "repo/$owner/$name"
//        fragmentManager.beginTransaction()
//                .replace(containerId, fragment, tag)
//                .addToBackStack(null)
//                .commitAllowingStateLoss()
//    }
//
//    fun navigateToUser(login: String) {
//        val tag = "user" + "/" + login
//        val userFragment = UserFragment.create(login)
//        fragmentManager.beginTransaction()
//                .replace(containerId, userFragment, tag)
//                .addToBackStack(null)
//                .commitAllowingStateLoss()
//    }
}