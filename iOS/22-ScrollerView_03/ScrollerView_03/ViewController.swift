//
//  ViewController.swift
//  ScrollerView_03
//
//  Created by Swift on 2015/5/21.
//  Copyright (c) 2015年 zhenkun. All rights reserved.
//

import UIKit

class ViewController: UIViewController,UIScrollViewDelegate {
    var m_scrollView:UIScrollView!
    var m_myImg:UIImageView!
    var m_btn:UIButton!
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        m_scrollView = UIScrollView()
        m_scrollView.frame = CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height)
        m_scrollView.contentSize = CGSizeMake(self.view.frame.size.width,self.view.frame.size.height)
        m_scrollView.backgroundColor = UIColor.clearColor()
        m_scrollView.showsVerticalScrollIndicator = true
        m_scrollView.showsHorizontalScrollIndicator = false
        m_scrollView.delegate = self
        m_scrollView.maximumZoomScale = 5.0
        m_scrollView.minimumZoomScale = 0.75
        m_scrollView.pagingEnabled = true
        
        m_myImg = UIImageView(image: UIImage(named: "rr") )
        m_myImg.frame = m_scrollView.frame
        m_scrollView.addSubview(m_myImg)
        
        m_btn = UIButton(frame: CGRectMake(0, 0, 50, 50))
        m_btn.setTitle("囧", forState: UIControlState.Normal)
        m_btn.tintColor = UIColor.redColor()
        m_btn.titleLabel?.font = UIFont.boldSystemFontOfSize(50)
        m_btn.addTarget(self, action: "onButtonAction:", forControlEvents: UIControlEvents.TouchUpInside)
        m_scrollView.addSubview(m_btn)
        
        self.view.addSubview(m_scrollView)
    }
    
    func viewForZoomingInScrollView(scrollView: UIScrollView) -> UIView? {
        return m_myImg
    }
    
    func onButtonAction(senter:UIButton){
        m_scrollView.setZoomScale(1.0, animated: true)
    }
    
}

