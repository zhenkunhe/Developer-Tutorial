import UIKit
import SpriteKit

class GameViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        var spriteView:SKView = self.view as SKView
        spriteView.showsDrawCount = true
        spriteView.showsNodeCount = true
        spriteView.showsFPS = true
    }
    
    override func viewDidAppear(animated: Bool) {
        var hello:MenuSence = MenuSence()
        hello.size = CGSizeMake(self.view.frame.width, self.view.frame.size.height)
        var spriteView:SKView = self.view as SKView
        spriteView.presentScene(hello)
    }
}
