package algorytmy;

import java.util.*;

class LongestCommonSubString
{
	//rekurencyjnie tworzy liste stringow chodzac po tabelce
	public static List<String> fReadsPutsStringsFromTable(String X, String Y, int m, int n,
								int[][] T)
	{
		//zaczynamy od dolu wiec to jest warunek na koniec
		if (m == 0 || n == 0)
		{
			return new ArrayList<>(Collections.nCopies(1, ""));//zwaracamy arrayliste z ""
		}
		// -----------------------------------------------------------------------------------jak ostatnia (aktualna) literka sie zgadza 
		if (X.charAt(m - 1) == Y.charAt(n - 1))
		{
			//odpalamy funkcje znowu po skosie idac
			List<String> lcs = fReadsPutsStringsFromTable(X, Y, m - 1, n - 1, T);

			//a to na co trafilismy to nam pasuje wiec dodajemy
			for (int i = 0; i < lcs.size(); i++) {
				lcs.set(i, lcs.get(i) + (X.charAt(m - 1)));
			}
			return lcs;
		}
		// -----------------------------------------------------------------------------------jak ostatnia (aktualna) literka sie nie zgadza 

		// gorna wieksza tam sie przesuwamy
		if (T[m - 1][n] > T[m][n - 1])
			return fReadsPutsStringsFromTable(X, Y, m - 1, n, T);

		// lewa wieksza tam sie przesuwamy
		if (T[m][n - 1] > T[m - 1][n])
			return fReadsPutsStringsFromTable(X, Y, m, n - 1, T);

		// wartosc gory i lewej rowna wiec rozdzielamy funkcje na dwie
		List<String> top = fReadsPutsStringsFromTable(X, Y, m - 1, n, T);
		List<String> left = fReadsPutsStringsFromTable(X, Y, m, n - 1, T);

		//wszystkie stringi z obu list zbierz do jednej
		top.addAll(left);
		return top;
	}

	public static void fillTable(String X, String Y, int[][] T)//zad1
	{
		// dwa fory na przejscie duwymiarowe
		for (int i = 1; i <= X.length(); i++)
		{
			for (int j = 1; j <= Y.length(); j++)
			{
				// wez se skosa i dodaj 1
				if (X.charAt(i - 1) == Y.charAt(j - 1))
					T[i][j] = T[i - 1][j - 1] + 1;
				else //przepisz wiekszy (z gory lub z boku)
					T[i][j] = Integer.max(T[i - 1][j], T[i][j - 1]);
			}
		}
	}

	public static void main(String[] args)
	{
		String X = "ABCBDAB", Y = "BDCABA";
		int[][] T = new int[X.length() + 1][Y.length() + 1];
		//tu jest ten caly algorytm z podpunktu pierwszego (wypelnia tabelke)
		fillTable(X, Y, T);

		// rekurencyjnie wypelniamy liste stringow tworzonych po tabelce
		List<String> lcs = fReadsPutsStringsFromTable(X, Y, X.length(), Y.length(), T);

		//HashSet to najlepszy sposob na usuniecie duplikatow z listy (Remove duplicate elements & Maintain the order of elements added to it)
		LinkedHashSet<String> hashSet = new LinkedHashSet<>(lcs);

		System.out.print(hashSet);
	}
}
