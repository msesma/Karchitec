package com.paradigmadigital.karchitect.api.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
class FeedItem {
    @Element(name = "pubDate")
    var pubDate: String? = null
    @Element(name = "title")
    var title: String? = null
    @Element(name = "link")
    var link: String? = null
    @Element(name = "description")
    var description: String? = null
}