package com.example.btl_cnpm.model

class Recipe {
    private var _id: String? = ""
    var id
        get() = _id ?: ""
        set(value) {
            _id = value
        }

    private var _name: String? = ""
    var name
        get() = _name ?: ""
        set(value) {
            _name = value
        }

    private var _idCategoryType: String? = ""
    var idCategoryType
        get() = _idCategoryType ?: ""
        set(value) {
            _idCategoryType = value
        }

    private var _idUser: String? = ""
    var idUser
        get() = _idUser ?: ""
        set(value) {
            _idUser = value
        }

    private var _ingredient: String? = ""
    var ingredient
        get() = _ingredient ?: ""
        set(value) {
            _ingredient = value
        }

    private var _date: String? = ""
    var date
        get() = _date ?: ""
        set(value) {
            _date = value
        }

    private var _image: String? = ""
    var image
        get() = _image ?: ""
        set(value) {
            _image = value
        }

    private var _timer: Int?= 0
    var timer
        get() = _timer ?: 0
        set(value) {
            _timer = value
        }

    constructor()
    constructor(
        id: String,
        name: String,
        idCategoryType: String,
        idUser: String,
        ingredient: String,
        date: String,
        image: String,
        timer: Int
    ) {
        this._id = id
        this._name = name
        this._idCategoryType = idCategoryType
        this._idUser = idUser
        this._ingredient = ingredient
        this._date = date
        this._image = image
        this._timer = timer
    }

    constructor(
        name: String,
        idCategoryType: String,
        idUser: String,
        ingredient: String,
        date: String,
        image: String,
        timer: Int
    ) {
        this._name = name
        this._idCategoryType = idCategoryType
        this._idUser = idUser
        this._ingredient = ingredient
        this._date = date
        this._image = image
        this._timer = timer
    }
}