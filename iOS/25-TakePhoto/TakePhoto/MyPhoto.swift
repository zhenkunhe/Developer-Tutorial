import UIKit

class MyPhoto: UIViewController {
    var m_parentObj:AnyObject?
    var m_saveCallBack:Selector?
    var m_myPhoto:UIImageView!
    
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        m_myPhoto = UIImageView(frame: CGRectMake(0, 0, frame.size.width, frame.size.height))
        self.view.addSubview(m_myPhoto)
        
        let saveBtn = UIButton(frame: CGRectMake(100, 300, 100, 50))
        saveBtn.titleLabel?.textColor = UIColor.whiteColor()
        saveBtn.setTitle("Save", forState: UIControlState.Normal)
        saveBtn.backgroundColor = UIColor.clearColor()
        saveBtn.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        self.view.addSubview(saveBtn)
    }
    
    func setImageData(image:UIImage){
        m_myPhoto.image = image
    }
    
    func threadStart(Void)->Void{
        //呼叫某個物件的某個方法,傳入該方法的某個參數
        NSThread.detachNewThreadSelector(m_saveCallBack!, toTarget: self.m_parentObj!, withObject: self.m_myPhoto.image)
    }
    
    func onButtonAction(senter:UIButton){
        //若有父親 && 有傳入方法
        if (m_parentObj != nil) && (m_saveCallBack != nil){
            //方法真的存在
            if m_parentObj?.respondsToSelector(m_saveCallBack!) == true{
                var time = dispatch_time(DISPATCH_TIME_NOW, Int64(0))
                dispatch_after(time, dispatch_get_main_queue(), threadStart)
            }
        }
    }
}
