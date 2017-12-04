import Foundation

/*
var apple:Dictionary<String,Int> = ["iPhone":26000,"iPad":18000]
apple["iWatch"] = 16800

for element in apple.keys{
    println("商品\(element) = \(apple[element])")
}

var max:Int = 0
for element in apple.values{
    if max < element {
        max = element
    }
}

println("最貴為\(max)元")
*/

var people:Dictionary<String,[String]> = [ "賀振坤" : ["吳國隆","王大維"] , "王勇傑" : ["賀振坤","王大維"] ]

var temp:[String] = people["賀振坤"]!
people["賀振坤"] = people["王勇傑"]
people["王勇傑"] = temp

for (person,friends) in people{
    println("我的名字叫做\(person)")
    for friend in friends {
        print("我的朋友有\(friend)\t")
    }
    println("")
}








