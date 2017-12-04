
import UIKit

class ViewController: UIViewController {
    
    var m_photoImg:UIImageView = UIImageView()
    var m_slideBar:UISlider = UISlider()
    var m_sizeLable:UILabel = UILabel()
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.grayColor()
        
        m_photoImg.frame = CGRectMake(0, 0, frame.size.width, frame.size.width)
        m_photoImg.image = UIImage(named: "aa")
        
        m_slideBar.frame = CGRectMake(0, m_photoImg.frame.height, frame.size.width, 100)
        m_slideBar.minimumValue = 0
        m_slideBar.maximumValue = 100
        m_slideBar.value = 70
        self.onSlideAction(m_slideBar)
        m_slideBar.setThumbImage(UIImage(named: "slideBall"), forState: UIControlState.Normal) //圖多大,bar就多大
        //m_slideBar.setMinimumTrackImage(UIImage(named: "slideBall"), forState: UIControlState.Normal)
        //m_slideBar.setMinimumTrackImage(UIImage(named: "slideBall"), forState: UIControlState.Normal)
        m_slideBar.addTarget(self, action: "onSlideAction:", forControlEvents: UIControlEvents.ValueChanged)
        
        m_sizeLable.frame = CGRectMake(0, m_photoImg.frame.height + m_slideBar.frame.height, frame.size.width, 100)
        m_sizeLable.textColor = UIColor.blueColor()
        m_sizeLable.textAlignment = NSTextAlignment.Center
        m_sizeLable.font = UIFont.boldSystemFontOfSize(m_sizeLable.frame.height * 0.85)
        
        self.onSlideAction(m_slideBar)
        self.view.addSubview(m_photoImg)
        self.view.addSubview(m_slideBar)
        self.view.addSubview(m_sizeLable)
    }
    
    func onSlideAction(slideBar:UISlider){
        m_photoImg.alpha = CGFloat(slideBar.value / slideBar.maximumValue)
        let strSlideValue = String(format: "%.2f", slideBar.value)
        m_sizeLable.text = "\(strSlideValue)%"
    }
}

