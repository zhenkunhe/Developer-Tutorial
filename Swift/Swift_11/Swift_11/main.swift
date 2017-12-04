import Foundation

/*
struct Ball {
    var x:Int = 0
    var y:Int = 0
    var z:Int = 0
    var name:String = ""
}

var b1:Ball = Ball()
var b2:Ball = Ball(x: 1, y: 2, z: 3, name: "blueBall")

b1 = b2
b1.x = 100

println("\(b2.x)")
*/



/*
struct Point {
    var X:Float = 0.0
    var Y:Float = 0.0
}

struct Size {
    var w:Float = 0.0
    var l:Float = 0.0
}

struct Rect {
    var origin:Point = Point()
    var size:Size = Size()
    var center:Point{                           //Swift的struct有能力將「屬性」與「屬性」之間建立關係  利用「get」&「set」
        get {
            let centerX = origin.X + (size.w/2)
            let centerY = origin.Y + (size.l/2)
            return Point(X:centerX  ,Y:centerY)
        }
        set (newCenter) {
            self.origin.X = newCenter.X - (size.w/2)
            self.origin.Y = newCenter.Y - (size.l/2)
        }
        
    }
    var area:Float{         //只有get沒有set
        return size.w * size.l
    }
}

var rect:Rect = Rect(origin: Point(X: 20, Y: 30), size: Size(w: 50, l: 50))         //測試get

rect.center = Point(X: 60, Y: 60)       //測試set
rect.center.X = 70                      //set進階版 此行等於rect.center = Point(X: 70, Y: 不變)
//rect.area = 0.0                       //get only的屬性沒有辦法set

println("原點：(\(rect.origin.X),\(rect.origin.Y))\n範圍：(\(rect.size.w),\(rect.size.l))\n中心：(\(rect.center.X),\(rect.center.Y))\n面積：\(rect.area)")
*/


struct ScoreRecord{
    var scroe:Int = 2{          //若「屬性」與其他「屬性」沒有關聯,則有個多餘的方式,willSet＆didSet 幾乎沒用   且didSet中有特定的關鍵字:oldValue
        willSet(newScore){
            println("傳入新分數\(newScore)")
        }
        didSet {
            println(scroe >= oldValue ? "進步了\(scroe - oldValue)分" : "退步了\(oldValue - scroe)分")   //didSet屬性設定中的特定關鍵字:oldValue
        }
    }
}

var score:ScoreRecord = ScoreRecord()

score.scroe = 10