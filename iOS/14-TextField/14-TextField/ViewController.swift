
import UIKit

class ViewController: UIViewController,UITextFieldDelegate {

    var m_textAccount:UITextField = UITextField()
    var m_textPassword:UITextField = UITextField()
    var m_loginBtn:UIButton = UIButton()
    var m_loggedPage:LoggedPage?
    
    //MARK:-Override
    //-----------------------------------
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        m_textAccount.becomeFirstResponder()
    }
    
    override func touchesEnded(touches: NSSet, withEvent event: UIEvent) {
        m_textAccount.resignFirstResponder()    //關閉鍵盤
        m_textPassword.resignFirstResponder()    //關閉鍵盤
    }
    
    //MARK:-Public
    //-----------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        var width:CGFloat = 150
        m_textAccount.frame = CGRectMake((frame.size.width - width) / 2, 62 , width, 35)
        m_textAccount.text = ""
        m_textAccount.font = UIFont.systemFontOfSize(m_textAccount.frame.size.height * 0.9)
        m_textAccount.backgroundColor = UIColor.whiteColor()
        m_textAccount.textColor = UIColor.blackColor()
        m_textAccount.textAlignment = NSTextAlignment.Center
        m_textAccount.borderStyle = UITextBorderStyle.RoundedRect
        m_textAccount.keyboardType = UIKeyboardType.Default
        m_textAccount.keyboardAppearance = UIKeyboardAppearance.Default
        m_textAccount.returnKeyType = UIReturnKeyType.Next
        //m_textAccount.clearButtonMode = UITextFieldViewMode.WhileEditing
        m_textAccount.placeholder = "帳號"
        m_textAccount.delegate = self
        
        
        m_textPassword.frame = CGRectMake((frame.size.width - width) / 2, m_textAccount.frame.origin.y + m_textAccount.frame.size.height + 20 , width, 35)
        m_textPassword.text = ""
        m_textPassword.font = UIFont.systemFontOfSize(m_textAccount.frame.size.height * 0.9)
        m_textPassword.backgroundColor = UIColor.whiteColor()
        m_textPassword.textColor = UIColor.blackColor()
        m_textPassword.textAlignment = NSTextAlignment.Center
        m_textPassword.borderStyle = UITextBorderStyle.RoundedRect
        m_textPassword.keyboardType = UIKeyboardType.Default
        m_textPassword.keyboardAppearance = UIKeyboardAppearance.Default
        m_textPassword.secureTextEntry = true
        m_textPassword.placeholder = "密碼"
        m_textPassword.delegate = self
        
        m_loginBtn.frame = CGRectMake((frame.size.width - width) / 2, m_textPassword.frame.origin.y + m_textPassword.frame.size.height + 20, width, 35)
        m_loginBtn.backgroundColor = UIColor.darkGrayColor()
        m_loginBtn.setTitle("登入", forState: UIControlState.Normal)
        m_loginBtn.addTarget(self, action: Selector("onButtonAction:"), forControlEvents: UIControlEvents.TouchUpInside)
        
        self.view.addSubview(m_textAccount)
        self.view.addSubview(m_textPassword)
        self.view.addSubview(m_loginBtn)
        
    }
    
    func showAlert(title:String , message:String , cancelButtonTitle:String){
        UIAlertView(title: title, message: message, delegate: nil, cancelButtonTitle: cancelButtonTitle).show()
    }
    
    //MARK:-Delegate
    //-----------------------------------
    
    func textField(textField: UITextField, shouldChangeCharactersInRange range: NSRange, replacementString string: String) -> Bool {
        if textField  == m_textAccount{
            if((countElements(textField.text) + countElements(string)) > 5 && countElements(string) > 0){
                showAlert("警告",message: "帳號不能輸入超過5個字",cancelButtonTitle: "確定")
                return false
            }
        }else {
            if((countElements(textField.text) + countElements(string)) > 8){
                showAlert("警告",message: "密碼不能輸入超過8個字",cancelButtonTitle: "確定")
                return false
            }
        }
        return true
    }
    
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        if textField  == m_textAccount{
            m_textPassword.becomeFirstResponder()
        }
        else{
            m_textPassword.resignFirstResponder()    //關閉鍵盤
        }
        return true
    }
    
    //MARK:-CallBack & Listener
    //-----------------------------------
    
    func onButtonAction(senter:UIButton){
        if m_textAccount.text != "a"{
            self.showAlert("警告", message: "無此帳號", cancelButtonTitle: "確定")
        }
        else if m_textPassword.text != "b"{
            self.showAlert("警告", message: "密碼輸入錯誤", cancelButtonTitle: "確定")
        }
        else{
            if m_loggedPage == nil{
                m_loggedPage = LoggedPage()
                m_loggedPage?.refreshWithFrame(CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height))
                m_loggedPage?.m_parentObj = self
            }
            self.presentViewController(m_loggedPage!, animated: true, completion: nil)  //切換VC
        }
    }
    
    func onDoneCallBack(sender:LoggedPage){
        sender.dismissViewControllerAnimated(true, completion: nil)
    }

}