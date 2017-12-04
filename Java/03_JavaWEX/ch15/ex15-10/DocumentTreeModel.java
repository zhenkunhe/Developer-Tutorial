import javax.swing.event.TreeModelListener;
import javax.swing.text.Element;
import javax.swing.tree.*;
import  java.util.Vector ;
//引入套件

//以實作TreeModel介面的方式, 
//定義儲存顯示文件內容樹狀結構之資料的TreeModel物件
class DocumentTreeModel implements TreeModel {

	private Element root; //定義儲存文件根節點的Element物件
	private Vector listeners;
	//定義儲存監聽TreeModelEvent事件之監聽器的容器

	//建構子
	public DocumentTreeModel(Element element) {
		listeners = new Vector();
		//宣告儲存監聽TreeModelEvent事件之監聽器的容器

		root = element; //設定文件的根節點
	}

	//將傳入的元素設定為根節點
	public void setRoot(Element element) {
		root = element;
	}

	//取得樹狀結構的根節點
	public Object getRoot(){
		return root;
	}

	//取得obj節點內i索引值所指定的子節點
    public Object getChild(Object obj, int i){
		return ((Element)obj).getElement(i);
	}

	//取得obj節點內子節點的個數
	public int getChildCount(Object obj){
		return ((Element)obj).getElementCount();
	}

	//判斷節點是否為葉節點
	public boolean isLeaf(Object obj){
		return ((Element)obj).isLeaf();
	}

	//取得obj1節點在obj節點內的索引值
	public int getIndexOfChild(Object obj, Object obj1){

		Element element = (Element)obj;
		//將obj型別轉換為Element物件

		//運用for迴圈, 執行節點內子節點的比對		
		for(int i = 0; i < element.getElementCount(); i++)
			if(obj1 == element.getElement(i)) //判斷傳入的obj1節點是否與子節點相同
				return i; //傳回子節點的索引值

		return -1; //找不到對應的子節點則傳回-1
    }

	public void valueForPathChanged(TreePath treepath, Object obj){}

	//註冊監聽TreeModelEvent事件的監聽器物件
	public void addTreeModelListener(
		TreeModelListener treemodellistener){

		//判斷傳入的監聽器物件是否為null
		if(treemodellistener == null){
			return; //終止執行方法
		}
		else{
			listeners.add(treemodellistener); //將監聽器物件加入容器內
			return;
		}
	}

	//移除監聽TreeModelEvent事件的監聽器物件
	public void removeTreeModelListener(TreeModelListener treemodellistener){
		listeners.remove(treemodellistener);
	}
}