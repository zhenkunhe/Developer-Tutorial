import javax.swing.*;  //�ޥ�Swing�M��
import java.awt.*;
import java.awt.event.*;

//�~��Canvas���O�w�q��ܹϤ����e��
class ImageCanvas extends Canvas {

	private Dimension d; //�e���j�p
	private Image offImage, image; //�Ϥ�
	private Graphics offGraphics; //�e��
	private boolean loading, //�P�_�O�_�i���J
								firstTime; //�P�_�O�_���Ĥ@��
	private int x, y; //�Ϥ����W���y��
	private int lastX, lastY; //�Ϥ����W�����®y��
	private int w, h; //�x�s�Ϥ��e��
 	private Color background = Color.gray; //�x�s�Ϥ���ܪ��I���C��
	private int lastmag, percent; //��ܤ�v
	private int maxZoom; //�̤j��j��v

	//�غc�l
	public ImageCanvas(int w, int h, int maxZoom){

		loading = true;
		firstTime = true;
		lastmag = 100;
		percent = 100;
		this.maxZoom = 1600;
		this.background = background;

		//�P�_�̤j����j��v�O�_�j�󵥩�1,
		//�O�h�N��j��v���H100
		if(maxZoom >= 1)
			this.maxZoom = maxZoom * 100;
		
		//�Y��j��v�p��1, �h�����]�w��j��v��100
		if(maxZoom < 1)
			this.maxZoom = 100;

		resize(w, h); //���s�վ�j�p
	}

	//����ø�Ϫ��_�l�]�w
	public void setup(){

		d = size(); //���o�e���j�p

		setBackground(Color.white); //�]�w�I����

		if(offImage == null){
			offImage = createImage(d.width, d.height);
			//�إ߹Ϥ�

			offGraphics = offImage.getGraphics();
			//�إ�ø�Ϥ��e����
		}

		offGraphics.setColor(Color.white);
		//�]�w�e���ثe���ϥ��C��

		offGraphics.fillRect(0, 0, d.width, d.height);
		//�b���w�d����C��

		offGraphics.setColor(Color.black);
		//�]�w�e���ثe���ϥ��C��
	}

	public void paint(Graphics g){ //ø�s�e��

		d = getSize();

		if(!loading){ //�P�_�O�_�w�g���J

			offGraphics.setColor(background);
			//�]�w�I���C��

			offGraphics.fillRect(0, 0, d.width, d.height);
			//�񺡯x��

			if(x > d.width) x = d.width;
			//�P�_x�y�ЬO�_�j��e�����e��, 
			//�O�h�]�wx�y�Ь��e��

			if(x < 0 - w) x = 0 - w;
			//�p��Ϥ��k�W����x�y��

			if(y > d.height) y = d.height;
			//�P�_y�y�ЬO�_�j��e��������, 
			//�O�h�]�wy�y�Ь��e������

			if(y < 0 - h) y = 0 - h;
			//�p��Ϥ����W����y�y��

			offGraphics.drawImage(image, x, y, w, h, this);
			//ø�s�Ϥ�
		}

		g.drawImage(offImage, 0, 0, this); //ø�s�Ϥ�
	}

	//���J���|���w���Ϥ�
	public void loadImage(String theImage){

		d = getSize(); //���o�e�����j�p

		String strTop = "Loading Image..."; //��ܸ��J�Ϥ����T��
		Font f = new Font("Arial", 0, 14); //�ŧi���ϥΪ��r��

		FontMetrics fm = offGraphics.getFontMetrics(f);
		//���oø�ϨϥΪ��r��W�檫��

		offGraphics.setFont(f); //�]�wø�ϨϥΪ��r��
		int fontwidth = fm.stringWidth(strTop); //�]�w�r�骺�e��
		int textwidth = (d.width - fontwidth) / 2; //�]�w�r�ꪺ�e��
		offGraphics.drawString(strTop, textwidth, 50); //ø�s�r��
		f = new Font("Arial", 0, 12); //�ŧi���ϥΪ��r��
		fm = offGraphics.getFontMetrics(f); //���o�r��W��

		offGraphics.setFont(f); //�]�wø�ϨϥΪ��r��
		repaint(10L); //��ø�e��, �̦h�ϥ�0.01��

		image = getToolkit().getImage(theImage);
		//���o���|�ҫ��V���ɪ����|

		MediaTracker tracker = new MediaTracker(this);
		//�ŧi�l�ܪ��󪬺A������

		tracker.addImage(image, 0);
		//�Nimage����[�J�l�ܲM��, �ó]�wID

		try{
			tracker.waitForID(0); //�]�w���ݹϤ���ID
		}
		catch(InterruptedException interruptedexception) { }

		loading = false; //�]�w�ثe�ëD���J���A
	}

	//��s�e��
	public void update(Graphics g){
		paint(g);
	}

	//�Y�p�Ϥ�
	public void zoomOut() {

		//�̾��Y�p��v���j�p�p���Y�p��v
		if(percent <= maxZoom && percent > 100){
			percent = (percent / 25) * 25;
			percent -= 25;
		}
		else if(percent <= 100 && percent > 10){
			percent = (percent / 5) * 5;
			percent -= 5;
		}

		if(percent < 10) percent = 10;
		//�Y��ܤ�v�p��10, �h�]�w��v��10

		w = (percent * image.getWidth(this)) / 100;
		//�p��Ϥ����e��

		h = (percent * image.getHeight(this)) / 100;
		//�p��Ϥ�������

		magnify(); //�����Y��
		repaint(); //��ø�e��
	}

	//��j�Ϥ�
	public void zoomIn(){

		//�̾ک�j��v���j�p�p���j��v
		if(percent < maxZoom && percent >= 100){
			percent = (percent / 25) * 25;
			percent += 25;
		}
		else if(percent < 100 && percent >= 10){
			percent = (percent / 5) * 5;
			percent += 5;
		}

		if(percent > maxZoom) percent = maxZoom;
		//�P�_��j��v�O�_�j��̤j��j��v

		w = (percent * image.getWidth(this)) / 100;
		h = (percent * image.getHeight(this)) / 100;
		//�p��Ϥ����e�׻P����

		magnify(); //�����Y��
		repaint(); //��ø�e��
	}

	//�]�w��ܹϤ������׻P�������׬ۦP
	public void fitHeight(){

		d = getSize(); //���o�e���j�p
		int xsize = image.getWidth(this),
			ysize = image.getHeight(this);
		//���o�Ϥ����e�B��

		float hratio = (float)d.height / (float)ysize;
		//�p�⫫���Y���v

		w = (int)(hratio * (float)xsize);
		h = (int)(hratio * (float)ysize);
		//�H�Y���v�p��Ϥ����e�B��

		percent = lastmag = (int)(hratio * 100F);
		//�p���Y���v

		x = (d.width - w) / 2;
		y = (d.height - h) / 2;
		//�p��Ϥ����W������m

		repaint(); //��ø�Ϥ�
	}

	public void imagecenter(){ //�m����ܹϤ�

		x = (d.width - w) / 2;
		y = (d.height - h) / 2;
		//�p��Ϥ����W������m

		repaint(); //��ø�e��
	}

	public void fitWindow(){ //�]�w�i�b������������ܹϤ�

		d = getSize(); //���o�e�����ؤo

		int xsize = image.getWidth(this);
		int ysize = image.getHeight(this);
		//���o�Ϥ����e�B��

		float wratio = (float)d.width / (float)xsize;
		float hratio = (float)d.height / (float)ysize;
		//�p��e�P������j��v

		//�P�_�e�P�����Y���v���j�p
		if(wratio <= hratio){ //�����Y���v�j��e��
			w = (int)(wratio * (float)xsize);
			h = (int)(wratio * (float)ysize);
			//�H�Y���v�p��Ϥ����e�׻P����

			percent = lastmag = (int)(wratio * 100F);
			//�p���Y���v
		}
		if(wratio > hratio){ //�e�ש�j��v�j�󰪫�
			w = (int)(hratio * (float)xsize);
			h = (int)(hratio * (float)ysize);
			//�H�Y���v�p��Ϥ����e�׻P����

			percent = lastmag = (int)(hratio * 100F);
			//�p���Y���v
		}

		x = (d.width - w) / 2;
		y = (d.height - h) / 2;
		//�p��Ϥ����W������m

		repaint(); //��ø����
	}

	public void normal(){ //�]�w�Ϥ��H���`�j�p���

		d = getSize(); //���o�e���j�p

		percent = 100;
		lastmag = 100;

		w = (percent * image.getWidth(this)) / 100;
		h = (percent * image.getHeight(this)) / 100;
		//�̷��Y���v�p��Ϥ��e�B��

		x = (d.width - image.getWidth(this)) / 2;
		y = (d.height - image.getHeight(this)) / 2;
		//�p��Ϥ������W���y��

		repaint(); //��ø
	}

	public void fitWidth(){ //�]�w�����H�Ϥ��e�����

		d = getSize(); //���o�e���j�p

		int xsize = image.getWidth(this);
		int ysize = image.getHeight(this);
		//���o�Ϥ����e�B��

		float wratio = (float)d.width / (float)xsize;
		//�p��e�ת��Y���v

		w = (int)(wratio * (float)xsize);
		h = (int)(wratio * (float)ysize);
		//�H�Y���v�p��e�B��

		percent = lastmag = (int)(wratio * 100F);
		//�]�w�Y���v

		x = (d.width - w) / 2;
		y = (d.height - h) / 2;
		//�p��Ϥ����W�����y��

		repaint(); //��ø�e��
    }

    public void magnify(){ //����Ϥ����Y��

		float magnify = percent; //�]�w�Y���v
		magnify /= lastmag;
		magnify--;
		x = (int)((float)x + magnify * (float)(x - d.width / 2));
		y = (int)((float)y + magnify * (float)(y - d.height / 2));
		//�p��Ϥ����W���y��

		lastmag = percent; //�]�w�Ϥ����Y���v
    }

	//����ƹ�����
   public boolean mouseUp(Event E, int x, int y){	
		firstTime = true; //�]�w��true
		return true;
	}

	//�ƹ��즲�Ϥ�
	public boolean mouseDrag(Event e, int x, int y){
		move(x, y); //���ʹϤ���m
		return true;
	}

	//���ʹϤ�
	public void move(int xLocation, int yLocation) {

		if(firstTime) { //�P�_�O�_���Ĥ@��
			firstTime = false; //�]�w���O�Ĥ@��
			lastX = xLocation; //�]�w�Ϥ����W�@����m
			lastY = yLocation;
			return;
		}
		else {
			x += xLocation - lastX; //�]�w�Ϥ��s�����W���y��
			y += yLocation - lastY;

			lastX = xLocation; //�W���Ϥ����W������m
			lastY = yLocation;

			repaint(); //��ø�Ϥ�
			return;
		}
	}
}