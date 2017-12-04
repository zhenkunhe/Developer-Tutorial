
import Foundation

/*
var sum:Int = 0
for (var i:Int = 1 ; i <= 1000 ; i++) {
    if (i % 9 == 0 && i % 32 == 0) {
        sum++
    }
}
println("\(sum)")
*/

/*
for (var i:Int = 10 ; i >= 0 ; i--) {
    println(i <= 0 ? "time out" : "\(i)")
}
*/

/*
var amount:Int = 0 , start:Int = 10 , end:Int = 20
for i in start ... end {
    amount += i
}
println("\(amount)")
*/

/*
var sum:Int = 0 , i:Int = 1;

while i <= 10 {
    sum += i++
}
println("\(sum)")
*/

/*
var amount:Int = 0 , start:Int = 10 , end:Int = 20 , i:Int = start
while i <= end {
    amount += i++
}
println("\(amount)")
*/

var level:Int = 10
for var i:Int = 1 ; i <= level ; i++ {
    for var j:Int = 1 ; j <= level-i ; j++ {
        print(" ")
    }
    for var j:Int = 1 ; j <= ( i * 2 - 1 ) ; j++ {
        print("*")
    }
    
    println("")
}




