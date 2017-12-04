
import UIKit

class Book: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    func refreshWithFrame(frame:CGRect,filePath:String){
        var contentStr:String = String(contentsOfFile: filePath , encoding: NSUTF8StringEncoding, error: nil)!
        let textView = UITextView(frame: CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height))
        textView.text = contentStr
        textView.backgroundColor = UIColor.orangeColor()
        textView.layer.borderColor = UIColor.blueColor().CGColor
        textView.layer.borderWidth = 4.5
        textView.layer.cornerRadius = 10
        textView.font = UIFont.boldSystemFontOfSize(20)
        textView.textAlignment = NSTextAlignment.Left
        textView.editable = false
        //使內文保持在UITextView內
        textView.clipsToBounds = true
        
        self.view.addSubview(textView)
    }
}
