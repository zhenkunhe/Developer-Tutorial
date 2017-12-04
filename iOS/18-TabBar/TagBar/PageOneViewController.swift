
import UIKit

class PageOneViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.redColor()
        
        var tabBarItem:UITabBarItem = UITabBarItem(title: "第一頁", image: UIImage(named: "aa"), selectedImage: UIImage(named: "bb"))
        self.tabBarItem = tabBarItem
    }
    override func viewWillAppear(animated: Bool) {
        var tabBarItem:UITabBarItem = UITabBarItem(title: "第一頁", image: UIImage(named: "aa"), selectedImage: UIImage(named: "bb"))
        self.tabBarItem = tabBarItem
    }

}
