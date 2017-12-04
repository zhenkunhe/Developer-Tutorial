import UIKit

class LoggedPage: UIViewController {
    weak var m_parentObj:ViewController?  //弱參考,若隨時要釋放,沒有權力阻止
    var callBtn:UIButton!
    
    override func viewDidAppear(animated: Bool) {
        showAlert("登入成功", message: "歡迎", cancelButtonTitle: "確定")
    }
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        callBtn = UIButton(frame: CGRectMake(0, 0, 70, 50))
        callBtn.setTitle("返回", forState: UIControlState.Normal)
        callBtn.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        
        self.view.addSubview(callBtn)
    }
    
    func showAlert(title:String , message:String , cancelButtonTitle:String){
        UIAlertView(title: title, message: message, delegate: nil, cancelButtonTitle: cancelButtonTitle).show()
    }
    
    func onButtonAction(senter:UIButton){
        if m_parentObj != nil{
            m_parentObj?.onDoneCallBack(self)
        }
    }
}
