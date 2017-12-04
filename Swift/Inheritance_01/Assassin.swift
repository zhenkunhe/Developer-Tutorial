

import Foundation

class Assassin:Role {
    override func didDefense(atk:Float) -> Void {
        if isDodge() {
            println("「\(self.name)」 成功閃避了襲來的攻擊.")
        }
        else {
            super.didDefense(atk)
        }
    }
    
    func isDodge(Void) -> Bool{
        return Int(arc4random()) % 4 == 0 ? true : false
    }
}