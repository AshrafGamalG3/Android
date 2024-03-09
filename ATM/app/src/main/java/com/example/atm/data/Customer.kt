package com.example.atm.data

data class UserData(
    var firstName: String ,
    var lastName: String,
    var country: String ,
    var address: String ,
    var postalCode: String ,
    var cardNumber: String,
    var cardType: String ,
    var cardExpirationDate: String,
    var balance: Double = 0.0,
    val cardCVV: String
) {
    // Required no-argument constructor
    constructor() : this("", "", "", "", "", "", "", "", 0.0,"")
}
