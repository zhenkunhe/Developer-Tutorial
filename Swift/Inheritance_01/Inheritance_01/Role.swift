

import Foundation

class Role {
    var name:String = ""
    var hp:Float = 0.0 , atk:Float = 0.0 , def:Float = 0.0
    var win:Int = 0 , lose:Int = 0
    var era:Float{return def / (def + 100)}
    
    func didAttack (target:Role) -> Void {
        println("「\(self.name)」 對 「\(target.name)」 進行攻擊")
        target.didDefense(self.atk)
    }
    
    func didDefense(atk:Float) -> Void {
        var hurt:Float = atk * ( 1 - era )
        
        self.hp = (hurt > self.hp) ? 0.0 : self.hp - hurt
        println("「\(self.name)」受到\(hurt)點傷害.")
        println("「\(self.name)」剩餘\(self.hp)ＨＰ.")
    }
    
    func isDead (Void) -> Bool {
        if hp == 0 {
            return true
        }
        else{
            return false
        }
    }
}



