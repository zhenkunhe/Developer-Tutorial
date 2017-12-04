
import Foundation


//var 身高:Float = 173.5
//var 體重 = 65.1

/*
var age:Int = 18
var height:Float = 173.5
var weight = 65.1       //不一定需要宣告型態,但最好宣告
let name:String = "坤"  //final

age = 20
height = 160
weight = 50

println("年紀\(age)歲,身高\(height)公分,"+"體重\(weight)公斤")
//println("年紀"+String(age)+"歲,身高"+String(身高)+"公分,"+"體重"+String(體重)+"公斤")  //中文變數名稱有轉型之類的bug
*/

/*
let minIntU8:UInt8 = UInt8.min  //byte
let maxIntU8:UInt8 = UInt8.max  //byte
println("最小值＝\(minIntU8)歲,最大值=\(maxIntU8)")
*/

/*
var dValue:Double = 100.999
var fValue:Float = Float(dValue)    //必須強制轉型
var iValue:Int = Int(fValue)

println("Double＝\(dValue),Float=\(fValue),Int=\(iValue)")
*/

/*
var number:Int?     //未定義內容的空間,直接印出會變成nil 效果等同宣告指標卻還沒分配空間
number = 100
println("number＝\(number)")
*/

var boolTrue:Bool = true
var boolFalse:Bool = false
println("boolTrue＝\(boolTrue),boolFalse=\(boolFalse)")
assert(boolFalse, "發生錯誤")   //斷程式專用,防惡意軟體 不常拿來debug
