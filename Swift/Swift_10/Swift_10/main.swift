
import Foundation

enum Language {
    case java
    case swift
    case objC
    case actionScript
    case other
}

/*
var tool:Language = Language.java
switch tool {
    case Language.java:
        println("此為Java")
    case Language.swift:
        println("此為Swift")
    case Language.objC:
        println("此為objC")
    case Language.actionScript:
        println("此為ActionScript")
    default:
        println("此語言為其他語言")
}


var tool:Language = .java
switch tool {
case .java:
    println("此為Java")
case .swift:
    println("此為Swift")
case .objC:
    println("此為objC")
case .actionScript:
    println("此為ActionScript")
default:
    println("此語言為其他語言")
}
*/

/*
enum Role{
    case status(Float,Float,Float)
    case name (String)
}

var roleStatus:Role = Role.status(1000, 599, 200)
var roleName:Role = Role.name("zhenkun")
var role = roleStatus

switch role {
case .status(var hp,var sp,var mp) :
    hp -= 200
    println("Hp:\(hp) Sp:\(sp) Mp:\(mp)")
case let .name(username):
    println("\(username)")
}
*/

enum Classroom : Int {
    case Mary = 3 ,  Ada , Fred = 6 , Eva , Cathy , Diana
}

if let value = Classroom(rawValue: 7){
    println("\(value.rawValue)")
}

println("\(Classroom.Ada.rawValue)")