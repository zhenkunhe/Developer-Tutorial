
import UIKit

class MyPhotoView: UIImageView {
    var oriFrame:CGRect!
    init(image:UIImage,frame: CGRect) {
        super.init(image: image)
        self.frame = frame
        self.oriFrame = frame
        self.userInteractionEnabled = true  //攔截事件,不穿透
    }

    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func touchesBegan(touches: NSSet, withEvent event: UIEvent) {
        UIView.beginAnimations("MoveGroupAni", context: nil)
        frame = oriFrame
        UIView.commitAnimations()
        
    }
}
