import UIKit

class GoogleMapViewController: UIViewController {
    var m_panoView:GMSPanoramaView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        m_panoView = GMSPanoramaView(frame: frame)
        self.view = m_panoView
        m_panoView.moveNearCoordinate(CLLocationCoordinate2DMake(-33.732, 150.312))
    }
    
    func setLocation(latitude:CLLocationDegrees,longitude: CLLocationDegrees){
        m_panoView.moveNearCoordinate(CLLocationCoordinate2DMake(latitude, longitude))
    }
    
}