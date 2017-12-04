//
//  ViewController.swift
//  TagBar
//
//  Created by Swift on 2015/5/19.
//  Copyright (c) 2015年 zhenkun. All rights reserved.
//

import UIKit

class ViewController: UIViewController,UITabBarControllerDelegate {
    var m_tabBar:UITabBarController!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        m_tabBar = UITabBarController()
        
        var controllers:[UIViewController] = [PageOneViewController(),PageTwoViewController(),PageThreeViewController()]
        m_tabBar.viewControllers = controllers
        m_tabBar.delegate = self
        
        for (var i = 0 ; i <= m_tabBar.viewControllers!.count ; i++) {  //初始全部跑一遍回到原點,起始才會出現全部選項
            m_tabBar.selectedIndex = i % m_tabBar.viewControllers!.count
        }
        self.view.addSubview(m_tabBar.view)
    }
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
    }
}

