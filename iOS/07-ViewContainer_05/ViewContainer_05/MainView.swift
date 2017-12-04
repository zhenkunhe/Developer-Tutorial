//
//  MainView.swift
//  ViewContainer_05
//
//  Created by Swift on 2015/5/13.
//  Copyright (c) 2015年 zhenkun. All rights reserved.
//

import UIKit

class MainView: UIView {
    var myPhotoView:MyPhotoView!
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.backgroundColor = UIColor.whiteColor()
        
        var image:UIImage? = UIImage(named: "aa")!
        myPhotoView = MyPhotoView(image: image!, frame: CGRectMake(0, 0, 100, 100))
        
        self.addSubview(myPhotoView)
        
    }

    required init(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func resizeWithTouch(touches: NSSet){
        var touch:UITouch = touches.allObjects[0] as UITouch
        var location:CGPoint = touch.locationInView(self)
        
        UIView.beginAnimations("MoveGroupAni", context: nil)
        myPhotoView?.frame.size = CGSizeMake(location.x, location.y)
        UIView.commitAnimations()
    }
    
    override func touchesBegan(touches: NSSet, withEvent event: UIEvent) {
        self.resizeWithTouch(touches)
    }
    
    override func touchesMoved(touches: NSSet, withEvent event: UIEvent) {
        self.resizeWithTouch(touches)
    }

}
