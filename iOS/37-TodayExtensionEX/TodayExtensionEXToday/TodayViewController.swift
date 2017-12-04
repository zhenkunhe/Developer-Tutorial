import UIKit
import NotificationCenter

//loading時間太長,單一圖片太大張,會loading失敗
//此Target或是iWatch Target上架時,一定要綁定一個手機應用Target
class TodayViewController: UIViewController, NCWidgetProviding {
        
    override func viewDidLoad() {
        super.viewDidLoad()
        
        var IS_IPAD:Bool = false
        //如果是Ipad,不能使用全螢幕寬,最高打592
        self.preferredContentSize = CGSizeMake(IS_IPAD ? 592 : self.view.frame.size.width, 400)
        
        var saveBtn:UIButton = UIButton(frame: CGRectMake(0, 50, 50, 100))
        saveBtn.setTitle("Save", forState: UIControlState.Normal)
        saveBtn.titleLabel?.font = UIFont.systemFontOfSize(20)
        saveBtn.tag = 0
        saveBtn.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        self.view.addSubview(saveBtn)
        
        var loadBtn:UIButton = UIButton(frame: CGRectMake(self.view.frame.size.width - 50, 50, 50, 100))
        loadBtn.setTitle("Load", forState: UIControlState.Normal)
        loadBtn.titleLabel?.font = UIFont.systemFontOfSize(20)
        loadBtn.tag = 1
        loadBtn.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        self.view.addSubview(loadBtn)
    }
    
    override func touchesEnded(touches: NSSet, withEvent event: UIEvent) {
        if self.preferredContentSize.height <= 50 {
            self.preferredContentSize = CGSizeMake(self.preferredContentSize.width, 400)
        }else{
            self.preferredContentSize = CGSizeMake(self.preferredContentSize.width, 50)
        }
    }
    
    //刷新共用資訊
    func renewContainerData(){
        //取得Plist檔案
        if(NSFileManager.defaultManager().containerURLForSecurityApplicationGroupIdentifier("手機") != nil){
            //取得Plist檔案
            let sharedDefaults = NSUserDefaults(suiteName: "手機")
            sharedDefaults?.setObject("dddd", forKey: "ResultPanelNumber")
            sharedDefaults?.setObject("ddd", forKey: "currentOperationValue")
            sharedDefaults?.synchronize()
        }
    }
    
    //將共用資訊匯入專案呈現
    func loadContainerData(){
        if(NSFileManager.defaultManager().containerURLForSecurityApplicationGroupIdentifier("手機") != nil){
            let sharedDefaults = NSUserDefaults(suiteName: "手機")
            if sharedDefaults?.objectForKey("ResultPanelNumber") == nil {
                return  //第一次開啟可能是nil,無需取值
            }
        }
    }
    
    func onButtonAction(sender:UIButton){
        
    }

    //每次拉上去就關閉此程式,需要在此更新資料
    func widgetPerformUpdateWithCompletionHandler(completionHandler: ((NCUpdateResult) -> Void)!) {
        completionHandler(NCUpdateResult.NewData)
    }
    
    //滿版無邊界
    func widgetMarginInsetsForProposedMarginInsets(defaultMarginInsets: UIEdgeInsets) -> UIEdgeInsets {
        return UIEdgeInsetsZero
    }
    
}
