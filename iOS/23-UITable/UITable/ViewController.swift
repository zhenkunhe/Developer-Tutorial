//
//  ViewController.swift
//  UITable
//
//  Created by Swift on 2015/5/21.
//  Copyright (c) 2015年 zhenkun. All rights reserved.
//

import UIKit

class ViewController: UIViewController,UITableViewDataSource,UITableViewDelegate {
    
    var m_aryBookKey:NSMutableArray!
    var m_aryContent:NSMutableArray!
    var m_aryImgs:NSMutableArray!
    var m_aryBook:NSMutableArray!
    var m_aryPackage:NSMutableArray!
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
        m_aryBookKey = NSMutableArray(objects: "什麼是Object C","開發環境","前言","建構子初始化","@property & @synthesize","繼承","多型 & 覆寫 ","類別部署","什麼是Swift","其他1","其他2")
        m_aryContent = NSMutableArray(objects: "aaa","bbb","ccc","ddd","eee","fff","ggg","hhh","iii","jjj","kkk")
        m_aryImgs = NSMutableArray(objects: "aa","bb","aa","bb","aa","bb","aa","bb","aa","bb","aa")
        m_aryBook = NSMutableArray(objects: NSNull(),NSNull(),NSNull(),NSNull(),NSNull(),NSNull(),NSNull(),NSNull(),NSNull(),NSNull(),NSNull())
        m_aryPackage = NSMutableArray(objects: m_aryBookKey,m_aryContent,m_aryImgs,m_aryBook)
        
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
    
    //set 項目數量
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return m_aryBookKey.count
    }
    
    //set 項目內容
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell{
        var kDisplayCell_ID:String = "DisplayCellID"
        var cell:UITableViewCell? = tableView.dequeueReusableCellWithIdentifier(kDisplayCell_ID) as? UITableViewCell
        
        if cell == nil{
            cell = UITableViewCell(style: UITableViewCellStyle.Subtitle, reuseIdentifier: kDisplayCell_ID)
            cell!.selectionStyle = UITableViewCellSelectionStyle.Blue
            cell!.showsReorderControl = true
        }
        cell!.textLabel!.text = m_aryBookKey[indexPath.row] as? String
        cell!.textLabel?.textColor = UIColor.darkGrayColor()
        cell!.detailTextLabel!.text = m_aryContent[indexPath.row] as? String
        cell!.imageView?.image = UIImage(named: "\(m_aryImgs.objectAtIndex(indexPath.row))")
        cell!.accessoryType = UITableViewCellAccessoryType.DisclosureIndicator  //右邊會有箭頭
        
        return cell!
    }
    
    //刪除
    func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
        if editingStyle == UITableViewCellEditingStyle.Delete{
            var tempAry:NSMutableArray
            for var i = 0 ; i < m_aryPackage.count ; i++ {
                tempAry = m_aryPackage[i] as NSMutableArray
                tempAry.removeObjectAtIndex(indexPath.row)
            }
            var aryDelPath = [indexPath]
            tableView.deleteRowsAtIndexPaths(aryDelPath, withRowAnimation: UITableViewRowAnimation.Fade)
        }
    }
    
    //移動 （不做這個,Edit時不會產生移動選項）
    func tableView(tableView: UITableView, moveRowAtIndexPath sourceIndexPath: NSIndexPath, toIndexPath destinationIndexPath: NSIndexPath) {
        var tempAry:NSMutableArray
        for var i = 0 ; i < m_aryPackage.count ; i++ {
            tempAry = m_aryPackage[i] as NSMutableArray
            var tempObj:AnyObject = tempAry[sourceIndexPath.row]
            tempAry.removeObjectAtIndex(sourceIndexPath.row)
            tempAry.insertObject(tempObj, atIndex: destinationIndexPath.row)
        }
    }
    
    func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool {
        return true
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
    
    func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        if m_aryBook[indexPath.row].isEqual(NSNull()){
            let txtPath:String = NSBundle.mainBundle().pathForResource( m_aryBookKey[indexPath.row] as? String, ofType: "txt")!
            var book:Book = Book()
            book.refreshWithFrame(CGRectMake(0, 0, self.view.frame.size.width, self.view.frame.size.height), filePath: txtPath)
            m_aryBook.replaceObjectAtIndex(indexPath.row, withObject: book)
        }
        self.navigationController?.pushViewController(m_aryBook[indexPath.row] as UIViewController, animated: true)
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

