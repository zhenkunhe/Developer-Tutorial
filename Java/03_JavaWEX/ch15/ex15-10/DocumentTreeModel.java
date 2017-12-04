import javax.swing.event.TreeModelListener;
import javax.swing.text.Element;
import javax.swing.tree.*;
import  java.util.Vector ;
//�ޤJ�M��

//�H��@TreeModel�������覡, 
//�w�q�x�s��ܤ�󤺮e�𪬵��c����ƪ�TreeModel����
class DocumentTreeModel implements TreeModel {

	private Element root; //�w�q�x�s���ڸ`�I��Element����
	private Vector listeners;
	//�w�q�x�s��ťTreeModelEvent�ƥ󤧺�ť�����e��

	//�غc�l
	public DocumentTreeModel(Element element) {
		listeners = new Vector();
		//�ŧi�x�s��ťTreeModelEvent�ƥ󤧺�ť�����e��

		root = element; //�]�w��󪺮ڸ`�I
	}

	//�N�ǤJ�������]�w���ڸ`�I
	public void setRoot(Element element) {
		root = element;
	}

	//���o�𪬵��c���ڸ`�I
	public Object getRoot(){
		return root;
	}

	//���oobj�`�I��i���ޭȩҫ��w���l�`�I
    public Object getChild(Object obj, int i){
		return ((Element)obj).getElement(i);
	}

	//���oobj�`�I���l�`�I���Ӽ�
	public int getChildCount(Object obj){
		return ((Element)obj).getElementCount();
	}

	//�P�_�`�I�O�_�����`�I
	public boolean isLeaf(Object obj){
		return ((Element)obj).isLeaf();
	}

	//���oobj1�`�I�bobj�`�I�������ޭ�
	public int getIndexOfChild(Object obj, Object obj1){

		Element element = (Element)obj;
		//�Nobj���O�ഫ��Element����

		//�B��for�j��, ����`�I���l�`�I�����		
		for(int i = 0; i < element.getElementCount(); i++)
			if(obj1 == element.getElement(i)) //�P�_�ǤJ��obj1�`�I�O�_�P�l�`�I�ۦP
				return i; //�Ǧ^�l�`�I�����ޭ�

		return -1; //�䤣��������l�`�I�h�Ǧ^-1
    }

	public void valueForPathChanged(TreePath treepath, Object obj){}

	//���U��ťTreeModelEvent�ƥ󪺺�ť������
	public void addTreeModelListener(
		TreeModelListener treemodellistener){

		//�P�_�ǤJ����ť������O�_��null
		if(treemodellistener == null){
			return; //�פ�����k
		}
		else{
			listeners.add(treemodellistener); //�N��ť������[�J�e����
			return;
		}
	}

	//������ťTreeModelEvent�ƥ󪺺�ť������
	public void removeTreeModelListener(TreeModelListener treemodellistener){
		listeners.remove(treemodellistener);
	}
}