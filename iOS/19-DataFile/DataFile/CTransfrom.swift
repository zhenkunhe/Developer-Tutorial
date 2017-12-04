import UIKit

private var m_aryAccountData:[[String]]!
private var m_accountDataPath:String!

class CTransfrom: NSObject {
    class var aryAccountData:[[String]]{
        get{return m_aryAccountData}
        set{m_aryAccountData = newValue}
    }
    
    class var path:String{
        get{return m_accountDataPath}
        set{m_accountDataPath = newValue}
    }
    
    class func filePath(name:String) -> String{
        //用NSSearchPathForDirectoriesInDomains（不可實體化）來幫助我們找到「使用者領域」下的「檔案資料夾」路徑
        //回傳預設就是「字串陣列」,即便只有一個路徑
        var path:NSArray = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory,NSSearchPathDomainMask.UserDomainMask, true)
        var doc:String = path[0] as String
        //String提供「路徑」銜接用的方法NSSearchPathForDirectoriesInDomains
        return doc.stringByAppendingPathComponent(name)
    }
    
    class func loadDatas() {
        //取出檔案accountData.plist的路徑,任何檔案名稱都一樣,最後存成plist檔案（類似xml格式）
        m_accountDataPath = self.filePath("accountData.plist")
        
        //用NSFileManager（不可實體化）來幫助我們找到此路徑下的檔案是否存在,若不存在...則建立空資料
        if !(NSFileManager.defaultManager().fileExistsAtPath(m_accountDataPath)){
            var aryNewData:[[String]] = [[String]]()
            //ObjC下的NSArray可以直接writeToFile
            //裡面的atomically設為true代表傳輸有緩衝區,完整傳輸某一大小後,才覆蓋舊檔案
            (aryNewData as NSArray).writeToFile(m_accountDataPath, atomically: true)
        }
        //NSURL是ObjC下的檔案路徑使用
        //同樣可以反序列化回來,用「建構子」打入參數
        m_aryAccountData = NSArray(contentsOfFile: m_accountDataPath)! as Array
    }
    
    class func refreshAccountData() {
        (m_aryAccountData as NSArray).writeToFile(m_accountDataPath, atomically: true)
    }
}
