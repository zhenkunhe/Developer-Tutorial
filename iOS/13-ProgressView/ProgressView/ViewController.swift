//
//  ViewController.swift
//  ProgressView
//
//  Created by Swift on 2015/5/15.
//  Copyright (c) 2015年 zhenkun. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    var m_progerss:UIProgressView!
    var m_aniSwitch:UISwitch!
    var m_timer:NSTimer!
    var timesPerSeconds:Double = 100.0  //timer每秒運行幾次
    var progressValue:Float = 0.0       //progress的值目前到多少了
    var progressSpeed:Float = 0.1       //progress運行的倍速
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.grayColor()
        
        m_progerss = UIProgressView(frame: CGRectMake(0, 100, frame.size.width, 0))
        m_progerss.transform = CGAffineTransformMakeScale(1, 5)
        m_progerss.trackTintColor = UIColor.lightGrayColor()          //未完成階段底色
        m_progerss.progressTintColor = UIColor.greenColor()     //完成階段底色
        m_progerss.progressViewStyle = UIProgressViewStyle.Default
        
        m_aniSwitch = UISwitch()
        m_aniSwitch.center = CGPointMake(frame.size.width/2, frame.size.height/2)
        m_aniSwitch.addTarget(self, action: Selector("onButtonAction:"), forControlEvents: UIControlEvents.TouchUpInside)
        m_aniSwitch.transform = CGAffineTransformMakeScale(2.0, 2.0)
        m_aniSwitch.tintColor = UIColor.whiteColor()
        
        self.view.addSubview(m_progerss)
        self.view.addSubview(m_aniSwitch)
    }
    
    func onButtonAction(sender:UISwitch) {
        if sender.on && m_progerss.progress < 1 {
            m_timer = NSTimer.scheduledTimerWithTimeInterval(1/timesPerSeconds, target: self, selector: Selector("onTimeTick"), userInfo: nil, repeats: true)
        }
        else {
            m_timer.invalidate()
        }
    }
    
    func onTimeTick(){
        m_progerss.setProgress(progressValue, animated: true)
        progressValue += 1 / Float(timesPerSeconds) * progressSpeed
        
        if m_progerss.progress == 1{
            UIAlertView(title: "完成", message: "恭喜你完成了！", delegate: nil, cancelButtonTitle: "結束").show()
            m_timer.invalidate()
        }
    }
}