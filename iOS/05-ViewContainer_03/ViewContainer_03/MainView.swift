
import UIKit

class MainView: UIView {

    var ary:[UIView] = [UIView]()
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.grayColor()
        
        //UIimage：named裡面打入圖檔名稱,預設副檔名為.png 因為有自動格式(a.png a@1,png 都只要打"a"),所以盡量不要打副檔名,且都用png圖檔
        var imageView:UIImage = UIImage(named: "btn")!
        
        //若圖檔小於此大小,不會延展 若大於,則等比例縮小(注意變形失真)
        var clearBtn:UIButton = UIButton(frame: CGRectMake(0, 0, 100, 100))
        
        //UIControlState為所有UI元件的控制狀態,一般放Normal
        clearBtn.setImage(imageView, forState: UIControlState.Normal)
        
        //加入監聽器:addTarget  第一個參數為「作用目標」
        //第二個參數為「進行動作」放的是函數名稱,若此函數有接收參數則需要加上:
        //第三個參數為「觸發條件」(不是任何元件都支援所有觸發條件)
        clearBtn.addTarget(self, action: Selector("onClearBtn:"), forControlEvents: UIControlEvents.TouchUpInside)
        self.addSubview(clearBtn)
    }

    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func touchesBegan(touches: NSSet, withEvent event: UIEvent) {
        //NSSet裡面是個UITouch陣列,存放手指觸碰的參數(以AnyObject存放)
        var touche:UITouch = touches.allObjects[0] as UITouch
        
        //取出touch相對於某View的相對位置
        var location:CGPoint = touche.locationInView(self)

        var view = UIView(frame: CGRectMake(location.x, location.y, 30, 30))
        view.backgroundColor = UIColor(red: CGFloat(arc4random()%256)/255, green: CGFloat(arc4random()%256)/255, blue: CGFloat(arc4random()%256)/255, alpha: CGFloat(arc4random()%100)/100)
        //RGB Alpha的value從0-255壓縮到0~1之間的浮點數
        self.addSubview(view)
        ary.append(view)
    }
    
    override func touchesEnded(touches: NSSet, withEvent event: UIEvent) {
    }

    func onClearBtn(btn:UIButton){
        for _ in ary{
            ary.removeLast().removeFromSuperview()
        }

        /*
        for subView in self.subviews {
            /*
            //「型態判斷」：as?
            if (subView as? UIButton == nil){
                subView.removeFromSuperview()
            }
            
            //「型態判斷」：is
            if !(subView is UIButton){
                subView.removeFromSuperview()
            }

            //「型態判斷」：isKindOfClass 判斷此物件是否是傳入的 「型態」 或是 「子型態」
            if !(subView.isKindOfClass(UIButton)) {
                subView.removeFromSuperview()
            }

            //「型態判斷」：isMemberOfClass 判斷此物件是否是傳入的 「型態」
            if (subView.isMemberOfClass(UIView)) {
                subView.removeFromSuperview()
            }
            */
            
            /*
            //「實體判斷」：as
            if (subView as NSObject != btn) {
                subView.removeFromSuperview()
            }
            
            //「實體判斷」：.isEqual()內可填入nil
            if !(subView.isEqual(btn)){
                subView.removeFromSuperview()
            }
            */
        }//end for
        */
    }
}
