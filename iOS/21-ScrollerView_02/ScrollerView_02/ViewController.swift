import UIKit

class ViewController: UIViewController,UIScrollViewDelegate {
    var m_scrollView:UIScrollView!
    //var x:CGFloat = CGFloat(arc4random() / 256)
    var m_color:[UIColor] = [UIColor.redColor(),UIColor.blueColor(),UIColor.brownColor(),UIColor.greenColor(),UIColor.purpleColor()]
    var m_pageControl:UIPageControl!
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        m_scrollView = UIScrollView()
        m_scrollView.frame = CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)
        m_scrollView.contentSize = CGSizeMake(0,0)
        m_scrollView.backgroundColor = UIColor.clearColor()
        m_scrollView.showsVerticalScrollIndicator = false
        m_scrollView.showsHorizontalScrollIndicator = true
        m_scrollView.delegate = self
        m_scrollView.pagingEnabled = true
        
        var tempView:UIView!
        for var i = 0 ; i < m_color.count ; i++ {
            tempView = UIView(frame: CGRectMake(self.view.frame.size.width * CGFloat(i), 0, self.view.frame.size.width, self.view.frame.size.height))
            tempView.backgroundColor = m_color[i]
            m_scrollView.addSubview(tempView)
        }
        m_scrollView.contentSize = CGSizeMake(m_scrollView.frame.size.width * CGFloat(m_color.count) , m_scrollView.frame.size.height)
        
        m_pageControl = UIPageControl(frame: CGRectMake(0, self.view.frame.size.height - 20, self.view.frame.size.width, 20))
        m_pageControl.numberOfPages = m_color.count
        m_pageControl.currentPage = 0
        m_pageControl.addTarget(self, action: "onPageAction:", forControlEvents: UIControlEvents.ValueChanged)
        
        self.view.addSubview(m_scrollView)
        self.view.addSubview(m_pageControl)
    }
    
    func scrollViewDidEndDecelerating(scrollView: UIScrollView) {
        var pageNumber:Int = Int(m_scrollView.contentOffset.x / m_scrollView.frame.size.width)
        if pageNumber >= 0 && m_pageControl.currentPage != pageNumber {
            UIAlertView(title: "歡迎", message: "歡迎來到第\(pageNumber + 1)頁", delegate: nil, cancelButtonTitle: "完成").show()
            m_pageControl.currentPage = pageNumber
        }
       
    }
    
    func onPageAction(senter:UIPageControl){
        var pageNumber:Int = senter.currentPage
        m_scrollView.setContentOffset(CGPointMake((m_scrollView.frame.size.width * CGFloat(pageNumber)), 0), animated: true)

        UIAlertView(title: "歡迎", message: "歡迎來到第\(pageNumber + 1)頁", delegate: nil, cancelButtonTitle: "完成").show()
    }
}

