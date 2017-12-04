
import UIKit

class MainView: UIView {
    var ballView:UIView!
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.grayColor()
        var w:CGFloat = 60
        ballView = UIView(frame: CGRectMake(100, 100, w , w))
        ballView.backgroundColor = UIColor.whiteColor()
        ballView.layer.masksToBounds = true //不去繪製超出遮罩外的範圍
        ballView.layer.cornerRadius = w/2
        self.addSubview(ballView)
    }

    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func moveBallWith(touches: NSSet){
        var touch:UITouch = touches.allObjects[0] as UITouch
        var location:CGPoint = touch.locationInView(self)
        
        UIView.beginAnimations("MoveGroupAni", context: nil)
        UIView.setAnimationDelay(0.1)
        //從這行到UIView.commitAnimations()之間的繪圖動作,會在0.1秒內逐漸完成,並命名這串動作為MoveGroupAni
        //若時間還沒到,卻引發了下一個Animations,則動作會立刻完成,以便銜接下一個Animations
        ballView.center = location

        UIView.commitAnimations()
    }
    
    override func touchesBegan(touches: NSSet, withEvent event: UIEvent) {
        self.moveBallWith(touches)
    }
    
    override func touchesMoved(touches: NSSet, withEvent event: UIEvent) {
        self.moveBallWith(touches)
    }
}