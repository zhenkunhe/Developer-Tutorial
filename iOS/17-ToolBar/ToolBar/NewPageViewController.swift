import UIKit

class NewPageViewController: UIViewController {
    
    //MARK: - Override
    //-------------------------------
    var isShow:Bool = true
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.blueColor()
        self.navigationItem.title = "Title"
        
        var onAction:Selector = Selector("onToolBarAction:")
        var item_1:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Done, target: self, action: onAction)
        var item_2:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.FastForward, target: self, action: onAction)
        var item_3:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Action, target: self, action: onAction)
        var item_4:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Cancel, target: self, action: onAction)
        var flexItem:UIBarButtonItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.FlexibleSpace, target: nil, action: nil)
        item_1.tag = 1
        self.toolbarItems = [flexItem,item_1,flexItem,item_2,flexItem,item_3,flexItem,item_4,flexItem]
    }
    
    override func viewDidAppear(animated: Bool) {
        showToolItem(isShow)
    }
    
    //MARK: - Public
    //-------------------------------
    
    func showToolItem(isShow:Bool){
        self.navigationController?.setToolbarHidden(!(isShow), animated: false)
    }
    
    //MARK: - CallBack & Lisetner
    //-------------------------------
    func onToolBarAction(senter:UIButton){
    }
}
