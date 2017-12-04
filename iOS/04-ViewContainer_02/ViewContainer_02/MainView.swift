import UIKit

class MainView: UIView {
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.brownColor()//透明色UIColor.clearColor
        
        var viewH:CGFloat = 300
        
        //red View
        var viewA = UIView(frame: CGRectMake(0, 0, self.frame.size.width, viewH))
        viewA.backgroundColor = UIColor.redColor()
        
        self.addSubview(viewA)
        
        //blue View
        var viewB = UIView(frame: CGRectMake(0, viewH/2, self.frame.size.width, viewH))
        viewB.backgroundColor = UIColor.blueColor()
        self.addSubview(viewB)
        
        //black View
        var viewC = UIView(frame: CGRectMake(0, viewH, self.frame.size.width, viewH))
        viewC.backgroundColor = UIColor.blackColor()
        self.insertSubview(viewC, aboveSubview: viewA)    //Subview
        
        //green View
        var viewD = UIView(frame: CGRectMake(0, viewH, self.frame.size.width, viewH/2+100))
        viewD.backgroundColor = UIColor.greenColor()
        self.insertSubview(viewD, aboveSubview: viewC)    //Subview
        
        //self.exchangeSubviewAtIndex(2, withSubviewAtIndex: 3) //交換子view層級
    }
    
    required init(coder aDecoder: NSCoder) {       //初始化失敗時呼叫
        fatalError("init(coder:) has not been implemented")
    }
}
