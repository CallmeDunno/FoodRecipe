package com.example.btl_cnpm.model

import com.google.firebase.firestore.PropertyName

class User {
    private var _id: String? = ""
    var id
        get() = _id ?: ""
        set(value) {
            _id = value
        }
    private var _username: String = ""
    var username
        get() = _username
        set(value) {
            _username = value
        }
    private var _password: String = ""
    var password
        get() = _password
        set(value) {
            _password = value
        }
    private var _email: String = ""
    var email
        get() = _email
        set(value) {
            _email = value
        }
    private var _bio: String? = ""
    var bio
        get() = _bio ?: ""
        set(value) {
            _bio = value
        }

    private var _image: String? = ""
    var image
        get() = _image ?: ""
        set(value) {
            _image = value
        }

    private var _totalFollower: Int = 0
    var follower
        get() = _totalFollower
        set(value) {
            _totalFollower = value
        }
    private var _totalFollowing: Int = 0
    var following
        get() = _totalFollowing
        set(value) {
            _totalFollowing = value
        }

    constructor()
    constructor(
        username: String,
        password: String,
        email: String,
        bio: String = "",
        image: String = "",
        follower: Int = 0,
        following: Int = 0
    ) {
        this._id = ""
        this._username = username
        this._password = password
        this._email = email
        this._bio = bio
        this._totalFollower = follower
        this._totalFollowing = following
    }

    constructor(
        id: String,
        username: String,
        password: String,
        email: String,
        bio: String = "",
        image: String,
        follower: Int = 0,
        following: Int = 0
    ) {
        this._id = id
        this._username = username
        this._password = password
        this._email = email
        this._bio = bio
        this._image = image
        this._totalFollower = follower
        this._totalFollowing = following
    }

    constructor(
        id: String,
        username: String,
        image: String,
    ) {
        this._id = id
        this._username = username
        this._image = image
    }
    constructor(
        id: String,
        username: String,
        bio: String,
        image: String,
    ) {
        this._id = id
        this._username = username
        this._bio = bio
        this._image = image
    }
}