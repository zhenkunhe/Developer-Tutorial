import Foundation

protocol BankDelegate {
    func showBroken() -> Void
    func showBalance(money:Int) -> Void
}