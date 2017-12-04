import UIKit

class ViewController: UIViewController {
    var btn:UIButton!
    var m_rightBtnItem:UIBarButtonItem!
    var newPageView:NewPageViewController!
    var m_isAlready:Bool = false    //不didAppear完成(連點過快),就觸發push有機會當機
    
    //MARK: - Override
    //-------------------------------
    
    override func viewDidLoad() {
        super.viewDidLoad()
        var onAction:Selector = Selector("onToolBarAction:")
        var item_1:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Camera, target: self, action: onAction)
        var item_2:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Bookmarks, target: self, action: onAction)
        var item_3:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Edit, target: self, action: onAction)
        var item_4:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Done, target: self, action: onAction)
        var flexItem:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.FlexibleSpace, target: nil, action: nil)
        item_1.tag = 1
        
        m_rightBtnItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Done, target: self, action: onAction)
        self.navigationItem.rightBarButtonItem = m_rightBtnItem
        
        self.toolbarItems = [flexItem,item_1,flexItem,item_2,flexItem,item_3,flexItem,item_4,flexItem]
    }
    
    override func viewDidAppear(animated: Bool) {
        showToolItem(!(self.navigationController!.toolbarHidden))
        m_isAlready = true
    }
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        m_isAlready = false
    }
    
    override func viewDidDisappear(animated: Bool) {
        super.viewDidDisappear(animated)
        
        m_isAlready = false
    }
    
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        btn = UIButton()
        btn.frame.size = CGSizeMake(200, 50)
        btn.center = CGPointMake(self.view.frame.size.width/2, self.view.frame.size.height/2)
        btn.setTitle("Show", forState: UIControlState.Normal)
        btn.titleLabel?.font = UIFont.boldSystemFontOfSize(50)
        btn.addTarget(self, action: Selector("onButtonAction:"), forControlEvents: UIControlEvents.TouchUpInside)
        btn.titleLabel?.textColor = UIColor.blackColor()
        self.view.addSubview(btn)
    }
    
    func showToolItem(isShow:Bool){
        self.navigationController?.setToolbarHidden(!(isShow), animated: false)
        btn.setTitle(isShow ? "Show" : "Hide", forState: UIControlState.Normal)
    }
    
    //MARK: - CallBack & Lisetner
    //-------------------------------
    
    func onToolBarAction(senter:UIButton){
        if newPageView == nil {
            newPageView = NewPageViewController()
        }
        else if m_isAlready {
            newPageView.isShow = !(self.navigationController!.toolbarHidden)
            self.navigationController?.pushViewController(newPageView!, animated: true)
        }
    }
    
    func onButtonAction(senter:UIButton){
        showToolItem(self.navigationController!.toolbarHidden)
    }
}

