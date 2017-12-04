import UIKit
import CoreLocation
import MapKit

//為了iOS 8以下的機型 注意plist要加入兩項參數,並設定yes
class ViewController: UIViewController,MKMapViewDelegate {
    
    var m_mapView:MKMapView!
    var m_annotation:AnyObject!
    var m_geocoder:CLGeocoder!
    let MAP_DIR:Int = 0
    
    //MARK: - Override
    //-------------------------------
    
    override func viewWillAppear(animated: Bool) {

    }
    
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        //MapView
        m_mapView = MKMapView(frame: frame)
        //delegate處理放大縮小等指標動作
        m_mapView.delegate = self
        self.view.addSubview(m_mapView)
        
        //長按手勢偵測元件,用來掛載在某一個View上
        //View Touch Begin觸發後,手勢才觸發
        //手勢結束觸發後,才觸發Touch End
        var m_longPressRecognizer:UILongPressGestureRecognizer = UILongPressGestureRecognizer(target: self, action: "handletLongPressForm:")
        m_longPressRecognizer.minimumPressDuration = 0.45
        m_longPressRecognizer.cancelsTouchesInView = false
        m_mapView.addGestureRecognizer(m_longPressRecognizer)
        
        m_geocoder = CLGeocoder()
    }
    
    //依照觸碰地圖的位置 換算出該地理座標 並 插上大頭針
    func showPointAnnotationWithTouchLacation(touchPoint:CGPoint){
        var locationInMapView:CLLocationCoordinate2D = m_mapView.convertPoint(touchPoint, toCoordinateFromView: m_mapView)
        var ann:DisplayMap = DisplayMap(location: locationInMapView)
        ann.title = "標題"
        ann.subtitle = NSString(format: "緯度:%.3f 經度:%.3f", locationInMapView.latitude,locationInMapView.longitude)
        m_mapView.addAnnotation(ann)
    }
    
    //網路查詢地址：Handler是一種條件觸發的callBack
    func refreshGeoInfo(newLocation:CLLocation){
        m_geocoder.reverseGeocodeLocation(newLocation, completionHandler: completionGeocodeHandler)
    }
    
    //MARK: - Handler
    //-------------------------------
    
    func completionGeocodeHandler(placemarks:[AnyObject]!, error:NSError!) -> Void{
        if error != nil{
            println("Reverse geocoder failed with error" + error.localizedDescription)
            return
        }
        for placemark in placemarks {
            println("國家:\(placemark.country)")
            println("國家代碼:\(placemark.ISOcountryCode)")
            println("行政區/州(市)名:\(placemark.administrativeArea)")
            println("次行政區(縣)名:\(placemark.subAdministrativeArea)")
            println("城市名稱:\(placemark.locality)")
            println("次城市名稱:\(placemark.thoroughfare)")
            println("街道號碼:\(placemark.subThoroughfare)")
            println("郵遞區號:\(placemark.postalCode)")
        }
        return
    }

    
    //MARK: - Listener
    //-------------------------------
    
    func handletLongPressForm(recognizer:UILongPressGestureRecognizer){
        switch recognizer.state{
            
        case UIGestureRecognizerState.Began:
            self.showPointAnnotationWithTouchLacation(recognizer.locationOfTouch(0, inView: m_mapView))
            println("began")
            
        case UIGestureRecognizerState.Changed:
            self.showPointAnnotationWithTouchLacation(recognizer.locationOfTouch(0, inView: m_mapView))
            println("changed")
            
        case UIGestureRecognizerState.Ended:
            self.showPointAnnotationWithTouchLacation(recognizer.locationOfTouch(0, inView: m_mapView))
            var touchPoing:CGPoint = recognizer.locationOfTouch(0, inView: m_mapView)
            var touchMapCoordinate:CLLocationCoordinate2D = m_mapView.convertPoint(touchPoing, toCoordinateFromView: m_mapView)
            var newLocation:CLLocation = CLLocation(latitude: touchMapCoordinate.latitude, longitude: touchMapCoordinate.longitude)
            refreshGeoInfo(newLocation)
            println("end")
            
        case UIGestureRecognizerState.Cancelled:
            println("cancel")
        default:
            break
        }
    }
    
    //MARK: - Override
    //-------------------------------
    
    override func touchesBegan(touches: NSSet, withEvent event: UIEvent) {
        //取得點擊座標
        var touch:UITouch = touches.allObjects[0] as UITouch
        let location = touch.locationInView(self.view)
        println("touch Begin")
    }
    
    override func touchesEnded(touches: NSSet, withEvent event: UIEvent) {
        //取得點擊座標
        var touch:UITouch = touches.allObjects[0] as UITouch
        let location = touch.locationInView(self.view)
        println("touch End")
    }
    
    //MARK: - Delegate
    //-------------------------------
    
    func mapView(mapView: MKMapView!, didSelectAnnotationView view: MKAnnotationView!) {
        if let displayMap:DisplayMap = view.annotation as? DisplayMap{
            var coord:CLLocationCoordinate2D = displayMap.coordinate
            if(MAP_DIR == 1){
                //啟動Google Map
                var mapLink:String = String(format: "comgooglemaps://?daddr=%f,%f&saddr=%f,%f&mrsp=0&ht=it&ftr=0",coord.latitude , coord.longitude , coord.latitude , coord.longitude)//所在地＆目的地
                UIApplication.sharedApplication().openURL(NSURL(string: mapLink)!)
            }
            else{
                //啟動iOS Map
                var destCoord:CLLocationCoordinate2D = CLLocationCoordinate2DMake(coord.latitude, coord.longitude)
                var destPlacemark:MKPlacemark = MKPlacemark(coordinate: destCoord, addressDictionary: nil)
                var destMapItem:MKMapItem = MKMapItem(placemark: destPlacemark)
                
                var currentMapItem:MKMapItem = MKMapItem.mapItemForCurrentLocation()    //取得現在位置 
                //開啟模式選擇開車模式
                MKMapItem.openMapsWithItems([currentMapItem,destMapItem], launchOptions: [MKLaunchOptionsDirectionsModeKey:MKLaunchOptionsDirectionsModeDriving])
            }
        }
    }
    
    //要插上大頭針時的運作
    //同UITable View,取得沒在使用的大頭針,並  初始化 或 指向已經存在的數據包
    func mapView(mapView: MKMapView!, viewForAnnotation annotation: MKAnnotation!) -> MKAnnotationView!{
        m_annotation = annotation
        var annotationIdentifier:String = "AnnotationIdentifier"
        var coustomPinView:MKPinAnnotationView? = m_mapView.dequeueReusableAnnotationViewWithIdentifier(annotationIdentifier) as? MKPinAnnotationView
        if coustomPinView == nil {
            coustomPinView = MKPinAnnotationView(annotation: annotation, reuseIdentifier: annotationIdentifier)
        }
        else{
            coustomPinView?.annotation = annotation
        }
        //要給圖片,就不能移動
        coustomPinView?.pinColor = MKPinAnnotationColor.Purple
        coustomPinView?.draggable = true    //是否可以抓取
        coustomPinView?.animatesDrop = true     //是否有抓取動畫
        coustomPinView?.canShowCallout = true   //是否出現註解
        coustomPinView?.image = UIImage(named: "aa")

        return coustomPinView
    }
}

