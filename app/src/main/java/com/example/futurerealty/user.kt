package com.example.futurerealty

data class user(
    var username:String,
    var mobilenum:String,
    var emailadress:String,
    var password:String
    //var typeofuser:String
)
{
    // No-argument constructor
    constructor() : this("", "", "", "")
}