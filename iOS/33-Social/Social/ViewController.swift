import UIKit
import Social
import MessageUI

class ViewController: UIViewController,UIActionSheetDelegate,MFMailComposeViewControllerDelegate {
    var rexPost:SLComposeViewController!
    var m_shareStage:UIView!
    var mailController:MFMailComposeViewController!
    
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        var shareBtn:UIButton = UIButton(frame: CGRectMake(0, self.view.frame.size.height - 50, self.view.frame.size.width, 50))
        shareBtn.backgroundColor = UIColor.blackColor()
        shareBtn.setTitle("一鍵分享ＡＰＰ", forState: UIControlState.Normal)
        shareBtn.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        self.view.addSubview(shareBtn)
        
        m_shareStage = UIView(frame: CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height - shareBtn.frame.size.height))
        m_shareStage.backgroundColor = UIColor.lightGrayColor()
        self.view.addSubview(m_shareStage)
        
        var iconImg:UIImageView!
        var space:CGFloat = 15
        var iconW:CGFloat = (m_shareStage.frame.size.width - space * 4) / 3
        for var i = 0 ; i < 7 ; i++ {
            iconImg = UIImageView(frame: CGRectMake(space + CGFloat(i % 3) * (space + iconW), space + CGFloat(i / 3) * (space + iconW), iconW, iconW))
            iconImg.image = UIImage(named: "Icon_\(i)")
            iconImg.layer.masksToBounds = true
            iconImg.layer.cornerRadius = 20.0
            m_shareStage.addSubview(iconImg)
        }
    }
    
    func convertViewToImage(v:UIView) -> UIImage{
        var s:CGSize = v.bounds.size
        //範圍,透明度,放大縮小
        UIGraphicsBeginImageContextWithOptions(s, false, UIScreen.mainScreen().scale)
        v.layer.renderInContext(UIGraphicsGetCurrentContext())
        var image:UIImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return image
    }
    
    func callDoShareGeoKind(index:Int){
        var info:String = "全台灣最好的ＡＰＰ！"
        var appendPic:UIImage = self.convertViewToImage(m_shareStage)
        var URL:String = "itunes.apple.com/tw/artist/wonderfulcode"
        var sendFaild:String = "取消發佈"
        var sendSuccess:String = "成功發佈"
        var mailTital:String = "App Share"
        var mailSubject:String = "推薦你必下載App"
        if rexPost == nil {
            rexPost = SLComposeViewController()
        }
        
        switch index{
        case 0://Facebook
            rexPost = SLComposeViewController(forServiceType: SLServiceTypeFacebook)
        case 2://Twitter
            rexPost = SLComposeViewController(forServiceType: SLServiceTypeTwitter)
        case 3://新浪微博 不會自動跳入頁面,需要自行判斷
            if SLComposeViewController.isAvailableForServiceType(SLServiceTypeSinaWeibo){
                rexPost = SLComposeViewController(forServiceType: SLServiceTypeSinaWeibo)
            }else{
                return;
            }
        case 4:
            self.showEmail(eTitle: mailTital, eContent: info, eSubject: mailSubject, ePicture: appendPic, eRecipients: nil, eAttachment: nil)
            return
        default:
            return
        }
        rexPost.addImage(appendPic)
        rexPost.setInitialText(info)//若字數太多,送出去會變空字串
        rexPost.addURL(NSURL(string: URL))
        self.presentViewController(rexPost, animated: true, completion: nil)
    }
    
    func showEmail(eTitle title:String , eContent content:String , eSubject subject:String , ePicture pic:UIImage , eRecipients aryRecipients:Array<String>? , eAttachment attachment:NSData? ){
        if mailController == nil {
            mailController = MFMailComposeViewController()
        }
        mailController.title = title
        mailController.mailComposeDelegate = self
        mailController.setSubject(subject)
        mailController.setMessageBody(content, isHTML: true)
        var imageData:NSData = UIImagePNGRepresentation(pic)
        //夾帶圖檔
        mailController.addAttachmentData(imageData, mimeType: "", fileName: "iCon.png")
        //若有指定收件人
        if aryRecipients != nil{
            mailController.setToRecipients(aryRecipients)
        }
        //若有夾帶檔案
        if attachment != nil {
            mailController.addAttachmentData(attachment, mimeType: "text/csv", fileName: "Report.csv")
        }
        //若不能使用信箱
        if !MFMailComposeViewController.canSendMail() {
            return
        }
        //導入頁面,2015後的模擬器會當掉
        self.presentViewController(mailController, animated: true, completion: nil)
    }
    
    //MARK: - Listener
    //-------------------------------
    
    func onButtonAction(sender:UIButton){
        var sheet:UIActionSheet = UIActionSheet(title: "分享", delegate: self, cancelButtonTitle: "取消", destructiveButtonTitle: "FB", otherButtonTitles: "Twitter", "新浪微薄" , "eMail")
        sheet.showInView(self.view)
    }
    
    //MARK: - Delegate
    //-------------------------------
    
    //只會顯示正中間的圖片,但實際上都有擷取到
    func actionSheet(actionSheet: UIActionSheet, clickedButtonAtIndex buttonIndex: Int) {
        callDoShareGeoKind(buttonIndex)
    }
    
    func mailComposeController(controller: MFMailComposeViewController!, didFinishWithResult result: MFMailComposeResult, error: NSError!) {
        switch result.value{
        case MFMailComposeResultCancelled.value:
            NSLog("取消書寫信件")
        case MFMailComposeResultSaved.value:
            NSLog("信件儲存")
        case MFMailComposeResultSent.value:
            NSLog("信件送出")
        case MFMailComposeResultFailed.value:
            NSLog("發送失敗: %@",[error.localizedDescription])
        default:
            break
        }
        self.dismissViewControllerAnimated(true, completion: nil)
    }
}