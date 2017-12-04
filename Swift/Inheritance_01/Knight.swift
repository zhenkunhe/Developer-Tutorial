
import Foundation


class Knight:Role {
    override func didDefense(atk:Float) -> Void {
        if isParry() {
            println("「\(self.name)」 舉起了盾牌！")
            super.didDefense(atk * 0.5)
        }
        else {
            super.didDefense(atk)
        }
    }
    
    func isParry(Void) -> Bool{
        return Bool( Int(arc4random()) % 2 )
    }
}
