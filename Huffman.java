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
	  String c; 
	  HuffmanNode left; 
	  HuffmanNode right; 
	} 

	class MyComparator implements Comparator<HuffmanNode> { 
	  public int compare(HuffmanNode x, HuffmanNode y) 
	  { 
	    return x.data - y.data; 
	  } 
	} 

	public class Huffman { 
		static int suma=0;
		
		private static int getRandomNumberInRange(int min, int max) {

			if (min >= max) {
				throw new IllegalArgumentException("max must be greater than min");
			}

			Random r = new Random();
			return r.nextInt((max - min) + 1) + min;
		}
		
	  public static void printCode(HuffmanNode root, String s) 
	  {
	    if (root.left == null && root.right == null && Integer.parseInt(root.c)>=0) {

	      System.out.println(root.c + "   |  " + s);

	      return;
	    }
	    printCode(root.left, s + "0");
	    printCode(root.right, s + "1");
	  }
	  
	  public static void calc(HuffmanNode root, String s) 
	  {
	    if (root.left == null && root.right == null && Integer.parseInt(root.c)>=0) {
suma=suma+s.length();

	      return;
	    }
	    calc(root.left, s + "0");
	    calc(root.right, s + "1");
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
		  
		  //inicjalizacja
int help=0;
		    String[] charArray1 = {"0","1","2","3","4","5","6","7"};
		    int[] charfreq1 = {0,0,0,0,0,0,0,0}; 
		    String[] charArray2 = new String[64];
		    for(int i = 0; i < 78; i++) {
		    	if(i==0) {
		    		charArray2[help]="00";help++;continue;}
		    	if(i<8) {
		    		charArray2[help]="0"+Integer.toString(i);help++;continue;} 
		    	if(i%10<8) {
		    		charArray2[help]=Integer.toString(i);help++;continue;}
		    }
		    int[] charfreq2 = new int[64];
		    for(int i = 0; i < 64; i++) {
		    	charfreq2[i]=0;
		    }
		    //koniec inicjalizacji


			  
		    
		    //z pliku
		    BufferedReader br = new BufferedReader(new FileReader("input.txt"));
		    //np
		    //6734127432173421634217341273412763421364127342176342147432176341231231231231231231234523131235435134
	        String st;
	        StringBuilder sb = new StringBuilder();
				while ((st = br.readLine()) != null)  
				    sb.append(st);                 
				String data = sb.toString();
			
//kalkulacja freq po1
			  for (int i = 0; i < data.length(); i++) {
				  for (int k = 0; k < 8; k++) {
					  if(charArray1[k].equals(Character.toString(data.charAt(i)))) {
						  charfreq1[k]++;
					  }
				  }
				}
			  
			  //po2
			  for (int i = 0; i < data.length(); i=i+2) {
				  for (int k = 0; k < 64; k++) {
					  if(charArray2[k].equals(Character.toString(data.charAt(i))+Character.toString(data.charAt(i+1)))) {
						  charfreq2[k]++;
					  }
				  }
				}

			  //sprawdzenie
			  System.out.print("liczba");
			  for (int i = 0; i < 8; i++) {
				  System.out.print(" "+charArray1[i]+" ");
			  }
			  System.out.println("");
			  System.out.print("ilosc");
			  for (int i = 0; i < 8; i++) {
				  System.out.print(" "+charfreq1[i]+" ");
			  }
			  System.out.println("");
			  System.out.println("");
			  
			  System.out.print("liczba");
			  for (int i = 0; i < 64; i++) {
				  System.out.print("  "+charArray2[i]+"   ");
			  }
			  System.out.println("");
			  System.out.print("ilosc");
			  for (int i = 0; i < 64; i++) {
				  System.out.print("   "+charfreq2[i]+"   ");
			  }
			  System.out.println("");
			  System.out.println("");
			  //koniec sprawdzenia
			  
	        //po jednym
		    PriorityQueue<HuffmanNode> q1 
		      = new PriorityQueue<HuffmanNode>(8, new MyComparator()); 
	        //po dwa
		    PriorityQueue<HuffmanNode> q2 
		      = new PriorityQueue<HuffmanNode>(64, new MyComparator()); 
		    
		    
		    for (int i = 0; i < 8; i++) {
			      HuffmanNode hn1 = new HuffmanNode();

			      hn1.c = charArray1[i];
			      hn1.data = charfreq1[i];

			      hn1.left = null;
			      hn1.right = null;

			      q1.add(hn1);
			    }
			    
			    HuffmanNode root1 = null;
			    
			    while (q1.size() > 1) {

			      HuffmanNode x = q1.peek();
			      q1.poll();

			      HuffmanNode y = q1.peek();
			      q1.poll();

			      HuffmanNode f = new HuffmanNode();

			      f.data = x.data + y.data;
			      f.c = "-";
			      f.left = x;
			      f.right = y;
			      root1 = f;

			      q1.add(f);
			    }	    
			    System.out.println("drzewo");
			    printHelper(root1, "", true);
			    System.out.println("");
			    System.out.println("--------------------");
			    System.out.println(" Char | Huffman code ");
			    System.out.println("--------------------");
			    printCode(root1, ""); 
			    
			    for (int i = 0; i < 64; i++) {
				      HuffmanNode hn2 = new HuffmanNode();

				      hn2.c = charArray2[i];
				      hn2.data = charfreq2[i];

				      hn2.left = null;
				      hn2.right = null;

				      q2.add(hn2);
				    }
				    
				    HuffmanNode root2 = null;
				    
				    while (q2.size() > 1) {

				      HuffmanNode x = q2.peek();
				      q2.poll();

				      HuffmanNode y = q2.peek();
				      q2.poll();

				      HuffmanNode f = new HuffmanNode();

				      f.data = x.data + y.data;
				      f.c = "-";
				      f.left = x;
				      f.right = y;
				      root2 = f;

				      q2.add(f);
				    }	    
				    System.out.println("drzewo");
				    printHelper(root2, "", true);
				    System.out.println("");
				    System.out.println("--------------------");
				    System.out.println(" Char | Huffman code ");
				    System.out.println("--------------------");
				    printCode(root2, ""); 
		    
	        
	        //losowe dla jednego znaku tak dla sprawdzenia
		    int n = 8; 
		    String[] charArray = {"0","1","2","3","4","5","6","7"};
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
		  //koniec losowe

	 
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
	      f.c = "-";
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
	    
	    
	    System.out.println("--------------------");
	    System.out.println("--------------------");
	    System.out.println("podsumowanie");
	    System.out.println("--------------------");
	    System.out.println("--------------------");
	    calc(root1, "");
	    System.out.println("dla 1 bez - 8*3="+8*3+" z "+suma);
	    calc(root2, "");
	    System.out.println("dla 2 bez - 64*6="+64*6+" z "+suma);
	  } 
	} 
