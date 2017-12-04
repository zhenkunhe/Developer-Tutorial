import UIKit

class ViewController: UIViewController,UIActionSheetDelegate {
    
    var m_photoImg:UIImageView = UIImageView()
    var m_sizeLable:UILabel = UILabel()
    
    //MARK: -Override UIViewController
    //-----------------------------------
    
    override func viewDidAppear(animated: Bool) {
    }
    
    override func viewDidDisappear(animated: Bool) {
    }
    
    //MARK: -Public Function
    //-----------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.grayColor()
        
        var resizeBtn:UIButton = UIButton()
        
        m_photoImg.frame = CGRectMake(0, 0, frame.size.width, frame.size.width)
        m_photoImg.image = UIImage(named: "aa")
        
        m_sizeLable.frame = CGRectMake(0, m_photoImg.frame.height, frame.size.width, 100)
        m_sizeLable.text = "100%"
        m_sizeLable.textColor = UIColor.blueColor()
        m_sizeLable.textAlignment = NSTextAlignment.Center
        m_sizeLable.font = UIFont.boldSystemFontOfSize(m_sizeLable.frame.height * 0.85)
        //m_sizeLable.adjustsFontSizeToFitWidth = true
        
        resizeBtn.frame = CGRectMake(0, m_photoImg.frame.height + m_sizeLable.frame.height , frame.size.width , self.view.frame.height - (m_photoImg.frame.height + m_sizeLable.frame.height))
        resizeBtn.setTitle("ReSize", forState: UIControlState.Normal)
        resizeBtn.setTitleColor(UIColor.redColor(), forState: UIControlState.Normal)
        resizeBtn.titleLabel?.font = UIFont.boldSystemFontOfSize(resizeBtn.frame.height * 0.3)
        resizeBtn.addTarget(self, action: Selector("onResizeBtnClickUp:"), forControlEvents: UIControlEvents.TouchUpInside)
        resizeBtn.addTarget(self, action: Selector("onResizeBtnClickDown:"), forControlEvents: UIControlEvents.TouchDown)
        
        
        
        self.view.addSubview(m_photoImg)
        self.view.addSubview(m_sizeLable)
        self.view.addSubview(resizeBtn)
    }
    
    //MARK: -onClickListoner
    //-----------------------------------
    
    func onResizeBtnClickUp(btn:UIButton){
        var actSheet:UIActionSheet = UIActionSheet(title: "ReSize", delegate: self, cancelButtonTitle: "取消", destructiveButtonTitle: "100%", otherButtonTitles: "75%", "50%")
        actSheet.showInView(self.view)
    }
    
    func onResizeBtnClickDown(btn:UIButton){
        btn.titleLabel?.alpha = 0.7
    }
    
    //MARK: -implement UIActionSheetDelegate
    //-----------------------------------
    
    func actionSheet(actionSheet: UIActionSheet, clickedButtonAtIndex buttonIndex: Int) {
        var percentage:CGFloat = 1.0
        switch buttonIndex {
        case 0:
            percentage = 1.0
        case 2:
            percentage = 0.75
        case 3:
            percentage = 0.5
        default:
            return
        }
        let resizeW:CGFloat = self.view.frame.width * percentage
        
        UIView.beginAnimations("MakeAniGroup", context: nil)
        m_photoImg.frame = CGRectMake(
            (self.view.frame.size.width - resizeW)/2 ,
            (self.view.frame.size.width - resizeW)/2,
            resizeW, resizeW)
        UIView.commitAnimations()
        
        m_sizeLable.text = "\(Int(percentage * 100))%"
    }
}

