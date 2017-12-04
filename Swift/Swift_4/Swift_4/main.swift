
import Foundation

/*
var num:CInt = 0;
println("請輸入任意整數")
getImput(&num);

if Int(num) % 2 == 1 {
    println("奇數")
}
else {
    println("偶數")
}
*/

/*
var t_1:Int = 10,t_2:Int = 20,t_3:Int = 10
if t_1 == t_2 && t_2 == t_3 {
    println("正三角形")
}
else if t_1 == t_2 || t_2 == t_3 || t_1 == t_3{
    println("等腰三角形")
}
else {
    println("？")
}
*/

/*
var inCome:CInt = 0 , deposite:CInt = 0 ,spending:CInt = 0 ;
println("本金＝")
getImput(&deposite)
println("開銷＝")
getImput(&spending)
println("營收＝")
getImput(&inCome)


if spending > inCome {
    println("本季虧本")
}
else {
    println("本季賺錢")
}
*/


/*
var ID:Int = 3
switch ID {
    case 1:
        println("1")    //case 若進入不需要加入break
    case 2:
        println("2")
    case 3 ... 10:
        println("3~10")
        fallthrough     //穿越到下一個case繼續
    case 11 ... 20:
        println("11~20")
    default:
        break           //default必須存在且有功能,若無功能則加入break
}

var say:String = "食べます"
var chinese:String?
    switch say {
    case "Eat","食べます":
        chinese = "吃飯"
    case "Hello","こんにちは":
        chinese = "問安"
    default:
        break
}
if chinese == nil{
    println("?")
}
else {
    println("\(chinese)")
}
*/

/*
var x:Int?
switch true{                //搭配bool值
    case x>1 || x<0 :       //可以邏輯運算,可取代if else
        break;
    default:
    break
}
*/

var cash:Int = 1001
var bank:String = "a"
switch bank.uppercaseString {
case "A":
    println((cash > 1000 ? "success": "fail"))
case "B":
    println((cash > 2000 ? "success": "fail"))
case "C":
    println((cash > 3000 ? "success": "fail"))
default:
    break
}

