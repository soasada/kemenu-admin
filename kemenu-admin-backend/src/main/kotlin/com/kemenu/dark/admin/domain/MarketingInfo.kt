package com.kemenu.dark.admin.domain

data class MarketingInfo(
        val newsletterStatus: NewsletterStatus
) {

    enum class NewsletterStatus {
        OLD, ACCEPTED, REJECTED;

        fun isOld() = this == OLD
        fun isAccepted() = this == ACCEPTED
        fun isRejected() = this == REJECTED
    }
}