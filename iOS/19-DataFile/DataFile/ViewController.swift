import UIKit

class ViewController: UIViewController {

    
    var m_textAccount:UITextField = UITextField()
    var m_textPassword:UITextField = UITextField()
    var m_loginBtn:UIButton = UIButton()
    var m_registBtn:UIButton = UIButton()
    var m_loggedPage:LoggedPage?
    
    //MARK:-Override
    //-----------------------------------
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.title = "登入頁面"
    }
    
    //MARK:-Public
    //-----------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        var width:CGFloat = 150
        m_textAccount.frame = CGRectMake((frame.size.width - width) / 2, 80 , width, 35)
        m_textAccount.text = ""
        m_textAccount.font = UIFont.systemFontOfSize(m_textAccount.frame.size.height * 0.9)
        m_textAccount.backgroundColor = UIColor.whiteColor()
        m_textAccount.textColor = UIColor.blackColor()
        m_textAccount.textAlignment = NSTextAlignment.Center
        m_textAccount.borderStyle = UITextBorderStyle.RoundedRect
        m_textAccount.keyboardType = UIKeyboardType.Default
        m_textAccount.keyboardAppearance = UIKeyboardAppearance.Default
        m_textAccount.returnKeyType = UIReturnKeyType.Next
        m_textAccount.placeholder = "帳號"
        
        
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
        
        m_loginBtn.frame = CGRectMake((frame.size.width - width) / 2, m_textPassword.frame.origin.y + m_textPassword.frame.size.height + 20, width, 35)
        m_loginBtn.backgroundColor = UIColor.darkGrayColor()
        m_loginBtn.setTitle("登入", forState: UIControlState.Normal)
        m_loginBtn.addTarget(self, action: Selector("onButtonLogin:"), forControlEvents: UIControlEvents.TouchUpInside)
        
        m_registBtn.frame = CGRectMake((frame.size.width - width) / 2, m_loginBtn.frame.origin.y + m_textPassword.frame.size.height + 20, width, 35)
        m_registBtn.backgroundColor = UIColor.darkGrayColor()
        m_registBtn.setTitle("註冊", forState: UIControlState.Normal)
        m_registBtn.addTarget(self, action: Selector("onButtonRegister:"), forControlEvents: UIControlEvents.TouchUpInside)
        
        self.view.addSubview(m_textAccount)
        self.view.addSubview(m_textPassword)
        self.view.addSubview(m_loginBtn)
        self.view.addSubview(m_registBtn)
    }
    
    func showAlertView(message:String){
        UIAlertView(title: "警告", message: message, delegate: nil, cancelButtonTitle: "確定").show()
    }
    
    func showDoneView(message:String){
        UIAlertView(title: "完成", message: message, delegate: nil, cancelButtonTitle: "確定").show()
    }
    
    func isTextEmpty() -> Bool{
        return m_textAccount.text == "" || m_textPassword.text == ""
    }
    
    func clean(){
        m_textAccount.text = ""
        m_textPassword.text = ""
    }
    
    //MARK:-CallBack & Listener
    //-----------------------------------
    
    func onButtonLogin(senter:UIButton){
        if isTextEmpty() {
            showAlertView("你輸入的帳號或密碼不可為空")
            return
        }
        
        var isCheckAccount = false , isCheckPassword = false
        
        //判斷帳號是否已經存在
        for account in CTransfrom.aryAccountData{
            if account[0] == m_textAccount.text {
                isCheckAccount = true
                if account[1] == m_textPassword.text{
                    isCheckPassword = true
                    break
                }
            }
        }
        
        if !(isCheckAccount) {
            showAlertView("你的帳號不存在")
        }
        else if !(isCheckPassword) {
            showAlertView("你的密碼錯誤")
        }
        else {
            if m_loggedPage == nil{
                m_loggedPage = LoggedPage()
                m_loggedPage?.refreshWithFrame(self.view.frame)
            }
            m_loggedPage?.m_username = m_textAccount.text
            self.navigationController?.pushViewController(m_loggedPage!, animated: true)
            clean()
        }
    }
    
    func onButtonRegister(senter:UIButton){
        if isTextEmpty() {
            showAlertView("你輸入的帳號或密碼不可為空")
            return
        }
        
        //判斷帳號是否已經存在
        for account in CTransfrom.aryAccountData{
            if account[0] == m_textAccount.text{
                showAlertView("你的帳號已經存在")
                clean()
                return
            }
        }
        
        var user:[String] = [m_textAccount.text,m_textPassword.text]
        CTransfrom.aryAccountData.append(user)
        CTransfrom.refreshAccountData()
        showAlertView("註冊完成")
        clean()
    }
}

