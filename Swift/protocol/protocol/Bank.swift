
import Foundation

class Bank {
    var _money:Int = 0
    let _bankD:BankDelegate?
    init (money:Int , bankD:BankDelegate){
        _bankD = bankD
        _money = money
    }
    init (money:Int){
        _money = money
    }
    
    func withDrawal(value:Int) -> Void{
        if _money >= value {
            _money -= value
            _bankD!.showBalance(_money)
        }
        else {
            _bankD!.showBroken()
        }
    }
    func deposit(value:Int) -> Void{
        _money += value
        _bankD!.showBalance(_money)
    }
}
