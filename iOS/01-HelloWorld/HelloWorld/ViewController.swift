import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var lable: UILabel!
    @IBOutlet weak var test: UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBAction func test(sender: UIButton) {
        lable.hidden = !lable.hidden
    }
}

