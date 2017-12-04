//
//  Pop3ViewController.swift
//  UIBarButtonItem
//
//  Created by Swift on 2015/5/12.
//  Copyright (c) 2015年 zhenkun. All rights reserved.
//

import UIKit

class Pop3ViewController: UIViewController {
    
    var m_isAlready:Bool = false
    
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
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.view.backgroundColor = UIColor.greenColor()
        self.navigationItem.title = "三號子畫面"
    }
}
