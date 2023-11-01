package com.example.btl_cnpm.model

class Procedure {
    private var _index: Int = 0
    var index
        get() = _index
        set(value) {
            _index = value
        }
    private var _content: String = ""
    var content
        get() = _content
        set(value) {
            _content = value
        }

    constructor()

    constructor(index: Int, content: String) {
        this._index = index
        this._content = content
    }

    companion object {
        class SortByIndex : Comparator<Procedure> {
            override fun compare(p0: Procedure?, p1: Procedure?): Int {
                return p0!!.index.compareTo(p1!!.index)
            }
        }
    }
}