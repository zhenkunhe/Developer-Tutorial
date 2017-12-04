import Foundation
/*
var apple:[String] = ["iPad","iPhone"]
let newProduct:[String] = ["ipod","Mac"]
//println("Apple目前產品有\(apple[0])與\(apple[1])")

apple += newProduct
apple.append("iWatch")
apple += ["Mac Book Pro"]
apple.insert( "magic mouse",  atIndex: 2 )  //特定格式

let r_1 = apple.removeAtIndex(2)
let r_2 = apple.removeLast()

for (index,product) in enumerate(apple) {   //陣列 列舉法
    println("目前第\(index)項產品是\(product)")
}

apple.removeAll(keepCapacity: true)

if apple.isEmpty {
    println("clean")
}
*/

/*
var htc:[String] = [String]()
htc.append("one")
htc += [String](count: 3, repeatedValue: "尚未推出")

for (index,product) in enumerate(htc) {
    println("目前第\(index)項產品是\(product)")
}
*/

/*
var sony:Array<String> = Array<String>(count: 4, repeatedValue: "即將推出") //Array集合 字串版型
sony[0 ... 2 ] = ["z1","z2","z3"]   //加入元素
for (index,product) in enumerate(sony) {
    println("目前第\(index)項產品是\(product)")
}
*/

/*
var acer:NSArray = NSArray(objects: "a_1","a_2",88)     //槓Object C的Array,此宣告為final值,也就是內容不能替換
for (index,product) in enumerate(acer) {
    println("目前第\(index)項產品是\(product)")
}
*/

var mi:NSMutableArray = NSMutableArray()
mi.addObject("紅米機")
println("\(mi.objectAtIndex(0))")


var ary = [[4,7,6,9],[1,2,3],[4,5,6,7,8]]
/*
for var i:Int = 0 ; i<ary.count ;i++ {
    for var j:Int = 0 ; j<ary[i].count ;j++ {
        print("\(ary[i][j])\t")
    }
    println()
}
*/

for elements in ary {
    for element in elements {
        print("\(element)\t")
    }
    println("")
}