package com.paradigmadigital.karchitect.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
class FeedChannel(
        @field:Element(name = "title")
        var title: String? = null,

        @field:Element(name = "description")
        var description: String? = null,

        @field:ElementList(inline = true, name = "item")
        var feedItems: List<FeedItem>? = null
)