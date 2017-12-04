

import UIKit

class PageThreeViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.greenColor()
        
        var tabBarItem:UITabBarItem = UITabBarItem(title: "第三頁", image: UIImage(named: "aa"), selectedImage: UIImage(named: "bb"))
        self.tabBarItem = tabBarItem
    }
}