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

    private var _timer: Int = 0
    var timer
        get() = _timer
        set(value) {
            _timer = value
        }

    private var _rate: Float = 0.0f
    var rate
        get() = _rate
        set(value) {
            _rate = value
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
        timer: Int,
        rate: Float
    ) {
        this._id = id
        this._name = name
        this._idCategoryType = idCategoryType
        this._idUser = idUser
        this._ingredient = ingredient
        this._date = date
        this._image = image
        this._timer = timer
        this._rate = rate
    }

    constructor(
        name: String,
        idCategoryType: String,
        idUser: String,
        ingredient: String,
        date: String,
        image: String,
        timer: Int,
        rate: Float
    ) {
        this._name = name
        this._idCategoryType = idCategoryType
        this._idUser = idUser
        this._ingredient = ingredient
        this._date = date
        this._image = image
        this._timer = timer
        this._rate = rate
    }

    constructor(
        id: String,
        name: String,
        idUser: String,
        image: String,
    ) {
        this._id = id
        this._name = name
        this._idUser = idUser
        this._image = image
    }
}