import java.util.ArrayList;
import java.util.Scanner;

public class BitMapHoles {
	
	private ArrayList<Range> hole;
	private ArrayList<ArrayList<Range>> grp = new ArrayList<ArrayList<Range>>();
	private int count = 0;
	private int[][] mat;
	
	
	private class Range{
		int start, end, mark;
	}
	
	private int[] sameGrp(Range ran) {
		
		int rack[] = new int[grp.size()]; int countx = 0;
		for(int i = 0; i < rack.length; i++) {
			if(grp.get(i).contains(ran)) { rack[i] = 1; countx++; grp.get(i).remove(ran);} 
			else rack[i] = 0;
		}
		count = count - (countx - 1); System.out.println("dec count "+count);
		return rack;
	}
	
	//merging similar groups
	private void merge(Range ran) {
		ArrayList<ArrayList<Range>> newGrp = new ArrayList<ArrayList<Range>>(grp);
		int[] rack = sameGrp(ran);
		ArrayList<Range> collect = new ArrayList<Range>();
		for(int i = 0 ; i > rack.length; i++) {
			if(rack[i] == 1) {
				collect.addAll(grp.get(i));
				
				newGrp.remove(i);
			}
		}
		collect.add(ran);
		newGrp.add(collect);
		grp = newGrp;
		System.out.println("///////////CHECKING GRP");
		for(ArrayList<Range> e: grp) {
			for(Range r: e) {
				System.out.println("start-"+r.start+" end-"+r.end+" mark-"+r.mark+" hole-"+grp.indexOf(e));
			}
		}System.out.println("///////////CHECKING Ends");
		
	}
	
	private int range(int l, int n) { // lth lvl, range starts from nth element and nth element is 0
		//System.out.println("l="+l+", n="+n);
		while(n<mat[0].length&&mat[l][n]==0) {
			n++;
		} System.out.println("n="+n);
		return --n;
	}
	
	private boolean addition(Range ran, int lev) { //find n add
		boolean flag = false;
		for(ArrayList<Range> h: grp) {
			for(int i = 0; i < h.size(); i++) {
				if((lev-1 == h.get(i).mark)&&((ran.start>=h.get(i).start&&ran.start<=h.get(i).end)||(ran.end>=h.get(i).start&&ran.end<=h.get(i).end))) { //if lies in hole
					h.add(ran); flag = true; System.out.println("Range matched - "+h.get(i).start+" to "+h.get(i).end+", Of hole "+grp.indexOf(h)+" lev="+lev+" mark="+h.get(i).mark);
					i = h.size(); // simply skip iteration
				}
			}
		}
		return flag;
	}
	
	public int bitMapMain() {
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat[0].length; j++) {
				System.out.println("i="+i+", j="+j);
				if(mat[i][j]==0) {
					Range ran = new Range(); ran.start = j; ran.end = j; ran.mark = i;
					System.out.println("GGi="+i+", j="+j);
					ran.end = range(i, j); 
					j = ran.end;// exclusion of already counted 0s
					boolean flag = false;
					if(i==0) { // For first row
						hole = new ArrayList<Range>(); count++; System.out.println("incr count: "+count);
						hole.add(ran);
						grp.add(hole);
					}
					//Finding membership of ran with holes and merging holes that are now joined after addition of ran
					else{ 
						flag = addition(ran, i);System.out.println("flag "+flag);
						if(flag) merge(ran); 
						else { // new hole
							hole = new ArrayList<Range>();
							hole.add(ran);
							grp.add(hole); count++; System.out.println("incr count: "+count);
						}
					    
					}    
				}
			}
		}
		return count;
	}
	
	public BitMapHoles() { //find a sequence of 0s at kth lvl, compare it with sequences found in (k-1)th lvl 
		// TODO Auto-generated constructor stub	
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter input: ");
		String txt = sc.nextLine();
		sc.close();
		String txtc = txt.substring(1, txt.length()-1);
		String[] strArr  = txtc.split(",", 0);
		int m[][] = new int[strArr.length][strArr[0].length()];
		for(int j = 0; j < strArr.length; j++) {
			System.out.println(strArr[j]);
			String[] line = strArr[j].split("",0);
			for(int i = 0; i < strArr[0].length(); i++) {
				m[j][i] = Integer.parseInt(line[i]);
			}
		}
		mat = m;
	}
	
	public static void main(String[] args) {
		
		// input: {10010,00110,10101,11001}
		BitMapHoles test = new BitMapHoles();
		//TODO- range should only check the ranges in holes till lvl k-1th
		System.out.println(test.bitMapMain());
		
	}
}
