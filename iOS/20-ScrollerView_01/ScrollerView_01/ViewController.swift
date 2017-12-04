import UIKit

class ViewController: UIViewController,UIScrollViewDelegate {
    var m_scrollView:UIScrollView!
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        m_scrollView = UIScrollView()
        m_scrollView.frame = CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)
        m_scrollView.contentSize = CGSizeMake(0,0)
        m_scrollView.backgroundColor = UIColor.clearColor()
        m_scrollView.showsVerticalScrollIndicator = true
        m_scrollView.showsHorizontalScrollIndicator = false
        
        /*
        var lableNumber:CGFloat = 123
        var lableHeight:CGFloat = 70
        var lableSpace:CGFloat = 10
        var contentLable:UILabel!
        for var i:CGFloat = 0 ; i < lableNumber ; i++ {
            contentLable = UILabel()
            contentLable.frame.origin = CGPointMake(0, lableSpace + (lableSpace + lableHeight) * i )
            contentLable.frame.size = CGSizeMake(self.view.frame.size.width, lableHeight)
            contentLable.font = UIFont.boldSystemFontOfSize(lableHeight * 0.9)
            contentLable.text = "\(Int(i)+1)"
            contentLable.textAlignment = NSTextAlignment.Center
            
            m_scrollView.addSubview(contentLable)
        }
        m_scrollView.contentSize = CGSizeMake(self.view.frame.size.width,contentLable.frame.origin.y + lableHeight + lableSpace)
        
        */
        
        let fontFamilyNames = UIFont.familyNames() //回傳字體型態陣列
        var contentLable:UILabel!
        var lableHeight:CGFloat = 70
        var lableSpace:CGFloat = 10
        var i:CGFloat = 0
        
        for familyName in fontFamilyNames{
            contentLable = UILabel()
            contentLable.frame.origin = CGPointMake(0, lableSpace + (lableSpace + lableHeight) * i )
            contentLable.frame.size = CGSizeMake(self.view.frame.size.width, lableHeight)
            contentLable.text = "\(Int(i+1)).\(familyName as String)"
            contentLable.textAlignment = NSTextAlignment.Left
            contentLable.font = UIFont(name: familyName as String, size: lableHeight * 0.5)
            
            
            m_scrollView.addSubview(contentLable)
            i++
        }
        m_scrollView.contentSize = CGSizeMake(self.view.frame.size.width,contentLable.frame.origin.y + lableHeight + lableSpace)
        m_scrollView.delegate = self

        self.view.addSubview(m_scrollView)
    }
    
    var isToButtom:Bool = false
    //手指放看瞬間觸發
    func scrollViewDidEndDragging(scrollView: UIScrollView, willDecelerate decelerate: Bool) {
        isToButtom = scrollView.contentOffset.y < -100 ? true : false
    }
    
    
    
    //滑動動畫完成時 觸發
    func scrollViewDidEndDecelerating(scrollView: UIScrollView) {
        if isToButtom {
            m_scrollView.setContentOffset(CGPointMake(0, m_scrollView.contentSize.height - self.view.frame.size.height), animated: true)
        }
    }
}

