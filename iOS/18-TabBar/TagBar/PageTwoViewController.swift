

import UIKit

class PageTwoViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.blueColor()
        
        var tabBarItem:UITabBarItem = UITabBarItem(title: "第二頁", image: UIImage(named: "aa"), selectedImage: UIImage(named: "bb"))
        self.tabBarItem = tabBarItem
    }
}
