
import UIKit

class ViewController: UIViewController {
    var m_gooMapVC:GoogleMapViewController!
    var m_btn0:UIButton! , m_btn1:UIButton! ,m_btn2:UIButton!
    
    
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        m_gooMapVC = GoogleMapViewController()
        m_gooMapVC.refreshWithFrame(CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height - 200))
        self.view.addSubview(m_gooMapVC.view)
        
        var width:CGFloat = self.view.frame.size.width / 3
        var height:CGFloat = 200
        m_btn0 = UIButton(frame: CGRectMake(0, m_gooMapVC.view.frame.size.height, width, height))
        m_btn1 = UIButton(frame: CGRectMake(width, m_gooMapVC.view.frame.size.height, width, height))
        m_btn2 = UIButton(frame: CGRectMake(width * 2, m_gooMapVC.view.frame.size.height, width, height))
        m_btn0.setTitle("0", forState: UIControlState.Normal)
        m_btn1.setTitle("1", forState: UIControlState.Normal)
        m_btn2.setTitle("2", forState: UIControlState.Normal)
        m_btn0.titleLabel?.font = UIFont.boldSystemFontOfSize(height * 0.8)
        m_btn1.titleLabel?.font = UIFont.boldSystemFontOfSize(height * 0.8)
        m_btn2.titleLabel?.font = UIFont.boldSystemFontOfSize(height * 0.8)
        m_btn0.tag = 0
        m_btn1.tag = 1
        m_btn2.tag = 2
        m_btn0.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        m_btn1.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        m_btn2.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        self.view.addSubview(m_btn0)
        self.view.addSubview(m_btn1)
        self.view.addSubview(m_btn2)
    }
    
    func onButtonAction(sender:UIButton){
        switch sender.tag {
        case 0:
            m_gooMapVC.setLocation(-33.732, longitude: 150.312)
        case 1:
            m_gooMapVC.setLocation(25.028435, longitude: 121.523730)
        case 2:
            m_gooMapVC.setLocation(25.032401, longitude: 121.562086)
        default:
            break
        }
    }
    
}

