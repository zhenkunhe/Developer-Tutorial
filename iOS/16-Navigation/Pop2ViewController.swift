

import UIKit

class Pop2ViewController: UIViewController {
    var m_rightBtnItem:UIBarButtonItem!
    var popRightVC:Pop3ViewController?
    var m_isAlready:Bool = false
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        m_isAlready = false
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        m_isAlready = true
    }
    
    override func viewDidDisappear(animated: Bool) {
        super.viewDidDisappear(animated)
        m_isAlready = false
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.view.backgroundColor = UIColor.orangeColor()
        self.navigationItem.title = "二號子畫面"
        
        m_rightBtnItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Done, target: self, action: Selector("onSelectRightAction:"))
        self.navigationItem.rightBarButtonItem = m_rightBtnItem
    }
    
    
    //MARK: - CallBack & Lisetner
    //-------------------------------
    
    func onSelectRightAction(sender:UIBarButtonItem){
        if popRightVC == nil {
            popRightVC = Pop3ViewController()
        }
        else if m_isAlready {
            self.navigationController?.pushViewController(popRightVC!, animated: true)
        }
    }
}
