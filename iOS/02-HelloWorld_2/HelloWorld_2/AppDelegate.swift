import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate  {    //最重要的兩個元件
    //UIApplicationDelegate 內部設定了appa生命週期觸發的函式

    var window: UIWindow?   //root視窗物件,不包含container

    //啟動瞬間觸發
    func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
        
        window = UIWindow(frame: UIScreen.mainScreen().bounds)  //取得靜態物件screen的寬高
        window?.backgroundColor = UIColor.lightGrayColor()      //設置背景顏色
        window!.makeKeyAndVisible()                             //window可視化
        
        println("application");
        return true
    }

    //即將終止活動 觸發
    func applicationWillResignActive(application: UIApplication) {
        println("applicationWillResignActive");
    }

    //進入後台完成 觸發
    func applicationDidEnterBackground(application: UIApplication) {
        println("applicationDid Enter Background");
    }

    //即將進入前端
    func applicationWillEnterForeground(application: UIApplication) {
        println("application Will Enter Foreground");
    }

    //app開始活動觸發
    func applicationDidBecomeActive(application: UIApplication) {
        println("application Did Become Active");
    }
    
    //app釋放的瞬間觸發
    func applicationWillTerminate(application: UIApplication) {
        println("application Will Terminate");
    }


}

