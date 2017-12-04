
import UIKit

class ViewController: UIViewController,GADBannerViewDelegate {

    var m_GADBannerView:GADBannerView!
    var m_isGADActivite:Bool!
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        m_GADBannerView = GADBannerView(adSize: kGADAdSizeSmartBannerPortrait)
        m_GADBannerView.frame.origin = CGPointMake(0, frame.size.height)
        m_GADBannerView.layer.masksToBounds = true
        m_GADBannerView.layer.cornerRadius = 20.0
        m_GADBannerView.adUnitID = "ca-app-pub-5592520181032142/8744418913"
        m_GADBannerView.rootViewController = self
        m_GADBannerView.delegate = self
        self.view.addSubview(m_GADBannerView)
        
        m_GADBannerView.loadRequest(GADRequest())
    }
    
    func showAdBanner(isShow:Bool){
        m_isGADActivite = isShow
        UIView.beginAnimations("", context: nil)
        if isShow{
            m_GADBannerView.frame = CGRectMake(0, self.view.frame.size.height - m_GADBannerView.frame.size.height, m_GADBannerView.frame.size.width, m_GADBannerView.frame.size.height)
        }else{
            m_GADBannerView.frame = CGRectMake(0, self.view.frame.size.height, m_GADBannerView.frame.size.width, m_GADBannerView.frame.size.height)
        }
        UIView.commitAnimations()
    }
    
    func adViewDidReceiveAd(view: GADBannerView!) {
        println("收到Google廣告")
        showAdBanner(true)
    }
    
    func adView(view: GADBannerView!, didFailToReceiveAdWithError error: GADRequestError!) {
        println("Google接收廣告失敗")
    }
    
    func adViewWillPresentScreen(adView: GADBannerView!) {
        println("點擊廣告")
        showAdBanner(false)
    }
    
    func adViewWillDismissScreen(adView: GADBannerView!) {
        println("關閉廣告視窗")
        showAdBanner(false)
    }
    
    func adViewWillLeaveApplication(adView: GADBannerView!) {
        println("廣告離開應用程式")
        showAdBanner(false)
    }
}

