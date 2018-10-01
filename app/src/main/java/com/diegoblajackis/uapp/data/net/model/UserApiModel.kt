package com.diegoblajackis.uapp.data.net.model


class UserApiModel {
    lateinit var name: Name
    lateinit var email: String
    lateinit var picture: Picture
    lateinit var location: Location
    lateinit var dob: Dob

    class Name {
        lateinit var first: String
        lateinit var last: String
    }

    class Picture {
        lateinit var large: String
        lateinit var thumbnail: String
    }

    class Location{
        lateinit var city: String
        lateinit var state: String
        lateinit var street: String
    }

    class Dob {
        lateinit var age: String
    }
}