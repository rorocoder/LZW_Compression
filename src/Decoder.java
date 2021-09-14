import java.util.*;
import java.io.*;
public class Decoder {


	
	//public static final int BITS = 12;
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		FileInputStream in = new FileInputStream("encoded.txt");//reader
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("decoded.txt")));
		HashMap<Integer,String> decodeMap = new HashMap<Integer,String>();
		int counter = 0;
		
		for (int i=0; i<256; i++) //initializing existing ascii values in hashmap
		{
			decodeMap.put(counter,String.valueOf((char)(i)));
			counter++;
		}
		
		StringBuilder binary = new StringBuilder();
		byte[] bits = in.readAllBytes();
		for(int i =0;i<bits.length;i++)
		{
			System.out.println(bits[i]+" "+toBinary(bits[i]));
			out.print(toBinary(bits[i]));
			binary.append(toBinary(bits[i]));
		}
		
		System.out.println(binary);
		int currInt, nextInt;
		
		System.out.println(""+binaryToInt("000100000110"));
		for(int i = 0; i< binary.length()-12; i+=12) 
		{
			String currBin = binary.substring(i,i+12);
			currInt = binaryToInt(currBin);
			String nextBin = binary.substring(i+12, i+24);
			nextInt = binaryToInt(nextBin);
			String currWFirstNext = decodeMap.get(nextInt).substring(0,1);// i = 72 and nextInt becomes 262 which doesnt exist yet
			decodeMap.put(counter++,  "" + decodeMap.get(currInt) + currWFirstNext );
			
		}
		//000100000011
		//need to have a maximum size?
		

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
	
	public static int binaryToInt(String binary){
		
		return Integer.parseInt(binary,2);
		
	}
	


	
}
