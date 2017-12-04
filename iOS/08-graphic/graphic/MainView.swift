

import UIKit

class MainView: UIView {
    var m_context:CGContextRef!     //文本參考
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.grayColor()
    }

    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func drawRect(rect: CGRect) {
        var drawWidth:CGFloat = 10
        m_context = UIGraphicsGetCurrentContext()   //畫筆文本
        setDrawWidth(drawWidth)                     //CGContextSet...
        
        setContentColer(UIColor.redColor())         //CGCGContextSet...
        var ori:CGPoint = CGPoint(x: 0, y: 0)
        var tar:CGPoint = CGPoint(x: frame.size.width, y: frame.size.height)
        drawLine(ori, tar: tar)                     //CGContext Set..Move...Add...Path
        
        setContentColer(UIColor.blueColor())        //CGCGContextSet...
        var r:CGFloat = 50
        var center:CGPoint = CGPoint(x: frame.size.width - r - drawWidth, y: 0 + r + drawWidth)
        drawCircle(center, r: r ,isFill: false)     //CGContext Set..Move...Add...Path
        
        setContentColer(UIColor.greenColor())        //CGCGContextSet...
        var size:CGSize = CGSize(width: 100, height: 100)
        ori = CGPoint(x: 0 , y: frame.size.height - size.height)
        drawTriangleRect(ori, rect: size, isFill: true)
        
        setContentColer(UIColor.yellowColor())        //CGCGContextSet...
        var p1:CGPoint = CGPoint(x: 0, y: frame.size.height/2 )
        var p2:CGPoint = CGPoint(x: frame.size.width, y: frame.size.height/2 )
        var p3:CGPoint = CGPoint(x: frame.size.width/2, y: frame.size.height/4 )
        drawQuadCurve(p1, to: p2, curve: p3)
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
    
    func drawCircle(center:CGPoint,r:CGFloat,isFill:Bool){
        CGContextMoveToPoint(m_context, center.x - r, center.y - r)
        CGContextAddEllipseInRect(m_context, CGRectMake(center.x - r, center.y - r, r * 2, r * 2))
        isFill ? CGContextFillPath(m_context) : CGContextStrokePath(m_context)
    }
    
    func drawTriangleRect(ori:CGPoint, rect:CGSize,isFill:Bool){
        CGContextMoveToPoint(m_context, ori.x + (rect.width / 2), ori.y)    //移動畫筆到某一點
        CGContextAddLineToPoint(m_context, ori.x , ori.y + rect.height)     //畫一條線
        CGContextAddLineToPoint(m_context, ori.x + rect.width , ori.y + rect.height)    //畫一條線
        CGContextAddLineToPoint(m_context, ori.x + (rect.width / 2), ori.y) //畫一條線
        isFill ? CGContextFillPath(m_context) : CGContextStrokePath(m_context)
    }
    
    func drawQuadCurve(ori:CGPoint, to:CGPoint , curve:CGPoint){
        CGContextMoveToPoint(m_context, ori.x , ori.y)    //移動畫筆到某一點
        CGContextAddQuadCurveToPoint(m_context, curve.x, curve.y, to.x, to.y)
        CGContextStrokePath(m_context)
    }

}
