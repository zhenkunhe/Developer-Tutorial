import UIKit
import CoreLocation

//為了iOS 8以下的機型 注意plist要加入兩項參數,並設定yes
class ViewController: UIViewController,CLLocationManagerDelegate {
    
    var m_lable:UILabel!
    var m_locationManager:CLLocationManager!
    var m_latitude:CLLocationDegrees!      //緯度(Double)
    var m_longitude:CLLocationDegrees!     //經度
    var m_heading:CLLocationDegrees!       //方向
    
    //MARK: - Override
    //-------------------------------
    
    override func viewWillAppear(animated: Bool) {
        m_locationManager.startUpdatingLocation()
        m_locationManager.startUpdatingHeading()
    }
    
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        m_lable = makeLabel("Location", font: UIFont.boldSystemFontOfSize(30))
        m_lable.frame.origin = CGPointMake(0, 50)
        m_lable.textAlignment = NSTextAlignment.Left
        self.view.addSubview(m_lable)
        
        m_latitude = 0.0
        m_longitude = 0.0
        m_heading = 0.0
        
        //建立定位資訊管理器
        m_locationManager = CLLocationManager()
        m_locationManager.delegate = self
       
        
        if (UIDevice.currentDevice().systemVersion as NSString).floatValue >= 8.0 {
            //判斷使用者是否接受「app活著的時候,啟用導航」(iOS 7 error)
            m_locationManager.requestWhenInUseAuthorization()
            //判斷使用者是否接受「即使App在背景,也使用導航」(iOS 7 error)
            m_locationManager.requestAlwaysAuthorization()
        }
        
        NSTimer.scheduledTimerWithTimeInterval(1/20, target: self, selector: "onTimeTick:", userInfo: nil, repeats: true)
    }
    
    func makeLabel(text:NSString,font:UIFont) -> UILabel{
        var label:UILabel = UILabel()
        label.text = text
        label.font = font
        label.textColor = UIColor.blackColor()
        label.backgroundColor = UIColor.clearColor()
        label.textAlignment = NSTextAlignment.Center
        label.numberOfLines = 0                                 //自動換行
        label.lineBreakMode = NSLineBreakMode.ByWordWrapping    //??
        self.resizeLabel(label)
        
        return label
    }
    
    func resizeLabel(label:UILabel){
        var text:NSString = label.text!
        var attributes:NSDictionary = [NSFontAttributeName:label.font]
        var maximumLabelSize:CGSize = CGSizeMake(320, 1000)
        var textRect:CGRect = text.boundingRectWithSize(maximumLabelSize, options: NSStringDrawingOptions.UsesLineFragmentOrigin, attributes: attributes, context: nil)
        label.frame.size = textRect.size
    }
    
    func showAlert(title:String,message:String){
        UIAlertView(title: title, message: message, delegate: nil, cancelButtonTitle: "確定")
    }
    
    //MARK: - Listener
    //-------------------------------
    
    func onTimeTick(senter:NSTimer){
        var str:String = "Location\n"
        str += "緯度:\(m_latitude)\n"
        str += "經度:\(m_longitude)\n"
        str += "方向:\(m_heading)"
        m_lable.text = str
        resizeLabel(m_lable)
    }

    func locationManager(manager: CLLocationManager!, didUpdateToLocation newLocation: CLLocation!, fromLocation oldLocation: CLLocation!) {
        //CLLocationCoordinate2D是CLLocation裡面的其中一個屬性,包含了經緯度
        //CLLocation還有其他屬性,例如海拔,
        var coordinate:CLLocationCoordinate2D = newLocation.coordinate
        m_latitude = coordinate.latitude
        m_longitude = coordinate.longitude
        
    }

    
    func locationManager(manager: CLLocationManager!, didUpdateHeading newHeading: CLHeading!) {
        //m_heading = newHeading.trueHeading  //真北
        m_heading = newHeading.magneticHeading  //磁北
    }
    
    func locationManager(manager: CLLocationManager!, didFailWithError error: NSError!) {
        if error.code == kCLErrorDomain{
            showAlert("Error", message: "不允許存取定位資訊")
        }
        else{
            showAlert("Error", message: "定位資訊存取失敗")
        }
    }
}

