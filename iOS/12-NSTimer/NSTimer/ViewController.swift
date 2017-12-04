import UIKit

class ViewController: UIViewController {
    var m_timer:NSTimer!
    var m_timeLabel:UILabel!
    var m_secondAmount:Int = 0
    var m_timesPerSecond:Int = 0
    
    override func viewDidAppear(animated: Bool) {
        m_secondAmount = 3680      //倒數99秒
        m_timesPerSecond = 10    //一秒幾次
        
        //若設定0或是負數,則以0.1取代
        m_timer = NSTimer.scheduledTimerWithTimeInterval(
            1/Double(m_timesPerSecond),
            target: self,
            selector: Selector("onTimeTick:"),
            userInfo: nil,
            repeats: true)
    }
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.grayColor()
        
        //生成時間顯示標籤
        m_timeLabel = UILabel(frame: CGRectMake(0, 0, self.view.frame.size.width, 50))
        m_timeLabel.center = CGPointMake(frame.size.width/2, frame.size.height/2)
        m_timeLabel.font = UIFont.boldSystemFontOfSize(50)
        m_timeLabel.textColor = UIColor.whiteColor()
        m_timeLabel.textAlignment = NSTextAlignment.Center
        m_timeLabel.text = "00:00:00"
        self.view.addSubview(m_timeLabel)
        

    }
    
    func onTimeTick(sender:NSTimer){
        let hour:NSString = "\(m_secondAmount / (60 * 60))"
        let minute:NSString = "\(m_secondAmount % (60 * 60) / 60)"
        let second:NSString = "\(m_secondAmount % (60 * 60) % 60)"
        var aryTime:[NSString] = [hour,minute,second]
        
        for var i = 0 ; i < aryTime.count ; i++ {
            aryTime[i] = aryTime[i].integerValue > 9 ? aryTime[i] : "0\(aryTime[i])"
        }
        
        m_timeLabel.text = "\(aryTime[0]):\(aryTime[1]):\(aryTime[2])"
        
        if m_secondAmount<=0{
            m_timer.invalidate()        //關閉m_timer
            UIAlertView(title: "Times", message: "時間到！", delegate: nil, cancelButtonTitle: "結束").show()
        }
        
        m_secondAmount--
    }
}

