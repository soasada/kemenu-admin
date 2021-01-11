package com.kemenu.admin.helper

import com.kemenu.admin.domain.Customer
import com.kemenu.admin.domain.MarketingInfo
import java.util.UUID

object CustomerHelper {

    fun random() = Customer(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString() + "@example.com",
            UUID.randomUUID().toString(),
            listOf(BusinessHelper.random()),
            Customer.Role.USER,
            MarketingInfo(MarketingInfo.NewsletterStatus.ACCEPTED)
    )
}