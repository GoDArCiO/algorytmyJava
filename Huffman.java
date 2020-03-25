package algorytmy;

/*
BufferedWriter writer = new BufferedWriter(new FileWriter("huf.txt"));
writer.write("1234567");
writer.close();
  File file = new File("huf.txt"); 
  BufferedReader br = new BufferedReader(new FileReader(file)); 
  String st;
  String data="";
  while ((st = br.readLine()) != null) 
    data=""+data+st+"\n"; 
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.PriorityQueue; 
import java.util.Scanner;



import java.util.Comparator;

	class HuffmanNode { 
	  int data; 
	  char c; 
	  HuffmanNode left; 
	  HuffmanNode right; 
	} 

	class MyComparator implements Comparator<HuffmanNode> { 
	  public int compare(HuffmanNode x, HuffmanNode y) 
	  { 
	    return x.data - y.data; 
	  } 
	} 

	public class RedBlackTree { 
		
		private static int getRandomNumberInRange(int min, int max) {

			if (min >= max) {
				throw new IllegalArgumentException("max must be greater than min");
			}

			Random r = new Random();
			return r.nextInt((max - min) + 1) + min;
		}
		
	  public static void printCode(HuffmanNode root, String s) 
	  {
		  char[] charArray = {'0','1','2','3','4','5','6','7'};
	    if (root.left == null && root.right == null && new String(charArray).indexOf(root.c)>=0) {

	      System.out.println(root.c + "   |  " + s);

	      return;
	    }
	    printCode(root.left, s + "0");
	    printCode(root.right, s + "1");
	  }

		private static void printHelper(HuffmanNode root, String indent, boolean last) {
		   	if (root != null) {
			   System.out.print(indent);
			   if (last) {
			      System.out.print("R----");
			      indent += "     ";
			   } else {
			      System.out.print("L----");
			      indent += "|    ";
			   }
	            
			   System.out.println(root.c);
			   printHelper(root.left, indent, false);
			   printHelper(root.right, indent, true);
			}
		}

	  public static void main(String[] args) throws IOException 
	  { 
		    int n = 8; 
		    char[] charArray = {'0','1','2','3','4','5','6','7'};
		    int[] charfreq = {0,0,0,0,0,0,0,0}; 
		    int[] input = new int[10000];
		    
		  for (int i = 0; i < 10000; i++) {
			  input[i]=getRandomNumberInRange(0, 7);
			  for (int k = 0; k < 8; k++) {
				  if(input[i]==k) {
					  charfreq[k]++;
				  }
			  }
			}

		  System.out.print("liczba");
		  for (int i = 0; i < 8; i++) {
			  System.out.print("  "+charArray[i]+"   ");
		  }
		  System.out.println("");
		  System.out.print("ilosc");
		  for (int i = 0; i < 8; i++) {
			  System.out.print(" "+charfreq[i]+" ");
		  }
		  System.out.println("");
		  System.out.println("");


	 
	    PriorityQueue<HuffmanNode> q 
	      = new PriorityQueue<HuffmanNode>(n, new MyComparator()); 

	    for (int i = 0; i < n; i++) {
	      HuffmanNode hn = new HuffmanNode();

	      hn.c = charArray[i];
	      hn.data = charfreq[i];

	      hn.left = null;
	      hn.right = null;

	      q.add(hn);
	    }
	    
	    HuffmanNode root = null;
	    
	    while (q.size() > 1) {

	      HuffmanNode x = q.peek();
	      q.poll();

	      HuffmanNode y = q.peek();
	      q.poll();

	      HuffmanNode f = new HuffmanNode();

	      f.data = x.data + y.data;
	      f.c = '-';
	      f.left = x;
	      f.right = y;
	      root = f;

	      q.add(f);
	    }	    
	    System.out.println("drzewo");
	    printHelper(root, "", true);
	    System.out.println("");
	    System.out.println("--------------------");
	    System.out.println(" Char | Huffman code ");
	    System.out.println("--------------------");
	    printCode(root, ""); 
	  } 
	} 
