import UIKit

class LoggedPage: UIViewController {
    var m_username:String!
    
    override func viewDidAppear(animated: Bool) {
        UIAlertView(title: "登入成功", message: "歡迎\(m_username)回來", delegate: nil, cancelButtonTitle: "確定").show()
    }
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
    }
}
