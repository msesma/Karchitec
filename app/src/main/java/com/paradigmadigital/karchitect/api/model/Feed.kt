package com.paradigmadigital.karchitect.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
class Feed(
        @field:Element(name = "channel")
        var channel: FeedChannel? = null,

        var url: String? = null
)