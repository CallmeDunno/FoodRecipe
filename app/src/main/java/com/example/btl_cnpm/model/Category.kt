package com.example.btl_cnpm.model

class Category {
    private var _id: String?= ""
    var id
        get() = _id ?: ""
        set(value) {
            _id = value
        }

    private var _name: String?= ""
    var name
        get() = _name ?: ""
        set(value) {
            _name = value
        }
    constructor()
    constructor(
        id: String,
        name: String
    ) {
        this._id = id
        this._name = name
    }
}