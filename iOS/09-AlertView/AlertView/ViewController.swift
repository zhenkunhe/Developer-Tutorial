import UIKit

class ViewController: UIViewController ,UIAlertViewDelegate {

    var buyBtn:UIButton!
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.grayColor()
        
        buyBtn = UIButton(frame: CGRectMake(0, 0, 180, 100))
        buyBtn.backgroundColor = UIColor.redColor()
        buyBtn.center = self.view.center
        buyBtn.setTitle("購買", forState: UIControlState.Normal)
        buyBtn.titleLabel?.font = UIFont.systemFontOfSize(40)   //裡面放的是一個lable,設定lable的Font大小
        buyBtn.addTarget(self, action: Selector("onClickListener:"), forControlEvents: UIControlEvents.TouchDown)
        view.addSubview(buyBtn)
    }
    
    //效能「快不足」的時候跳出警告（是否有某些View需要釋放）
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func onClickListener(btn:UIButton){
        var alert:UIAlertView = UIAlertView(title: "你好", message: "今天吃飽了嗎？", delegate: self, cancelButtonTitle: "結束", otherButtonTitles: "確定", "來鬧的")
        alert.show()
    }
    
    func alertView(alertView: UIAlertView, clickedButtonAtIndex buttonIndex: Int) {
        if alertView.tag == 1 { //如果是已經進來過的alert,就不要再執行了
            return
        }
        let alert:UIAlertView = UIAlertView(title: "再見", message: "!", delegate: self, cancelButtonTitle: "結束")
        alert.tag = 1   //防止delegate又放self的時候,再次進來此函式
        switch buttonIndex {
        case 0:
            alert.message = "See U again!"
        case 1:
            alert.message = "精神飽滿！"
        case 2:
            alert.message = "FUCK"
        default:
            break
        }
        alert.show()
    }
}

