package com.kemenu.dark.admin.application.security.recaptcha

import java.time.Instant

data class RecaptchaResponse(val success: Boolean, val challenge_ts: Instant, val hostname: String)