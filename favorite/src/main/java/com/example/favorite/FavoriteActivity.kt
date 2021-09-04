package com.example.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.favorite.databinding.ActivityFavoriteBinding
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(favoriteModule)
        makeCurrentFragment(FavoriteFragment())
    }

    private fun makeCurrentFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(com.example.submissionjetpackpro.R.id.fl_content, fragment)
            commit()
        }
    }
}