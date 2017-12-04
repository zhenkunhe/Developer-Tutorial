
import Foundation
/*
struct StaticStruct {
    static var value:String = "ABC"
}
StaticStruct.value += "BCD"
StaticStruct.value += "EFG"
println("\(StaticStruct.value)")
*/

/*
enum StaticEnum {
    static var value:String = "ABC"
}
StaticEnum.value += "BCD"
StaticEnum.value += "EFG"
println("\(StaticEnum.value)")
*/

/*
private var str:String = "ABC"

class StaticClass {
    class var value:String{         //物件的靜態屬性：為了安全起見,限定不行使用static直接呼叫來使用或修改 規定要設定get & set,指向此檔案內的private成員做為共同存取
        get{
        return str
        }
        set (newValue) {
            str = newValue
        }
    }
    class func ccc (Void) -> Void {
        
    }
}

StaticClass.value = "123"
StaticClass.value += "BCD"
StaticClass.value += "EFG"
println("\(StaticClass.value)")
*/

private var acount:Int = 0

struct Classroom{
    static var count:Int = 0
}

class Student {
    var _name:String?
    class var number:Int{
        get{
        return acount
        }
        set (newValue){
            acount = newValue
        }
    }
    init(name:String){
        _name = name
        println("\(_name)走進教室")
        Student.number++            //原始寫法,但也沒有特別屬於此物件所有
        Classroom.count++           //較好的寫法,將所有靜態屬性放入struct   並在使用的時候呼叫就好
    }
    deinit{
        println("\(_name)離開教室")
        Student.number--
        Classroom.count--
    }
}



var s1:Student? = Student(name: "Zhenkun")
var s2:Student? = Student(name: "Alex")

println("教室\(Classroom.count)人")
println("教室\(Student.number)人")

s1 = nil
s2 = nil

println("教室\(Classroom.count)人")
println("教室\(Student.number)人")
