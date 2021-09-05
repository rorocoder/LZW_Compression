import java.util.*;
import java.io.*;
public class Decoder {

	
	//public static final int BITS = 12;
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		FileInputStream in = new FileInputStream("encoded.txt");//reader
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("decoded.txt")));
		
		StringBuilder ans = new StringBuilder();
		byte[] bits = in.readAllBytes();
		for(int i =0;i<bits.length;i++)
		{
			System.out.println(bits[i]+" "+toBinary(bits[i]));
			out.print(toBinary(bits[i]));
		}
		/*
		while(true)
		{
			int inp = in.read();
			if(inp==-1)
				break;
			/*if(inp>=256)
				inp = inp%256;
				
			System.out.println(inp+" "+toBinary(inp));
			//ans.append(toBinary(inp));
			out.print(toBinary(inp));
			
		}*/
	
		/*for(int i =0;i<ans.length();)
		{
			if(i+12>=ans.length())
			{
				out.println(ans.substring(i));
				break;
			}
			out.println(ans.substring(i,i+12));
			i = i+12;
		}*/
		out.close();

	}
	public static String toBinary(int a)
	{
		String cur =Integer.toBinaryString(a);
		StringBuilder ans = new StringBuilder();
		while(cur.length()+ans.length()<8)
		{
			ans.append("0");
		}
		if(cur.length()>8)
		{
			cur = cur.substring(cur.length()-8);
		}
		ans.append(cur);
		return ans.toString();
	}

}
