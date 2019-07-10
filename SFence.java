package sFence;

import java.util.Scanner;

/**
 * 201713074
 * @author �ӿ���
 * ����̿� ��Ÿ��
 */

public class SFence {
	
	static BST bst=new BST(0,-1,0,0);

	public static void main(String[] args) {
		// �Է�
		Scanner scan=new Scanner(System.in);
		System.out.println("ù°�ٿ��� ����� �������� ���� N��,"+'\n'+"���� N�ٿ��� �Ÿ� �ʺ� ���̸� ���ʷ� �Է��ϼ���");
		int n=Integer.parseInt(scan.nextLine());
		
		for(int i=0;i<n;i++) { insertBST(scan.nextLine(),i); }
		
		boolean sfence[]=new boolean[n];
		for(int i=0;i<sfence.length;i++) { sfence[i]=false; }
	
		bst.inorder(bst.getRoot(),sfence);	
		
		int nodenum=0;
		
		for(int i=0;i<sfence.length;i++) {
			if(sfence[i]==true) { nodenum++; }
		}
		
		System.out.println("======���======");
		System.out.println(nodenum);
		
		for(int i=0;i<sfence.length;i++) {
			if(sfence[i]==true) { System.out.print((i+1)+" "); }
		}	
	}
	
	
	public static void insertBST(String s,int m) {
		String a[]=s.split(" ");
		int numarr[]=new int[a.length];
		
		for(int i=0;i<a.length;i++) {
			numarr[i]=Integer.parseInt(a[i]);
		}
		
		bst.put(bst.root,numarr[0], m, numarr[2], numarr[0]+numarr[1]);	
	}
}




class BST<Key extends Comparable<Key>,Value> { //Ʈ��
	public Node root;
	public Node getRoot() { return root; }
	public BST(int newDist, int newName, int newHeight, int newWidth) {
		root=new Node(newDist, newName, newHeight, newWidth);
	}
	
	
	//���Կ���<<***** | �Ÿ� �̸� ���� �ʺ�+�Ÿ�
	public void put(int d, int v, int h, int w) { root=put(root,d,v,h,w); }
	public Node put(Node n, int d, int v, int h, int w) {
		
		if(w-d==0 || w-d<0) {
			return n;
		}
		
		if(n==null) return new Node (d,v,h,w);
		
		int dd=n.getDist()-d;
		int hh=n.getHeight()-h;
		int ww=n.getWidth()-w;
		
		int wd=n.getWidth()-d;
		int dw=n.getDist()-w;
		
		if(dw>=0 || wd<=0) { //��Ÿ���� �� ��ĥ ��
			//������ �˰��� ��� ����
			if(dd>0) {
				n.setLeft(put(n.getLeft(),d,v,h,w));
			}
			else if (dd<=0)	{
				n.setRight(put(n.getRight(),d,v,h,w));			
			}			
		}
		else if(hh>0) { //�� ��Ÿ�� ���̰� �� ����
			if(dd<=0 && ww>=0) { //�� ��� ����==�� ��� ���� ���
				return n;
			}
			else if(dd>0 && ww>=0) { //�� ��� w ����
				put(n,d,v,h,n.getDist());
			}
			else if(dd<=0 && ww<0) { //�� ��� d ����
				put(n,n.getWidth(),v,h,w);
			}
			else { //�� ��� �и� ����
				//�� ��Ÿ���� ��� �κ��� �����ϰ� �� ���� ���� ������ �����. �̸��� ����.
				put(n,d,v,h,n.getDist());
				put(this.root,n.getWidth(),v,h,w);
			}
			
		}
		else if(hh<0) { //�� ��Ÿ�� ���̰� �� ����
			if(dd>=0 && ww<=0) { //�� ��� ����
				n.setWidth(n.getDist()); //�� ��带 ���ɳ���
				put(n,d,v,h,n.getDist()); //�� ��� �и�����
				put(this.root,n.getWidth(),v,h,w);
			}
			else if(dd>=0 && ww>0) { //�� ��� d ����
				n.setDist(w);
				put(n,d,v,h,w);
			}
			else if(dd<0 && ww<=0) { //�� ��� w ����
				n.setWidth(d);
				put(n,d,v,h,w);
			}
			else { //�� ��� �и� ����
				//�� ��Ÿ���� ��� �κ��� �����ϰ� �� ���� ���� ������ ����� �� �� ��Ÿ�� �����. �̸��� ����.
				int ow=n.getWidth();
				n.setWidth(d);
				put(this.root,w,n.getName(),n.getHeight(),ow);//���Ÿ�������
				put(n,d,v,h,w); //�� ��Ÿ�� �����
			}
		}
		else { //hh==0. ��Ÿ�� ���̰� ���� ��
			if(dd==0) {
				if(ww>=0) { //��������
					return n;
				}
				else { //�������
					n.setWidth(n.getDist()); //�� ��带 ���ɳ���
					put(n,d,v,h,n.getDist()); //�� ��� �и�����
					put(this.root,n.getWidth(),v,h,w);
				}
			}
			else if(dd>0) {
				if(ww<=0) { //�������
					n.setWidth(n.getDist()); //�� ��带 ���ɳ���
					put(n,d,v,h,n.getDist()); //�� ��� �и�����
					put(this.root,n.getWidth(),v,h,w);
				}
				else { //�����w����d����
					int od=n.getDist();
					int nw=w;
					n.setDist(w);
					put(n,d,v,h,n.getDist());
					put(root,od,-1,h,nw);
				}
			}
			else {
				if(ww>=0) { //��������
					return n;
				}
				else { //�����d����w����
					int ow=n.getWidth();
					int nd=d;
					n.setWidth(d);
					put(n,n.getWidth(),v,h,w);
					put(root,nd,-1,h,ow);
				}
			}
		}
		
		return n;
	}
	
	
	
	public void inorder(Node n, boolean[] sfence) {
		if(n!=null) {
			inorder(n.getLeft(), sfence);
			if((n.getDist()!=n.getWidth()) && (n.getName()>=0)) {
				sfence[n.getName()]=true;
			}
			inorder(n.getRight(), sfence);
		}
	}
	
	public boolean notRoot(Node n) {
		if(n.equals(root))	return false;
		else return true;
	}
}



class Node <Key extends Comparable<Key>,Value> {
	//���
	
	private int dist, height, width; //�Ÿ�,����,����. ���� : dist
	private int name; //���� �̸�
	private Node left, right;
	
	public Node (int newDist, int newName, int newHeight, int newWidth) {
		dist=newDist;
		name=newName;
		height=newHeight;
		width=newWidth;
		left=right=null;
	}
	
	public int getDist() { return dist; }
	public int getHeight() { return height; }
	public int getWidth() { return width; }
	public int getName() { return name; }
	public Node getLeft() { return left; }
	public Node getRight() { return right; }
	
	public void setDist(int newDist) { dist=newDist; }
	public void setHeight(int newHeight) { height=newHeight; }
	public void setWidth(int newWidth) { width=newWidth; }
	public void setname(int newName) { name=newName; }
	public void setLeft(Node newLeft) { left=newLeft; }
	public void setRight(Node newRight) { right=newRight; }
	
}