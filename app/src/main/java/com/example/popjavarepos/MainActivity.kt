package com.example.popjavarepos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.popjavarepos.ui.repositorylist.RepositoryListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RepositoryListFragment())
                .commit()
        }
    }
}