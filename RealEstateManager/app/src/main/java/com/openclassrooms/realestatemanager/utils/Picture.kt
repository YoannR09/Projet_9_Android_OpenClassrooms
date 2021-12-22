package com.openclassrooms.realestatemanager.utils

class Picture {
    var url: String? = null
    var description: String? = null

    constructor() {}
    constructor(url: String?, description: String?) {
        this.url = url
        this.description = description
    }
}