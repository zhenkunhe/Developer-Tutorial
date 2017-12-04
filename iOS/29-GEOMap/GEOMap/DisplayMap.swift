import UIKit
import MapKit

class DisplayMap:NSObject,MKAnnotation {
    var coordinate:CLLocationCoordinate2D
    var title:String = ""
    var subtitle:String = ""
    
    init(location coord:CLLocationCoordinate2D){
        self.coordinate = coord
        super.init()
    }
}