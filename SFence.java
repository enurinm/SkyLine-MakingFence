package sFence;

import java.util.Scanner;

/**
 * 201713074
 * @author 임예린
 * 상근이와 울타리
 */

public class SFence {
	
	static BST bst=new BST(0,-1,0,0);

	public static void main(String[] args) {
		// 입력
		Scanner scan=new Scanner(System.in);
		System.out.println("첫째줄에는 사용한 나무판의 개수 N을,"+'\n'+"다음 N줄에는 거리 너비 높이를 차례로 입력하세요");
		int n=Integer.parseInt(scan.nextLine());
		
		for(int i=0;i<n;i++) { insertBST(scan.nextLine(),i); }
		
		boolean sfence[]=new boolean[n];
		for(int i=0;i<sfence.length;i++) { sfence[i]=false; }
	
		bst.inorder(bst.getRoot(),sfence);	
		
		int nodenum=0;
		
		for(int i=0;i<sfence.length;i++) {
			if(sfence[i]==true) { nodenum++; }
		}
		
		System.out.println("======출력======");
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




class BST<Key extends Comparable<Key>,Value> { //트리
	public Node root;
	public Node getRoot() { return root; }
	public BST(int newDist, int newName, int newHeight, int newWidth) {
		root=new Node(newDist, newName, newHeight, newWidth);
	}
	
	
	//삽입연산<<***** | 거리 이름 높이 너비+거리
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
		
		if(dw>=0 || wd<=0) { //울타리가 안 겹칠 때
			//원래의 알고리즘 대로 삽입
			if(dd>0) {
				n.setLeft(put(n.getLeft(),d,v,h,w));
			}
			else if (dd<=0)	{
				n.setRight(put(n.getRight(),d,v,h,w));			
			}			
		}
		else if(hh>0) { //헌 울타리 높이가 더 높음
			if(dd<=0 && ww>=0) { //새 노드 삭제==새 노드 삽입 취소
				return n;
			}
			else if(dd>0 && ww>=0) { //새 노드 w 갱신
				put(n,d,v,h,n.getDist());
			}
			else if(dd<=0 && ww<0) { //새 노드 d 갱신
				put(n,n.getWidth(),v,h,w);
			}
			else { //새 노드 분리 갱신
				//새 울타리의 가운데 부분을 삭제하고 두 개의 노드로 나누어 재삽입. 이름은 같다.
				put(n,d,v,h,n.getDist());
				put(this.root,n.getWidth(),v,h,w);
			}
			
		}
		else if(hh<0) { //새 울타리 높이가 더 높음
			if(dd>=0 && ww<=0) { //헌 노드 삭제
				n.setWidth(n.getDist()); //헌 노드를 유령노드로
				put(n,d,v,h,n.getDist()); //새 노드 분리삽입
				put(this.root,n.getWidth(),v,h,w);
			}
			else if(dd>=0 && ww>0) { //헌 노드 d 갱신
				n.setDist(w);
				put(n,d,v,h,w);
			}
			else if(dd<0 && ww<=0) { //헌 노드 w 갱신
				n.setWidth(d);
				put(n,d,v,h,w);
			}
			else { //헌 노드 분리 갱신
				//헌 울타리의 가운데 부분을 삭제하고 두 개의 노드로 나누어 재삽입 후 새 울타리 재삽입. 이름은 같다.
				int ow=n.getWidth();
				n.setWidth(d);
				put(this.root,w,n.getName(),n.getHeight(),ow);//헌울타리재삽입
				put(n,d,v,h,w); //새 울타리 재삽입
			}
		}
		else { //hh==0. 울타리 높이가 같을 때
			if(dd==0) {
				if(ww>=0) { //새노드삭제
					return n;
				}
				else { //헌노드삭제
					n.setWidth(n.getDist()); //헌 노드를 유령노드로
					put(n,d,v,h,n.getDist()); //새 노드 분리삽입
					put(this.root,n.getWidth(),v,h,w);
				}
			}
			else if(dd>0) {
				if(ww<=0) { //헌노드삭제
					n.setWidth(n.getDist()); //헌 노드를 유령노드로
					put(n,d,v,h,n.getDist()); //새 노드 분리삽입
					put(this.root,n.getWidth(),v,h,w);
				}
				else { //새노드w헌노드d갱신
					int od=n.getDist();
					int nw=w;
					n.setDist(w);
					put(n,d,v,h,n.getDist());
					put(root,od,-1,h,nw);
				}
			}
			else {
				if(ww>=0) { //새노드삭제
					return n;
				}
				else { //새노드d헌노드w갱신
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
	//노드
	
	private int dist, height, width; //거리,높이,넓이. 기준 : dist
	private int name; //판자 이름
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