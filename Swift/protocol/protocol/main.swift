
import Foundation

/*
protocol Rect {
    func getPerimeter(w:Int ,l:Int) -> Int
    func getArea(w:Int , l:Int) -> Int
}

protocol Circle {
    func getPerimeter(r:Float) -> Float
    func getArea(r:Float) -> Float
}

class Object: Rect,Circle{     //不能多重繼承,但是可以多重實作.若要繼承,父類別需要寫在冒號後的第一個

    func getPerimeter(w:Int ,l:Int) -> Int {
        return (l + w) * 2
    }
    func getArea(w:Int ,l:Int) -> Int {
        return l * w
    }
    func getPerimeter(r:Float) -> Float {
        return r * r * Float(M_PI)
    }
    func getArea(r:Float) -> Float {
        return 2 * r * Float(M_PI)
    }
}

var obj:Object = Object()

println("圓面積：\(obj.getArea(3)),圓周長：\(obj.getPerimeter(3))")
println("Rect面積：\(obj.getArea(3, l: 4)),Rect周長：\(obj.getPerimeter(3,l: 4))")    //參數第一個不需要形容意義(init 例外)

*/

/*
class Main:BankDelegate {
    init(Void){
        var bank:Bank = Bank(money: 1000, bankD: self)
        bank.withDrawal(300)
        bank.deposit(200)
        bank.withDrawal(1000)
    }
    func showBroken() -> Void{
        println("你已經破產了")
    }
    func showBalance(money:Int) -> Void{
        println("你還剩下\(money)元")
    }
}
*/

class Main:BankDelegate {
    init(Void){
        var bank:Bank = Bank(money: 1000, bankD: self)
        bank.withDrawal(300)
        bank.deposit(200)
        bank.withDrawal(1000)
    }
    func showBroken() -> Void{
        println("你已經破產了")
    }
    func showBalance(money:Int) -> Void{
        println("你還剩下\(money)元")
    }
}

var main:Main = Main()



