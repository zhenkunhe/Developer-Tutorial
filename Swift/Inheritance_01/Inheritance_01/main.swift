
import Foundation

/*
class Bank {
    var money:Int = 0
    func showInfo() {
        println("餘額：\(money)")
    }
    func deposit (value:Int) -> Void {
        self.money += value
        showInfo()
    }
}

class Atm: Bank {
    func withDrawal (value:Int) ->Void{
        if money >= value{
            self.money -= value
            showInfo()
        }
        else {
            println("餘額不足！")
        }
        
    }
}

var atm:Atm = Atm()
atm.deposit(100)
atm.deposit(100)
atm.withDrawal(200)
atm.withDrawal(200)
*/

/*
class BaseObject {
    var l:Float = 0 , w:Float = 0
    func getSize (Void) ->Float{
        return l * w
    }
}

class Rect:BaseObject {
}

class Circle:BaseObject {
    var r:Float = 0.0
    override func getSize (Void) ->Float{
        return Float (pow( Double(r) , 2.0 ) * M_PI)
    }
}

class Cube:BaseObject {
    var h:Float = 0.0
    override func getSize (Void) ->Float{
        return super.getSize() * h
    }
}

var rect:Rect = Rect()
var circle:Circle = Circle()
var cube:Cube = Cube()

rect.l = 10
rect.w = 20
println("\(rect.getSize())")

circle.r = 10
println("\(circle.getSize())")

cube.l = 10
cube.w = 20
cube.h = 30
println("\(cube.getSize())")
*/

var b:Role = Knight()
var c:Role = Assassin()

b.hp = 620
b.atk = 80
b.def = 35
b.name = "Knight"

c.hp = 580
c.atk = 89
c.def = 25
c.name = "Assasin"

func pk(inv:Role,def:Role) -> Bool {
    inv.didAttack(def)
    if def.isDead(){
        inv.win++
        def.lose++
        println("「\(def.name)」死亡.")
        return true
    }
    return false
}

var coin:Int = Int(arc4random() % 2)
var rd:Int = 0

while ( !(coin++ % 2 == 0 ?  pk(b,c) : pk(c,b)) ){
    println("第\(++rd)回合 結束")
    println()
}//end while

println()
println("「\(b.name)」贏：\(b.win)場,輸了\(b.lose)場")
println("「\(c.name)」贏：\(c.win)場,輸了\(c.lose)場")

