
import UIKit

class PopViewController: UIViewController {
    
    //寬高自動定義,無法改變
    override func viewDidLoad() {
        super.viewDidLoad()

        self.view.backgroundColor = UIColor.redColor()
        self.navigationItem.title = "一號子畫面"
    }
}
