//
//  ViewController.swift
//  Navigation
//
//  Created by Swift on 2015/5/19.
//  Copyright (c) 2015年 zhenkun. All rights reserved.
//

import UIKit

class ViewController: UIViewController {
    var m_rightBtnItem:UIBarButtonItem!
    var m_leftBtnItem:UIBarButtonItem!
    var popLeftVC:PopViewController?
    var popRightVC:Pop2ViewController?
    var lastPoint:CGPoint?
    var m_isAlready:Bool = false    //不didAppear完成(連點過快),就觸發push有機會當機
    
    override func viewWillAppear(animated: Bool) {
        super.viewWillAppear(animated)
        m_isAlready = false
    }
    
    override func viewDidAppear(animated: Bool) {
        super.viewDidAppear(animated)
        m_isAlready = true
    }
    
    override func viewDidDisappear(animated: Bool) {
        super.viewDidDisappear(animated)
        m_isAlready = false
    }
    
    //window掛載navigationController後呈現時,才執行以下
    //此時的self.navigationController與self.navigationItem才可抓到
    override func viewDidLoad() {
        super.viewDidLoad()
        m_leftBtnItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Compose , target: self, action: Selector("onSelectLeftAction:"))
        self.navigationItem.leftBarButtonItem = m_leftBtnItem
        
        m_rightBtnItem = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.Done, target: self, action: Selector("onSelectRightAction:"))
        self.navigationItem.rightBarButtonItem = m_rightBtnItem
        
        var titleView:UIImageView = UIImageView(frame: CGRectMake(0, 0, 30, 30))
        titleView.image = UIImage(named: "icon")
        self.navigationItem.titleView =  titleView
    }

    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        var btn:UIButton = UIButton()
        btn.frame.size = CGSizeMake(200, 50)
        btn.center = CGPointMake(frame.size.width/2, frame.size.height/2)
        btn.setTitle("Lock", forState: UIControlState.Normal)
        btn.addTarget(self, action: Selector("onButtonAction:"), forControlEvents: UIControlEvents.TouchUpInside)
        btn.titleLabel?.textColor = UIColor.blackColor()
        self.view.addSubview(btn)
    }
    
    //MARK: - CallBack & Lisetner
    //-------------------------------
    
    func onSelectRightAction(sender:UIBarButtonItem){
        if popRightVC == nil {
            popRightVC = Pop2ViewController()
        }
        else if m_isAlready {
            self.navigationController?.pushViewController(popRightVC!, animated: true)
        }
    }
    
    func onSelectLeftAction(sender:UIBarButtonItem){
        if popLeftVC == nil {
            popLeftVC = PopViewController()
        }
        self.navigationController?.pushViewController(popLeftVC!, animated: true)
    }
    
    func onButtonAction(sender:UIButton){
        if self.navigationItem.rightBarButtonItem == nil{
            self.navigationItem.rightBarButtonItem = m_rightBtnItem
        }
        else{
            self.navigationItem.rightBarButtonItem = nil
        }
    }
    
    //MARK: - Override
    //-------------------------------
    override func touchesBegan(touches: NSSet, withEvent event: UIEvent) {
        var touch:UITouch = touches.allObjects[0] as UITouch
        var location:CGPoint = touch.locationInView(self.view)
        lastPoint = location
    }
    
    override func touchesEnded(touches: NSSet, withEvent event: UIEvent) {
        var touch:UITouch = touches.allObjects[0] as UITouch
        var location:CGPoint = touch.locationInView(self.view)
        if lastPoint?.x > location.x {
            onSelectRightAction(m_rightBtnItem)
        }
        else{
            onSelectLeftAction(m_leftBtnItem)
        }
    }
}

