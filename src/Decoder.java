import java.util.*;
import java.io.*;
public class Decoder {


	
	//public static final int BITS = 12;
	public static void main(String[] args) throws IOException{
		
		final long startTime = System.currentTimeMillis();
		//System.out.println("S");
		
		// TODO Auto-generated method stub
		//FileInputStream in = new FileInputStream("encoded.txt");
		try  {
		    FileInputStream in  = new FileInputStream("encoded.txt");
		    
		  //reader
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("decoded.txt")));
			HashMap<Integer,String> decodeMap = new HashMap<Integer,String>();
			int counter = 0;
			
			for (int i=0; i<256; i++) //initializing existing ascii values in hashmap
			{
				decodeMap.put(counter,String.valueOf((char)(i)));
				counter++;
			}
			
			//bits to binary
			StringBuilder binary = new StringBuilder();
			byte[] bits = in.readAllBytes();
			for(int i =0;i<bits.length;i++)
			{
				//System.out.println(bits[i]+" "+toBinary(bits[i]));
				//out.print(toBinary(bits[i]));
				binary.append(toBinary(bits[i]));
			}
			
			//start rebuilding dictionary
			int currentInt, nextInt;//currentInt = old, nextInt = new
			String s,c, nextBin, currentBin;
			c = "";
			
			for(int i = 0; i<= binary.length()-12; i+=12) 
			{
				
				currentBin = binary.substring(i,i+12);
				//currentInt = binaryToInt(currentBin);
				currentInt = Integer.parseInt(currentBin,2);
				if(i+24 <= binary.length()-1) {
					 nextBin = binary.substring(i+12, i+24);
				}
				else {
					nextBin = binary.substring(i+12, binary.length());
				}
					//nextInt = binaryToInt(nextBin); 
					nextInt = Integer.parseInt(nextBin,2);
			
				
				if (!decodeMap.containsKey(nextInt)) { // if the key's value doesnt exist
					s  = decodeMap.get(currentInt); //string/character of current binary 
					s += c ; // current + first of next (defined below - will defintiley run the else statement first
					//decodeMap.put(counter++,  "" + s );//add it bc it doesnt exist yet
				}
				else {
					s = decodeMap.get(nextInt); //next binary combo's letter/character/string 
					
					//dont have to set old = newInt because reading in binary 12 at a time with for loop
				}
				c = s.substring(0,1);  //first character of s
				decodeMap.put(counter++,  "" + decodeMap.get(currentInt) + c );
				out.print(decodeMap.get(currentInt));
			}
			
			//need to have a maximum size?
			
		//	https://www.geeksforgeeks.org/lzw-lempel-ziv-welch-compression-technique/
			
			
			out.close();
			in.close();
			
		}catch  (FileNotFoundException e){
		    // The error  handling code  goes  here
			System.out.println("Your input file was not found");
		}
			
		
		
		final long endTime = System.currentTimeMillis();
		System.out.println("Original time was 5");
		System.out.println("Total decoder execution time: " + (endTime - startTime));

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
	
//	public static int binaryToInt(String binary){
//		
//		return Integer.parseInt(binary,2);
//		
//	} unnecessary method so commented out
	


	
}
