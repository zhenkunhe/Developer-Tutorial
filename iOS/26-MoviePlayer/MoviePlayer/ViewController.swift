import UIKit
import MediaPlayer

class ViewController: UIViewController {
    var m_player:MPMoviePlayerController!
    var m_volumeView:MPVolumeView!
    
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        m_player = makeMoviePlayer("aa",type: "mp4")
        m_player.view.frame = self.view.frame
        self.view.addSubview( m_player.view )
        
        //self向觀察中心註冊,願意監聽m_player送來的某項訊息(name),若收到則實作selector
        NSNotificationCenter.defaultCenter().addObserver(self, selector: "onMoviePlayBackDidFinish:", name: MPMoviePlayerPlaybackDidFinishNotification, object: m_player)
        
        m_player.prepareToPlay()
        m_player.play()
        
        m_volumeView = MPVolumeView()
        m_volumeView.frame = CGRectMake(0, self.view.frame.size.height - 60, self.view.frame.size.width , 40)
        m_volumeView.showsVolumeSlider = true
        self.view.addSubview( m_volumeView )
    }
    
    func makeMoviePlayer(res:String,type:String) -> MPMoviePlayerController{
        //取得檔案路徑
        var path:String = NSBundle.mainBundle().pathForResource(res, ofType: type)!
        var url:NSURL = NSURL.fileURLWithPath(path)!
        
        //生成播放器
        var player:MPMoviePlayerController = MPMoviePlayerController(contentURL: url)
        player.scalingMode = MPMovieScalingMode.AspectFill  //影片某一維度填滿player畫面
        player.repeatMode = MPMovieRepeatMode.None
        player.controlStyle = MPMovieControlStyle.Embedded  //上一格,下一格等操作
        //同UITable,發生旋轉時,自動調整寬高
        player.view.autoresizingMask = UIViewAutoresizing.FlexibleWidth |  UIViewAutoresizing.FlexibleHeight
        return  player
    }
    
    func onMoviePlayBackDidFinish(sendet:MPMoviePlayerController){
        
    }
    
    override func willAnimateRotationToInterfaceOrientation(toInterfaceOrientation: UIInterfaceOrientation, duration: NSTimeInterval) {
        if (toInterfaceOrientation == UIInterfaceOrientation.Portrait){
            println("直立,Home在正下方")
        }
        else if(toInterfaceOrientation == UIInterfaceOrientation.LandscapeLeft){
            println("左朝上")
        }
        else if(toInterfaceOrientation == UIInterfaceOrientation.LandscapeRight){
            println("右朝上")
        }
        else{
        }
    }
}

