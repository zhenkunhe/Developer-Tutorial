import UIKit

class MainView: UIView {
    //CGRect內放著((x,y),(w,h))也就是(原點,寬高)  值為CGFloat,與Float不同的地方只是一個有繪製輸出
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.brownColor()//透明色UIColor.clearColor
        
        var w:CGFloat = 100
        var h:CGFloat = 100
        
        //left top
        var view:UIView = UIView(frame:CGRectMake(0, 0, w, h) )
        view.backgroundColor = UIColor.redColor()
        self.addSubview(view)
        
        //right top
        view = UIView(frame:CGRectMake(self.frame.size.width - w, 0, w, h) )
        view.backgroundColor = UIColor.blueColor()
        self.addSubview(view)
        
        //left button
        view = UIView(frame:CGRectMake(0, self.frame.size.height - h, w, h) )
        view.backgroundColor = UIColor.greenColor()
        self.addSubview(view)
        
        //right button
        view = UIView(frame:CGRectMake(self.frame.size.width - w, self.frame.size.height - h, w, h) )
        view.backgroundColor = UIColor.purpleColor()
        self.addSubview(view)
        
        //center
        view = UIView(frame:CGRectZero)     //空的矩形
        view.frame.size = CGSizeMake(100, 100)  //設置size
        view.backgroundColor = UIColor.grayColor()
        view.center = CGPoint(x: frame.size.width/2, y: frame.size.height/2)    //設置View的「中心點」
        self.addSubview(view)
        
        view.removeFromSuperview()//移除需要子物件自己離開
        
    }
    
    required init(coder aDecoder: NSCoder) {       //初始化失敗時呼叫
        fatalError("init(coder:) has not been implemented")
    }
}
