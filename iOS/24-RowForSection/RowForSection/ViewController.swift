//
//  ViewController.swift
//  RowForSection
//
//  Created by Swift on 2015/5/21.
//  Copyright (c) 2015年 zhenkun. All rights reserved.
//

import UIKit

class ViewController: UIViewController,UITableViewDataSource,UITableViewDelegate  {
    
    var m_dicData:NSMutableDictionary!
    var m_tabView:UITableView!
    
    //MARK: - Override
    //-------------------------------
    
    override func viewDidLoad() {
        super.viewDidLoad()
        var buttonItem:UIBarButtonItem = UIBarButtonItem(title: "Edit", style: UIBarButtonItemStyle.Bordered, target: self, action: "onNavigationBarButtonClick:")
        buttonItem.tintColor = UIColor.blueColor()
        self.navigationItem.rightBarButtonItem = buttonItem
    }
    
    //MARK: - Public
    //-------------------------------
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        m_dicData = NSMutableDictionary()
        m_dicData["國小"] = NSMutableArray(array: ["1","2","3","4"])
        m_dicData["國中"] = NSMutableArray(array: ["a","b","c","d"])
        m_dicData["高中"] = NSMutableArray(array: ["A","B","C","D"])
        m_dicData["大學"] = NSMutableArray(array: ["&","*","$","%"])

        m_tabView = UITableView(frame: CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height))
        m_tabView.autoresizingMask = UIViewAutoresizing.FlexibleHeight | UIViewAutoresizing.FlexibleWidth   //旋轉會自動調整寬或高
        m_tabView.backgroundColor = UIColor.clearColor()
        m_tabView.setEditing(false, animated: false)
        m_tabView.dataSource = self
        m_tabView.delegate = self
        self.view.addSubview(m_tabView)
    }
    
    
    //MARK: - UITableViewDataSource
    //-------------------------------
    
    //set 群組數量
    func numberOfSectionsInTableView(tableView: UITableView) -> Int {
        return m_dicData.allKeys.count
    }
    
    //set 群組項目數量
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return (m_dicData.objectForKey(m_dicData.allKeys[section])! as NSMutableArray).count
    }
    
    //set 群組項目
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell{
        var kDisplayCell_ID:String = "DisplayCellID"
        var cell:UITableViewCell? = tableView.dequeueReusableCellWithIdentifier(kDisplayCell_ID) as? UITableViewCell
        
        if cell == nil{
            cell = UITableViewCell(style: UITableViewCellStyle.Default, reuseIdentifier: kDisplayCell_ID)
            cell!.selectionStyle = UITableViewCellSelectionStyle.Blue
            cell!.showsReorderControl = true    //編輯表格移動時軌跡顯示
        }
        let strKey:String = m_dicData.allKeys[indexPath.section] as String
        let aryData:NSMutableArray = m_dicData.objectForKey(strKey) as NSMutableArray
        cell!.textLabel!.text = aryData[indexPath.row] as? String
        cell!.textLabel!.textColor = UIColor.darkGrayColor()
        cell!.accessoryType = UITableViewCellAccessoryType.DisclosureIndicator  //右邊會有箭頭
        return cell!
    }
    
    //set 群組Title
    func tableView(tableView: UITableView, titleForHeaderInSection section: Int) -> String?{
        return m_dicData.allKeys[section] as? String
    }
    
    //刪除
    func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == UITableViewCellEditingStyle.Delete{
            ((m_dicData.objectForKey(m_dicData.allKeys[indexPath.section])) as NSMutableArray).removeObjectAtIndex(indexPath.row)
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.Fade)
        }
    }
    
    //移動 （不做這個,Edit時不會產生移動選項）
    func tableView(tableView: UITableView, moveRowAtIndexPath sourceIndexPath: NSIndexPath, toIndexPath destinationIndexPath: NSIndexPath) {
        var tempObj:AnyObject = ((m_dicData.objectForKey(m_dicData.allKeys[sourceIndexPath.section])) as NSMutableArray)[sourceIndexPath.row]
        ((m_dicData.objectForKey(m_dicData.allKeys[sourceIndexPath.section])) as NSMutableArray).removeObjectAtIndex(sourceIndexPath.row)
        ((m_dicData.objectForKey(m_dicData.allKeys[destinationIndexPath.section])) as NSMutableArray).insertObject(tempObj, atIndex: destinationIndexPath.row)
    }
    
    //MARK: - UITableViewDelegate
    //-------------------------------
    
    func tableView(tableView: UITableView, heightForRowAtIndexPath indexPath: NSIndexPath) -> CGFloat {
        return 100.0
    }
    
    func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
    }
    
    func tableView(tableView: UITableView, editingStyleForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCellEditingStyle {
        return UITableViewCellEditingStyle.Delete
    }
    
    //MARK: - ButtonClick Listener
    //-------------------------------
    
    func onNavigationBarButtonClick(senter:UIBarButtonItem){
        if senter.title == "Edit" {
            senter.title = "Done"
            senter.tintColor = UIColor.purpleColor()
            m_tabView.setEditing(true, animated: true)
        }
        else {
            senter.title = "Edit"
            senter.tintColor = UIColor.blueColor()
            m_tabView.setEditing(false, animated: true)
        }
    }
}

