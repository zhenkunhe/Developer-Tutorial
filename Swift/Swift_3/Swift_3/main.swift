

import Foundation

/*
var a:Int = 10,b:Int = 20,c:Float = 5.9,d:Int = -2
println("\(a)*\(b)=\(a * b)")
println("\(a)/\(b)=\(a / b)")
println("\(a)+\(c)=\(Float(a) + c)")      //變數型別不同的運算,需要注意轉型
println("\(b)%\(c)=\(b % Int(c))")
println("\(b)%\(c)=\(Float(b) % c)")
*/

/*
var str_1:String = "ABCDE"
var str_2:String = "ghijk"
var newString:String = str_1 + str_2
println("\(newString)")
*/

/*
var a:Int = 0 , b:Int = 10
a = ++b
println("a=\(a),b=\(b)")
a = b++
println("a=\(a),b=\(b)")
*/

/*
var a:Int = 10,b:Int = 5,c:Float = 3.5,d:Int = 10
println("\(a)>\(b)?\(a > b)")
println("\(a)<\(b)?\(a < b)")
println("\(a)=\(b)?\(a == b)")
println("\(a)!=\(d)?\(a != d)")     //運算元前後加上空格比較不會出錯（丁丁bug)
println("\(a)>\(b)的反向?\(!(a > b))")
*/

var a:Int = 10 , b:Int  = 20, temp:Int = b
b = a
a = temp
println("a=\(a),b=\(b)")