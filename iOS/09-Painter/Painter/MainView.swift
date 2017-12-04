import UIKit

class MainView: UIView {
    var m_context:CGContextRef!     //文本參考
    var m_drawImg:UIImageView!      //覆蓋在mainView上的ImageView
    var m_lastPoint:CGPoint = CGPointZero   //上一個點
    
    override init(frame: CGRect) {  //初始化
        super.init(frame: frame)
        self.backgroundColor = UIColor.whiteColor()
    }
    
    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func drawRect(rect: CGRect) {  //初始化完成後,自動執行第一次繪製
        m_drawImg = UIImageView(frame: self.frame)
        m_drawImg.image = UIImage(named: "aa")
        self.addSubview(m_drawImg)
    }
    
    override func touchesBegan(touches: NSSet, withEvent event: UIEvent) {
        let touch:UITouch = touches.allObjects[0] as UITouch
        let beganPoint:CGPoint = touch.locationInView(self)
        if touch.tapCount >= 2 {
            m_drawImg.image = UIImage(named: "aa")
        }
        m_lastPoint = beganPoint
        
    }
    override func touchesMoved(touches: NSSet, withEvent event: UIEvent) {
        var touch:UITouch = touches.allObjects[0] as UITouch
        var currentPoint:CGPoint = touch.locationInView(self)
        
        UIGraphicsBeginImageContextWithOptions(self.frame.size, false, 0)   //開始新文本(撈資料)
        m_context = UIGraphicsGetCurrentContext()                           //指向新文本
        setDrawWidth(10)                                                    //設定文本屬性（畫筆寬）
        setContentColer(UIColor.redColor())                                 //設定文本屬性（畫筆顏色）
        
        //撈m_drawImg的image  並壓到某個範圍
        m_drawImg.image?.drawInRect(CGRectMake(0, 0, m_drawImg.frame.size.width, m_drawImg.frame.size.height))
        //畫新的線
        drawLine(m_lastPoint, tar: currentPoint)
        
        m_drawImg.image = UIGraphicsGetImageFromCurrentImageContext()      //重載撈到的資料
        UIGraphicsEndImageContext()                                        //關閉文本（結束撈取資料）
        m_lastPoint = currentPoint
    }
    
    func setContentColer(col:UIColor){
        //UIColor內含著CGColor,CGColor只是ＡＲＧＢ四個浮點數的集和
        let components = CGColorGetComponents(col.CGColor)
        CGContextSetRGBFillColor(m_context, components[0], components[1], components[2], components[3])
        CGContextSetRGBStrokeColor(m_context, components[0], components[1], components[2], components[3])
    }
    
    func setDrawWidth(w:CGFloat){
        CGContextSetLineWidth(m_context, w)             //設定畫筆寬度
    }
    
    func drawLine(ori:CGPoint,tar:CGPoint){
        CGContextSetLineCap(m_context, kCGLineCapRound) //設定線條樣式,kCG系列是樣式
        CGContextMoveToPoint(m_context, ori.x, ori.y)   //移動畫筆到某一點
        CGContextAddLineToPoint(m_context, tar.x, tar.y)//畫一條線
        CGContextStrokePath(m_context)                  //空心繪製路徑開始
    }
}
