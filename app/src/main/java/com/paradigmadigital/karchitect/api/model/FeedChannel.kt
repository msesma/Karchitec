package com.paradigmadigital.karchitect.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root


@Root(name = "channel", strict = false)
class FeedChannel {
    @Element(name = "link")
    var link: String? = null
    @Element(name = "title")
    var title: String? = null
    @Element(name = "description")
    var description: String? = null
    @ElementList(inline = true, name = "item")
    var feedItems: List<FeedItem>? = null
}