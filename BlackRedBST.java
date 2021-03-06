package algorytmy;

class Node {
	int data; 
	Node parent; //wskaznik
	Node left; 
	Node right; 
	int color; //1.Red 0.Black
}

public class RedBlackTree {
	private Node root;
	private Node TNULL;

	private void fixDelete(Node x) {
		Node brat;
		while (x != root && x.color == 0) {
			if (x == x.parent.left) {//jest lewym synem
				brat = x.parent.right;//wiec to jest brat
				if (brat.color == 1) {
					//brat czerwony wiec rekolor z ojcem ------------------------------------------------przyp1
					brat.color = 0;
					x.parent.color = 1;
					leftRotate(x.parent);//rotujemy ojca w kierunku x
					brat = x.parent.right;//refresh brata
				}
				if (brat.left.color == 0 && brat.right.color == 0) {//brat wezla i jego synowie czarni ------------------przyp2
					brat.color = 1;//zmien kolor
					x = x.parent;//ustaw wskaznik na ojca i petla
				} else {
					if (brat.right.color == 0) {
						//syn brata skierowany w tym samym kierunku co x od ojca ( w tym przypadku w lewo)----------------------przyp3
						brat.left.color = 0;
						brat.color = 1;
						rightRotate(brat);//zmiana koloru, rotacja po bracie
						brat = x.parent.right;//refresh brata i odrazu instrukcje z syn brata skierowany w przeciwnym kierunku co x od ojca
					} //syn brata skierowany w przeciwnym kierunku co x od ojca ---------------------------------------------------------------przyp4
					brat.color = x.parent.color;//lokalny korzen ma zachowac swoj kolor
					x.parent.color = 0;
					brat.right.color = 0;
					leftRotate(x.parent);//rotuj ojca w kierunku syna ( w tym przypadku w lewo)
					x = root;//done (wyjscie z petli)
				}
			} else {//jest prawym synem -> lustro
				brat = x.parent.left;
				if (brat.color == 1) {
					brat.color = 0;
					x.parent.color = 1;
					rightRotate(x.parent);
					brat = x.parent.left;
				}
				if (brat.right.color == 0 && brat.right.color == 0) {
					brat.color = 1;
					x = x.parent;
				} else {
					if (brat.left.color == 0) {
						brat.right.color = 0;
						brat.color = 1;
						leftRotate(brat);
						brat = x.parent.left;
					} 
					brat.color = x.parent.color;
					x.parent.color = 0;
					brat.left.color = 0;
					rightRotate(x.parent);
					x = root;
				}
			} 
		}
		x.color = 0;//x jest czerwony ----------------------------------------------------przyp0
	}

	private void deleteNodeHelper(Node node, int key) {
		//znajdz
		Node z = TNULL;
		Node x, y;
		while (node != TNULL){
			if (node.data == key) {
				z = node;
			}

			if (node.data <= key) {
				node = node.right;
			} else {
				node = node.left;
			}
		}

		if (z == TNULL) {
			System.out.println("error");
			return;
		} 
		//koniec
		
		//zwykle usuwanko
		y = z;
		int yOriginalColor = y.color;
		if (z.left == TNULL) {//nie ma lewego syna to przesadz w siebie prawego
			x = z.right;
			if (z.parent == null) {
				root = z.right;
			} else if (z == z.parent.left){
				z.parent.left = z.right;
			} else {
				z.parent.right = z.right;
			}
			z.right.parent = z.parent;
		} else if (z.right == TNULL) {//nie ma prawego syna to przesadz w siebie lewego
			x = z.left;
			if (z.parent == null) {
				root = z.left;
			} else if (z == z.parent.left){
				z.parent.left = z.left;
			} else {
				z.parent.right = z.left;
			}
			z.left.parent = z.parent;
		} else {//ma obu synow
			y = minimum(z.right);//znajdz najblizszego sobie z prawej
			yOriginalColor = y.color;
			x = y.right;
			if (y.parent == z) {
				x.parent = y;
			} else {
				if (y.parent == null) {
					root = y.right;
				} else if (y == y.parent.left){
					y.parent.left = y.right;
				} else {
					y.parent.right = y.right;
				}
				y.right.parent = y.parent;
				y.right = z.right;
				y.right.parent = y;
			}
			if (z.parent == null) {
				root = y;
			} else if (z == z.parent.left){
				z.parent.left = y;
			} else {
				z.parent.right = y;
			}
			y.parent = z.parent;
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		}
		//zwykle usuwanko KONIEC
		
		if (yOriginalColor == 0){//jak byles czarny to idz sie naprawic
			fixDelete(x);
		}
	}
	
	//	##################################################	napraw insert
	private void fixInsert(Node k){
		Node u;
		while (k.parent.color == 1) {//ojciec jest czerwony
			
			//jezeli jest ojcem z prawej
			if (k.parent == k.parent.parent.right) {
				u = k.parent.parent.left; // brat ojca
				if (u.color == 1) {//jest czerwony
					//  "wujek czerwony" zamiana kolorami wujka i ojca z dziadkiem -------------przyp1
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;
				} else {//brat ojca jest czarny ----------------------------------------------------przyp2
					if (k == k.parent.left) {//i jest lewym dzieckiem
						// case 3.2.2 rotacja po rodzicu
						k = k.parent;
						rightRotate(k);
					}//brat ojca jest czarny i x oraz ojciec leza w tym samym kierunku (tutaj z prawej) ---------------------------------------przyp3
					//rotacja po dziadku i zmien kolory rodzica z dziadkiem (czyli po obrocie bratem) (czyli zachowaj kolor lokalnego korzenia)
					k.parent.color = 0;
					k.parent.parent.color = 1;
					leftRotate(k.parent.parent);
				}//done
			} else {//jezeli jest ojcem z lewej (lustrzane odbicie)
				u = k.parent.parent.right;
				if (u.color == 1) {
					u.color = 0;
					k.parent.color = 0;
					k.parent.parent.color = 1;
					k = k.parent.parent;	
				} else {
					if (k == k.parent.right) {
						k = k.parent;
						leftRotate(k);
					}
					k.parent.color = 0;
					k.parent.parent.color = 1;
					rightRotate(k.parent.parent);
				}
			}
			//jak dojdziemy do konca drzewa to spadamy z while'a
			if (k == root)break;
		}
		root.color = 0;//przyp 0
	}
	//	######################################################	napraw insert

	private void printHelper(Node root, String indent, boolean last) {
	   	if (root != TNULL) {
		   System.out.print(indent);
		   if (last) {
		      System.out.print("R----");
		      indent += "     ";
		   } else {
		      System.out.print("L----");
		      indent += "|    ";
		   }
            
           String sColor = root.color == 1?"RED":"BLACK";
		   System.out.println(root.data + "(" + sColor + ")");
		   printHelper(root.left, indent, false);
		   printHelper(root.right, indent, true);
		}
	}

	public RedBlackTree() {//prototyp
		TNULL = new Node();
		TNULL.color = 0;
		TNULL.left = null;
		TNULL.right = null;
		root = TNULL;
	}

	public Node minimum(Node node) {
		while (node.left != TNULL) {
			node = node.left;
		}
		return node;
	}
	
	public void scan() {//maksymalna i minimalna glebokosc lisci w drzewie czerwono-czarnym oraz liczbe czerwonych wezlow. 
		Node node = this.root;
		int redCount=red(node);
		int longestCount=maxDepth(node);
		int shortestCount=minDepth(node);
		System.out.println("redCount: "+redCount);
		System.out.println("longest: "+longestCount);
		System.out.println("shortest: "+shortestCount);
	}
	
	  int maxDepth(Node root)  
	  { 
	    // Root being null means tree doesn't exist.
	    if (root == null) 
	      return 0; 

	    // Get the depth of the left and right subtree 
	    // using recursion.
	    int leftDepth = maxDepth(root.left); 
	    int rightDepth = maxDepth(root.right); 

	    // Choose the larger one and add the root to it.
	    if (leftDepth > rightDepth) 
	      return (leftDepth + 1); 
	    else 
	      return (rightDepth + 1); 
	  }
	
	  int minDepth(Node root)  
	  { 
	    // Root being null means tree doesn't exist.
	    if (root == null) 
	      return 0; 

	    // Get the depth of the left and right subtree 
	    // using recursion.
	    int leftDepth = minDepth(root.left); 
	    int rightDepth = minDepth(root.right); 

	    // Choose the larger one and add the root to it.
	    if (leftDepth < rightDepth) 
	      return (leftDepth + 1); 
	    else 
	      return (rightDepth + 1); 
	  }
	  
	  int red(Node root)  
	  { 
		  int red=0;
	    // Root being null means tree doesn't exist.
	    if (root == null) 
	      return 0; 

	    red += red(root.left); 
	    red += red(root.right); 

	    if(root.color==1){
	        red++;
	    }
	    return red;
	  }
	  
	//  ######################################################### rotacja lewa
	public void leftRotate(Node x) {
		Node y = x.right;
		x.right = y.left;
		if (y.left != TNULL) {
			y.left.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.left) {
			x.parent.left = y;
		} else {
			x.parent.right = y;
		}
		y.left = x;
		x.parent = y;
	}
	//  ######################################################### rotacja lewa
	
	//  ######################################################### rotacja prawa
	public void rightRotate(Node x) {
		Node y = x.left;
		x.left = y.right;
		if (y.right != TNULL) {
			y.right.parent = x;
		}
		y.parent = x.parent;
		if (x.parent == null) {
			this.root = y;
		} else if (x == x.parent.right) {
			x.parent.right = y;
		} else {
			x.parent.left = y;
		}
		y.right = x;
		x.parent = y;
	}
	//  ######################################################### rotacja prawa

	//  ######################################################### insert
	public void insert(int key) {
		//Wstawianko jak w drzewku normalsowym
		Node node = new Node();
		node.parent = null;
		node.data = key;
		node.left = TNULL;
		node.right = TNULL;
		node.color = 1; // nowy=czerwony

		Node y = null;
		Node x = this.root;

//przesuwaj sie do swojego miejsca az nie spotkasz nula
		// x synem y
		while (x != TNULL) {
			y = x;
			if (node.data < x.data) {
				x = x.left;
			} else {
				x = x.right;
			}
		}

		node.parent = y;
		if (y == null) {
			root = node;//nie ma zadnego ojca to jestes korzeniem
		} else if (node.data < y.data) {
			y.left = node;
		} else {
			y.right = node;
		}

		// korzen
		if (node.parent == null){
			node.color = 0;//korzen=czarny
			return;
		}

		// jeden od korzenia
		if (node.parent.parent == null) {
			return;
		}

		fixInsert(node);
	}
	//  ######################################################### insert

	public void deleteNode(int data) {
		deleteNodeHelper(this.root, data);
	}

	public void prettyPrint() {
        printHelper(this.root, "", true);
	}
	
	public static void main(String [] args){
    	RedBlackTree bst = new RedBlackTree();
    	System.out.println("all 8.1");
    	System.out.println("");
    	//zadanie all8.1 38, 31, 22, 8, 20, 5, 10, 9, 21, 27, 29, 25, 28
        bst.insert(38);
    	bst.insert(31);
    	bst.insert(22);
    	bst.insert(8);
    	bst.insert(20);
    	bst.insert(5);
    	bst.insert(10);
    	bst.insert(9);
    	bst.insert(21);
    	bst.insert(27);
    	bst.insert(29);
    	bst.insert(25);
    	bst.prettyPrint();
    	System.out.println("all 8.2");
    	System.out.println("");
    	//zadanie all8.2 5, 38, 8, 10, 22, 20, 29, 31
    	bst.deleteNode(5);
    	bst.deleteNode(38);
    	bst.deleteNode(8);
    	bst.deleteNode(10);
    	bst.deleteNode(22);
    	bst.deleteNode(20);
    	bst.deleteNode(29);
    	bst.deleteNode(31);
    	bst.prettyPrint();
    	System.out.println("");
    	bst.scan();
    	System.out.println("");
    	System.out.println("big data");
    	RedBlackTree bst2 = new RedBlackTree();
    	for (int i = 1; i < 1000; i++) {
    		bst2.insert(i);
    	}
    	bst2.scan();
	}
}
