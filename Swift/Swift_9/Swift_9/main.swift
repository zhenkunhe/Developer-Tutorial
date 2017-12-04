
import Foundation

/*
func getNewP (money:Int) -> () -> Int{
    var salary:Int = 0
    func workADay() -> Int {
        salary += money
        return salary
    }
    return workADay
}

let emp_1 = getNewP(700), emp_2 = getNewP(800) , emp_3 = getNewP(900)
//由於emp_1為workADay()的實體,而workADay()實體緊抓著屬於自己的salary,故不得釋放salary的memory

for i in 1 ... 2 {
    println("第\(i)天員工1的薪水 ＝ \(emp_1()) 元")
    println("第\(i)天員工2的薪水 ＝ \(emp_2()) 元")
    println("第\(i)天員工3的薪水 ＝ \(emp_3()) 元")
}
*/

/*
func a() -> Void {
    println("這是Ａ函式")
}

func b() -> Void {
    println("這是B函式")
}

func countDown ( start:Int , timesupClosure:() -> Void ) -> Void{
    for var i = start ; i >= 0 ; i-- {
        if( i > 0){
            println("倒數\(i)秒")
        }
        else{
            timesupClosure()
        }
    }
}

countDown(10, a)
countDown(10, b)
countDown(10, {
        println("這是Ｃ函式")            //臨時函數如果沒有參數,沒有回傳,不需要多餘的in
    }
)
*/

func babyName (secName:String , firNameFunc:(String) -> String) -> Void{
    println("寶寶的名字叫\(firNameFunc(secName))")
}
/*
babyName("賀",
    {(secName : String) -> String in    //「臨時函式」的宣告規矩 一切宣告完以後要加「in」 爾後的區塊才是「函式本體」
        return secName + "振坤"
    }
)

babyName("賀")
{(secName : String) -> String in        //「臨時函式」若當「參數」,可寫在「呼叫此臨時函式」的「後方」
    return secName + "振坤"
}

babyName("賀"){return $0 + "振坤"}       //「臨時函式」若當「參數」,此「臨時函式」可「使用呼叫者的參數」
*/

babyName("賀",{$0 + "振坤"})             //「臨時函式」若當「參數」,則可直接將return的結果打入{}內,當作呼叫者的參數
                                        // 若此寫法的「臨時函式」回傳不帶參數,會認為此「臨時函式」是不需要輸入參數的函式

