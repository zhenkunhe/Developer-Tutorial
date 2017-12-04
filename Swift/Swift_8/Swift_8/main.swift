

import Foundation

/*
func sayHello(){
    println("Hello, World!")
}
sayHello()
*/

/*
func showName(name:String){
    println("My name is \(name)")
}
showName("zhenkun")
*/

/*
func printRect(w:Int,l:Int){
    for y in 1 ... l {
        for x in 1 ... w{
            print("*")
        }
        println("")
    }
}
//printRect(6,3)
for x in 1 ... 4 {
    printRect(x,x)
}
*/


/*
func whatsYourName() -> String {
    return "小王八"
}
println(whatsYourName())
*/

/*
func evenParity(num:Int) -> String {
    return num % 2 == 0 ? "偶數" :"奇數"
}
println("\(evenParity(101))")
*/

/*
func power(x:Int , n:Int) -> Int {
    return n == 1 ? x : x * power(x,n-1)
}
println( power(3,5) )

func classroom() -> (p_1:String , p_2:String ) {
    return ("喔喔","恩恩")
}
    println(classroom().p_1)
*/

/*
func number(num:Int) -> (num1:Int , num2:Int ) {
    return (num / 10, num % 10)
}
println("\(number(23).num1) , \(number(23).num2)")
*/

/*
func coundWord(str:String) -> (vowles:Int,cons:Int,other:Int){
    var vowles = 0 , cons = 0 , other = 0
    for c in str{
        switch String(c).uppercaseString {
        case "A","E","I","O","U" :
            vowles++
        case String(UnicodeScalar(65)) ... String(UnicodeScalar(90)):
            cons++
        default :
            other++
        }
    }
    return (vowles,cons,other)
}
    let number = coundWord("abcdefghijklmnopqrstuvwqxyz!@#$")
    println("\"abcdefghijklmnopqrstuvwqxyz!@#$\"有\(number.vowles)個母音,\(number.cons)個子音,\(number.other)個其他字")

    
func countResult (number a:Int , toAdd b:Int , toSub c:Float ) -> Float {
    return Float(a+b)-c
}
    println("\(countResult(number:1,toAdd:3,toSub:2.2))")
    
func discountResult (price p:Int , discount d:Float = 10 ) -> Float {
    return Float(p) * (d / 10)
}

println("\(discountResult(price:100))")
*/

/*
func avarageNum (nums:Float ...) -> Float {
    var sum:Float = 0.0
    for i in nums {
        sum += i
    }
    return sum / Float(nums.count)
}
println("\(avarageNum(1,2,3,4,5))")
*/

/*
func connectContent(var s_1:String , s_2:String , s_3:String ) -> String{
    s_1 += s_2 + s_3
    return s_1
}
var str:String = connectContent("a","b","c")
println("\(str)")
*/

/*
func swap(inout num1:Int , inout num2:Int ) {
    var temp:Int = num1
    num1 = num2
    num2 = temp
}

var a:Int = 1, b:Int = 2
swap(&a,&b)

println("\(a),\(b)")
*/

/*
func toAdd( num1:Int , num2:Int ) -> Int{
    return num1 + num2
}

func toSub( num1:Int , num2:Int ) -> Int{
    return num1 - num2
}

func countAddAndSub( a:Int , b:Int , fun_add:(Int,Int) -> Int , fun_sub:(Int,Int) -> Int ) -> (Int,Int) {
    return ( fun_add(a,b) , fun_sub(a,b) )
}

var a = 20 , b = 10
//var mathFuncAdd:(Int,Int)->Int = toAdd
//var mathFuncSub:(Int,Int)->Int = toSub
var result = countAddAndSub( a , b , toAdd , toSub)
//result = countAddAndSub( a , b , mathFuncAdd , mathFuncSub)
println("add = \(result.0),sub = \(result.1)")
*/

func toAdd( num1:Int , num2:Int ) -> Int{
    return num1 + num2
}

func toSub( num1:Int , num2:Int ) -> Int{
    return num1 - num2
}

func switchFunc( choice:Int ) -> (Int,Int) -> (Int) {
    func toMul( num1:Int , num2:Int ) -> Int {
        return num1 * num2
    }
    func toDiv( num1:Int , num2:Int ) -> Int {
        return num1 / num2
    }
    var mathFunc:(Int,Int)->Int = toAdd
    switch choice {
        case 1:
            mathFunc = toAdd
        case 2:
            mathFunc = toSub
        case 3:
            mathFunc = toMul
        case 4:
            mathFunc = toDiv
        default:
            break
    }
    return mathFunc
}

var choice = 4 , a = 20 , b = 10
var mathFunc:(Int,Int)->Int = switchFunc(choice)
var result = mathFunc(a,b)
//var result = switchFunc(choice)(a,b)
println("結果 = \(result)")
