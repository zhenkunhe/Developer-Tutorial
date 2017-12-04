import UIKit

//因為相機照完要編輯或返回,所以雖然不是在這裡使用,不過還是要實作UINavigationControllerDelegate
//因為UIImagePickerController繼承自UINavigationController,所以UINavigationControllerDelegate也可以用
class ViewController: UIViewController,UIImagePickerControllerDelegate,UINavigationControllerDelegate {
    var m_cameraBtn:UIButton = UIButton()
    var m_photoBtn:UIButton = UIButton()
    var m_photoImage:UIImageView!
    var m_myPhoto:MyPhoto?
    
    //MARK: - Override
    //-------------------------------
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        var width:CGFloat = 150
        
        m_photoImage = UIImageView(frame: CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.width))
        m_photoImage.backgroundColor = UIColor.blackColor()
        
        m_cameraBtn.frame = CGRectMake((frame.size.width - width) / 2, m_photoImage.frame.size.height + 100, width, 35)
        m_cameraBtn.backgroundColor = UIColor.darkGrayColor()
        m_cameraBtn.setTitle("相機", forState: UIControlState.Normal)
        m_cameraBtn.addTarget(self, action: Selector("onButtonCamera:"), forControlEvents: UIControlEvents.TouchUpInside)
        
        m_photoBtn.frame = CGRectMake((frame.size.width - width) / 2, m_cameraBtn.frame.origin.y + m_cameraBtn.frame.size.height + 100, width, 35)
        m_photoBtn.backgroundColor = UIColor.darkGrayColor()
        m_photoBtn.setTitle("相簿", forState: UIControlState.Normal)
        m_photoBtn.addTarget(self, action: Selector("onButtonPhoto:"), forControlEvents: UIControlEvents.TouchUpInside)
        
        self.view.addSubview(m_photoImage!)
        self.view.addSubview(m_cameraBtn)
        self.view.addSubview(m_photoBtn)
    }
    
    //MARK: - Function
    //-------------------------------
    func callGetPhoneWithKind(kind:Int){
        var picker:UIImagePickerController = UIImagePickerController()
        switch kind {
        case 1:     //呼叫相機
            if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.Camera){
                picker.sourceType = UIImagePickerControllerSourceType.Camera
                picker.allowsEditing = true //上下兩條白線
                picker.delegate = self
                self.presentViewController(picker, animated: true, completion: nil)
            }
            else{
                showAlertWithTitle("警告",message: "沒有相機鏡頭")
            }
        default:     //呼叫相簿
            if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.PhotoLibrary){
                picker.sourceType = UIImagePickerControllerSourceType.PhotoLibrary
                picker.allowsEditing = true //上下兩條白線
                picker.delegate = self
                self.presentViewController(picker, animated: true, completion: nil)
            }
            else{
                self.showAlertWithTitle("警告",message: "沒有相簿")
            }
        }
    }
    
    func showAlertWithTitle(title:String,message:String){
        UIAlertView(title: title, message: message, delegate: nil, cancelButtonTitle: "確定").show()
    }

    //MARK: - Listener
    //-------------------------------
    
    func onButtonCamera(sender:UIButton){
        self.callGetPhoneWithKind(1)
    }
    
    func onButtonPhoto(sender:UIButton){
        self.callGetPhoneWithKind(2)
    }
    
    //MARK: - Delegate
    //-------------------------------
    
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [NSObject : AnyObject]) {
        
        var image:UIImage = info[UIImagePickerControllerOriginalImage] as UIImage
        var imageEdit:UIImage = info[UIImagePickerControllerEditedImage] as UIImage
        m_photoImage.image = imageEdit
        picker.dismissViewControllerAnimated(true, completion: nil)
        
        if m_myPhoto == nil {
            m_myPhoto = MyPhoto()
            m_myPhoto?.refreshWithFrame(self.view.frame)
            m_myPhoto?.m_parentObj = self
            m_myPhoto?.m_saveCallBack = Selector("onSavePhoto:")
        }
        m_myPhoto?.setImageData(imageEdit)
        self.presentViewController(m_myPhoto!, animated: true, completion: nil)
    }
    
    func imagePickerController(picker: UIImagePickerController!, didFinishPickingImage image: UIImage!, editingInfo: [NSObject : AnyObject]!) {
        picker.dismissViewControllerAnimated(true, completion: nil)
    }
    
    func onSavePhoto(img:UIImage){
        //儲存影像後規定必須呼叫另一個Selector,來判斷成功或失敗,以及顯示什麼
        UIImageWriteToSavedPhotosAlbum(img, self, Selector("image:didFinishSavingWithError:contextInfo:"), nil)
        self.dismissViewControllerAnimated(true , completion: nil)
    }
    
    func image(image: UIImage,didFinishSavingWithError error:NSErrorPointer, contextInfo:UnsafePointer<()>) {
        if error == nil{
            self.showAlertWithTitle("Success", message: "儲存成功")
        }
    }
}

