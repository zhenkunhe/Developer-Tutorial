import UIKit

class ViewController: UIViewController {
    var m_scrollerView:UIScrollView!
    
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        m_scrollerView = UIScrollView(frame: CGRectMake(0, 0, frame.size.width, frame.size.height))
        m_scrollerView.backgroundColor = UIColor.clearColor()
        m_scrollerView.contentSize = CGSizeMake(frame.size.width, 1000)
        self.view.addSubview(m_scrollerView)
        
        //取得plist的內容
        var infoDictionary:NSDictionary = NSBundle.mainBundle().infoDictionary!
        if let data:String = infoDictionary["CFBundleName"] as? String {
            createNewLableWithInfo(data, title: "Bundle Name")
        }
        if let data:String = infoDictionary["Zhenkun"] as? String {
            createNewLableWithInfo(data, title: "Zhenkun")
        }
        
        //電池剩餘電量
        var remain:Float = UIDevice.currentDevice().batteryLevel
        createNewLableWithInfo("\(remain)", title: "剩餘電量")
        
        //電池狀態
        var stateArray:[String] =  ["未知","未插上電源","充電中","Full"]
        createNewLableWithInfo(stateArray[UIDevice.currentDevice().batteryState.rawValue], title: "電池狀態")
        
        //調節螢幕亮度（所有系統亮度都變暗)（模擬器看不出來）
        var slider:UISlider = UISlider()
        slider.center = CGPointMake(frame.size.width / 2, 400)
        slider.minimumValue = 0
        slider.maximumValue = 1
        slider.value = 1
        slider.addTarget(self, action: "onSliderAction:", forControlEvents: UIControlEvents.ValueChanged)
        m_scrollerView.addSubview(slider)
        
        //感應接近物體
        var switchPassC:UISwitch = UISwitch(frame: CGRectZero)
        switchPassC.transform = CGAffineTransformMakeScale(1.3, 1.3)
        switchPassC.center = CGPointMake(frame.size.width / 2 , slider.center.y + 50)
        switchPassC.onTintColor = UIColor.greenColor()
        switchPassC.on = false
        switchPassC.addTarget(self, action: "switchAction:", forControlEvents: UIControlEvents.ValueChanged)
        m_scrollerView.addSubview(switchPassC)
        
        //隱藏state bar
        var stateBarBtn:UIButton = UIButton(frame: CGRectMake(0, 0, frame.size.width, 50))
        stateBarBtn.center = CGPointMake(frame.size.width / 2 , switchPassC.center.y + 50)
        stateBarBtn.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        stateBarBtn.setTitle("狀態列顯示", forState: UIControlState.Normal)
        stateBarBtn.titleLabel?.font = UIFont.boldSystemFontOfSize(50)
        stateBarBtn.tag = 1
        m_scrollerView.addSubview(stateBarBtn)
    }
    
    var lableCount:Int = 0
    var lableH:CGFloat = 60
    
    func createNewLableWithInfo(info:String,title:String){
        var newLable:UILabel = UILabel(frame: CGRectMake(0, lableH * CGFloat(lableCount), self.view.frame.size.width, lableH))
        newLable.textColor = UIColor.whiteColor()
        newLable.font = UIFont.systemFontOfSize(50)
        newLable.textAlignment = NSTextAlignment.Left
        newLable.adjustsFontSizeToFitWidth = true
        newLable.text = "\(title):\(info)"
        m_scrollerView.addSubview(newLable)
        lableCount++
    }
    
    func openProximityMonitoring(isOpen:Bool){
        var device:UIDevice = UIDevice.currentDevice()
        //沒有可以監測是否有proximityMonitor的方法,要看proximityMonitoringEnabled是否true
        if isOpen{
            device.proximityMonitoringEnabled = true
            NSNotificationCenter.defaultCenter().addObserver(self, selector: "proximityChanged:", name: UIDeviceProximityStateDidChangeNotification, object: device)
        }else{
            device.proximityMonitoringEnabled = false
            NSNotificationCenter.defaultCenter().removeObserver(self, name: UIDeviceProximityStateDidChangeNotification, object: nil)
        }
    }
    
    func proximityChanged(device:UIDevice){
        println("你的距離感應器已經開啟！")
    }
    
    //MARK: - Listener
    //-------------------------------
    
    func onSliderAction(sender:UISlider){
        UIScreen.mainScreen().brightness = CGFloat(sender.value)
    }
    
    func switchAction(sender:UISwitch){
        openProximityMonitoring(sender.on)
    }
    
    func onButtonAction(sender:UIButton){
        sender.tag = sender.tag == 0 ? 1 : 0
        sender.setTitle(sender.tag == 0 ? "狀態列隱藏" :"狀態列顯示", forState: UIControlState.Normal)
        //要在plist上面打一行View controller-based status bar appearance的屬性
        UIApplication.sharedApplication().setStatusBarHidden(sender.tag == 0 ? true : false, withAnimation: UIStatusBarAnimation.Slide)
    }
}

