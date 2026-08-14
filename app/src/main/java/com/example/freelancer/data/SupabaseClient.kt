
package com.example.freelancer.data

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

val supabase = createSupabaseClient(
    supabaseUrl = "https://upsugfaojqarzgcizdid.supabase.co",
    supabaseKey = "sb_publishable_NeufFSRvmle53fzlkWT9Kw_INHi9sEc"
) {
    install(Auth) {
        scheme = "profreelance"
        host = "auth-callback"
    }

    install(Postgrest)
    install(Storage)
}
