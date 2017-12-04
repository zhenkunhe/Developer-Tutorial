
import Foundation

/*
class Position{
    var X:Float = 0.0
    var Y:Float = 0.0
    var Z:Float = 0.0
}

var ballA:Position = Position()
var ballB = ballA
ballB.X = 60

println("X:\(ballA.X),Y:\(ballA.Y),Z:\(ballA.Z)")
*/

/*
struct Position {
    var X:Float = 0.0
    var Y:Float = 0.0
    var Z:Float = 0.0
}

class Cube {
    var m_pos:Position = Position()
    var m_l:Float
    var m_w:Float
    var m_h:Float
    
    init (var p:Position , l:Float , w:Float , h:Float){
        m_pos = p
        m_l = l
        m_w = w
        m_h = h
    }
}

var cubeA:Cube = Cube(p: Position(), l: 200.0 , w: 300.0 , h: 1000.0)
//var cubeB:Cube = Cube()   //error 建構子被覆蓋

println("體積：\(cubeA.m_l * cubeA.m_w * cubeA.m_h)")

*/

/*
class DataImporter{
    var path:String = "path.txt"
}

class DataManager{
    lazy var dataImp:DataImporter = DataImporter()      //lazy:物件尚不用到時,不建立實體
    var data:[String] = [String]()
}

let dataManager:DataManager = DataManager()
dataManager.data.append("Data_1")
dataManager.data.append("Data_2")

println("將資料存到\(dataManager.dataImp.path)裡面")
*/






