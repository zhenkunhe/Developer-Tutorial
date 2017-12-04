
import UIKit

class ViewController: UIViewController ,UIPickerViewDataSource,UIPickerViewDelegate {
    var m_aryInfo:[UILabel] = [UILabel]()
    var dicPickerData:[String:[String]] = [String:[String]]()
    var picker:UIPickerView = UIPickerView(frame: CGRectZero)
    var m_checkBtn:UIButton = UIButton()
    
    func refreshWithFrame(frame:CGRect){
        self.view.frame = frame
        self.view.backgroundColor = UIColor.lightGrayColor()
        
        var tempLab:UILabel
        var labWidth:CGFloat = frame.size.width/3
        for (var i:CGFloat = 0 ; i < 3 ; i++) {
            tempLab = UILabel(frame: CGRectMake( labWidth * i , 30, labWidth, 40))
            tempLab.textColor = UIColor.blackColor()
            tempLab.font = UIFont.boldSystemFontOfSize(30)
            tempLab.textAlignment = NSTextAlignment.Center
            tempLab.adjustsFontSizeToFitWidth = true
            tempLab.text = "未定"
            m_aryInfo.append(tempLab)
            self.view.addSubview(tempLab)
        }
        
        dicPickerData["沙拉"] = ["田園沙拉","水果沙拉","凱薩沙拉"]
        dicPickerData["主菜"] = ["菲力牛排","肋眼牛排","北島冰魚","碳烤鴨胸","松阪豬排"]
        dicPickerData["甜點"] = ["鮮奶酪","芒果冰沙","巧克力冰淇凌","紫蘇梅醋"]
        
        picker.delegate = self
        picker.dataSource = self
        picker.showsSelectionIndicator = true
        picker.frame.origin = CGPointMake(0, frame.size.height - picker.frame.size.height)
        self.view.addSubview(picker)
        
        m_checkBtn.frame.size = CGSizeMake(100, 200)
        m_checkBtn.center = CGPoint(x: (self.view.frame.size.width / 2), y: (self.view.frame.size.height / 2))
        m_checkBtn.setTitle("確定", forState: UIControlState.Normal)
        m_checkBtn.setTitleColor(UIColor.blackColor(), forState: UIControlState.Normal)
        m_checkBtn.titleLabel?.font = UIFont.boldSystemFontOfSize(30)
        m_checkBtn.addTarget(self, action: "onButtonClick:", forControlEvents: UIControlEvents.TouchUpInside)
        self.view.addSubview(m_checkBtn)
    }
    
    func onButtonClick(senter:UIButton){
        var msg:String = "你所點的\n\([String](dicPickerData.keys)[0])為\(m_aryInfo[0].text!)\n\([String](dicPickerData.keys)[1])為\(m_aryInfo[1].text!)\n\([String](dicPickerData.keys)[2])為\(m_aryInfo[2].text!)"
        let alert:UIAlertView = UIAlertView(title: "完成", message: msg, delegate: nil, cancelButtonTitle: "確定")
        alert.show()
    }
    
    //MARK: -Delegate
    //-------------------------------
    
    func numberOfComponentsInPickerView(pickerView: UIPickerView) -> Int {
        return dicPickerData.count
    }
    
    func pickerView(pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        let pickerKey:String = [String](dicPickerData.keys)[component]
        return dicPickerData[pickerKey]!.count
    }
    
    func pickerView(pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String! {
        let pickerKey:String = [String](dicPickerData.keys)[component]
        return dicPickerData[pickerKey]![row]
    }
    
    func pickerView(pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        let pickerKey:String = [String](dicPickerData.keys)[component]
        var tempLable:UILabel = m_aryInfo[component]
        tempLable.text = dicPickerData[pickerKey]![row]
    }
//    func pickerView(pickerView: UIPickerView, widthForComponent component: Int) -> CGFloat {
//        <#code#>
//    }
//    func pickerView(pickerView: UIPickerView, rowHeightForComponent component: Int) -> CGFloat {
//        <#code#>
//    }
}

