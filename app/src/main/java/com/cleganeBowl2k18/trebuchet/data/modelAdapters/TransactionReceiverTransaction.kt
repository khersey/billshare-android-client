package com.cleganeBowl2k18.trebuchet.data.modelAdapters

class TransactionReceiverTransaction {
    // {"id": 1, "label": "testLabel", "group": 1, "creator": 1}
    var id: Long = 0
    var label: String = ""
    var group: Long = 0
    var creator: Long = 0

    constructor()

    constructor(id: Long, label: String, group: Long, creator: Long) {
        this.id = id
        this.label = label
        this.group = group
        this.creator = creator
    }
}

