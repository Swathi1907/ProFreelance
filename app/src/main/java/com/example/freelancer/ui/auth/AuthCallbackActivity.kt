package com.example.freelancer.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.freelancer.data.supabase
import com.example.freelancer.ui.HomeActivity
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.handleDeeplinks
import kotlinx.coroutines.launch

class AuthCallbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleAuthIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)
        handleAuthIntent(intent)
    }

    private fun handleAuthIntent(intent: Intent?) {

        if (intent == null) {
            finish()
            return
        }

        lifecycleScope.launch {

            try {

                // Let Supabase process the deep link
                supabase.handleDeeplinks(intent)

                // At this point the email confirmation/deep-link
                // flow should have created the authenticated session.
                val session = supabase.auth.currentSessionOrNull()

                if (session != null) {

                    startActivity(
                        Intent(
                            this@AuthCallbackActivity,
                            HomeActivity::class.java
                        ).apply {
                            flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    )

                } else {

                    finish()
                }

            } catch (e: Exception) {

                e.printStackTrace()
                finish()
            }
        }
    }
}