package com.imaec.data.auth

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AlicornAuthenticatorService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        val authenticator = AlicornAccountAuthenticator(this)
        return authenticator.iBinder
    }
}
