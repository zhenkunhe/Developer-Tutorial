import UIKit

class ViewController: UIViewController {
    var m_scrollView:UIScrollView!
    var m_aryPath:[String]!
    var m_aryImages:[UIImageView]!
    var m_index:Int = 0
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        m_scrollView = UIScrollView()
        m_scrollView.frame = CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)
        m_scrollView.contentSize = CGSizeMake(0,0)
        m_scrollView.backgroundColor = UIColor.clearColor()
        m_scrollView.showsVerticalScrollIndicator = true
        m_scrollView.showsHorizontalScrollIndicator = false
        self.view.addSubview(m_scrollView)
        
        m_aryPath = ["http://www.snoworks.co.uk/blog/wp-content/uploads/2011/02/adventure_home.jpg","http://images.huffingtonpost.com/2014-01-09-sillouhette.jpg","https://s-media-cache-ak0.pinimg.com/originals/8d/a0/12/8da0129e875e8d78734616ed6b058ff8.jpg","http://www.stepaheadtravel.com/images/Adventure.jpg","http://www.referenceforbusiness.com/photos/outdoor-adventure-travel-company-694.jpg","http://www.real-adventure.co.uk/uploads/library/126/real_adventure_123__large.jpg","http://www.snoworks.co.uk/blog/wp-content/uploads/2011/02/adventure_home.jpg","http://images.huffingtonpost.com/2014-01-09-sillouhette.jpg","https://s-media-cache-ak0.pinimg.com/originals/8d/a0/12/8da0129e875e8d78734616ed6b058ff8.jpg","http://www.stepaheadtravel.com/images/Adventure.jpg","http://www.referenceforbusiness.com/photos/outdoor-adventure-travel-company-694.jpg","http://www.real-adventure.co.uk/uploads/library/126/real_adventure_123__large.jpg","http://www.snoworks.co.uk/blog/wp-content/uploads/2011/02/adventure_home.jpg","http://images.huffingtonpost.com/2014-01-09-sillouhette.jpg","https://s-media-cache-ak0.pinimg.com/originals/8d/a0/12/8da0129e875e8d78734616ed6b058ff8.jpg","http://www.stepaheadtravel.com/images/Adventure.jpg","http://www.referenceforbusiness.com/photos/outdoor-adventure-travel-company-694.jpg","http://www.real-adventure.co.uk/uploads/library/126/real_adventure_123__large.jpg","http://www.snoworks.co.uk/blog/wp-content/uploads/2011/02/adventure_home.jpg","http://images.huffingtonpost.com/2014-01-09-sillouhette.jpg","https://s-media-cache-ak0.pinimg.com/originals/8d/a0/12/8da0129e875e8d78734616ed6b058ff8.jpg","http://www.stepaheadtravel.com/images/Adventure.jpg","http://www.referenceforbusiness.com/photos/outdoor-adventure-travel-company-694.jpg","http://www.real-adventure.co.uk/uploads/library/126/real_adventure_123__large.jpg"]
        var imgW:CGFloat = 120
        var imgH:CGFloat = 120
        var hLineCount:Int = Int(frame.size.width / imgW)
        var space:CGFloat = (frame.size.width - imgW * CGFloat(hLineCount)) / (CGFloat(hLineCount + 1))
        var picImg:UIImageView!
        
        /*
        for (var i = 0 ; i < m_aryPath.count ; i++) {
            picImg = UIImageView(frame: CGRectMake( space + CGFloat(i % hLineCount) * (imgW + space) , space + CGFloat(i / hLineCount) * (imgH + space), imgW, imgH))
            picImg.backgroundColor = UIColor.blackColor()
            picImg.layer.masksToBounds = true
            picImg.layer.cornerRadius = 5.0
            var imageUrl:NSURL = NSURL(string: m_aryPath[i])!
            picImg.image = UIImage(data: NSData(contentsOfURL: imageUrl)!)!
            m_scrollView.addSubview(picImg)
        }
        m_scrollView.contentSize = CGSizeMake(self.view.frame.size.width,picImg.frame.origin.y + imgH + space)
        */
        
        //第二種方法:非同步下載
        m_aryImages = [UIImageView]()
        
        for var i = 0 ; i < m_aryPath.count ; i++ {
            picImg = UIImageView(frame: CGRectMake( space + CGFloat(i % hLineCount) * (imgW + space) , space + CGFloat(i / hLineCount) * (imgH + space), imgW, imgH))
            picImg.backgroundColor = UIColor.blackColor()
            picImg.layer.masksToBounds = true
            picImg.layer.cornerRadius = 5.0
            
            m_aryImages.append(picImg)
            m_scrollView.addSubview(picImg)
            
            //將子分流加到管理器做處理,這樣可以分配一部分資源處理請求,一部分資源繼續跑迴圈
            //不必一邊在等網路回傳資料的過程卡住
            var imageUrl:NSURL = NSURL(string: m_aryPath[i])!
            let request:NSURLRequest = NSURLRequest(URL: imageUrl)
            NSURLConnection.sendAsynchronousRequest(request, queue: NSOperationQueue.mainQueue(), completionHandler: urlHandler)
        }
        m_scrollView.contentSize = CGSizeMake(self.view.frame.size.width,picImg.frame.origin.y + imgH + space)
    }
    
    func urlHandler(response:NSURLResponse!, data:NSData!, error:NSError!) -> Void{
        if error == nil {
            self.m_aryImages[self.m_index].image = UIImage(data: data)
            self.m_index++
        }
        else{
            println("Error:\(error.localizedDescription)")
        }
    }
}

