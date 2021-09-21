import java.util.*;
import java.io.*;
public class Encoder {


	public static final int BITS = 12;//amount of bits that something will take up
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		final long startTime = System.currentTimeMillis();
		
		BufferedReader fileInput = new BufferedReader(new FileReader(new File("original.txt")));//reader

		BitWriter fileOutput = new BitWriter(new FileOutputStream("encoded.txt"));
		
		
		//custom printer class to print bits specifically, but in bytes since we can't print bits	

		HashMap<String,Integer> dict = new HashMap<String, Integer>();//dictionary

		//initialize table
		for(int i = 0;i<256;i++)
		{
			dict.put(""+(char)i, i);
		}

//		String current = "";
//		String next = "";
		StringBuilder current = new StringBuilder();
		StringBuilder next = new StringBuilder();


		//starting input
		int input = fileInput.read();//read gives a number for the character or -1 if there's nothing left
		if(input==-1)
		{
			System.out.println("Nothing in the file");
			return; //stops the program if there's no text in the file
		}
		//current = ""+((char)input);
		current.replace(0, current.length(),""+(char)input);
		
		

		int newestIndex = 256;//newest index to use to put in dictionary
		while(true)
		{
			input = fileInput.read();
			if(input==-1)//input is -1 if at the end
				break;//stops reading if there's no more text to read


			//next = ""+(char)input;
			next.replace(0, next.length(), ""+(char)input);

			//String combine = current + next;
			String combine = current.toString() + next.toString();

			if(dict.containsKey(combine))//if we already have seen current+next
			{
				//current = combine; //set the current to combined
				current.replace(0, current.length(), combine);
			}
			else
			{

				//System.out.print(toBinary(dict.get(current.toString())));

				//write(toBinary(dict.get(current)),fileOutput); //writes out binary versions of our numbers to encodex.txt
				write(toBinary(dict.get(current.toString())),fileOutput);
				
				
				dict.put(combine, newestIndex); //adds combined to dictionary
				newestIndex++;

				current = next;
			}

		}
		//write(toBinary(dict.get(current)),fileOutput); //writes the last item?
		write(toBinary(dict.get(current.toString())),fileOutput);
		
		//System.out.println(toBinary(dict.get(current)));
		//System.out.println(toBinary(dict.get(current.toString())));

		fileOutput.close();
		
		final long endTime = System.currentTimeMillis();

		System.out.println("Total encoder execution time: " + (endTime - startTime));
		System.out.println("Original time was about 15-17");
		
	}
	public static void write(String binary, BitWriter fout) throws IOException //writes things to the file as binary
	{
		for(int i =0;i<binary.length();i++)
		{
			if(binary.charAt(i)=='0')
			{
				fout.write(false);
			}
			else
			{
				fout.write(true);
			}
		}
	}
	public static String toBinary(int a)
	{
		StringBuilder cur = new StringBuilder(Integer.toBinaryString(a));
		StringBuilder ans = new StringBuilder();
		while(cur.length()+ans.length()<BITS)
		{
			ans.append("0");
		}
		ans.append(cur);
		return ans.toString();
	}
	static class BitWriter {//very important to understand in order to decode
		private FileOutputStream out;
		private boolean[] buffer = new boolean[8];//each byte is 8 bits long
		private int count = 0;

		public BitWriter(FileOutputStream out) {
			this.out = out;
		}

		public void write(boolean x) throws IOException 
		{
			this.buffer[this.count] = x;
			this.count++;


			//takes up a boolean (true [1] or false [0]) each time this method is called
			//puts this 1 or zero in the front of the boolean array, so if you wrote 11001000
			//it would give you an array of [True, True, False, False, True, False, False, False]
			//if we have put in 8 bits already, we are going to print a byte now
			if (this.count == 8)
			{
				int num = 0;
				//num is the value of the character(byte) we're going to print
				
				
				for (int index = 0; index < 8; index++)
				{
					if(this.buffer[index])
					{
						num += (1<<(7-index));//this is the same as 2^(7-index)
						/*
						the reason we're doing 2^(7-index) instead of 2^index is because the
						1st element (buffer[0]) in our array is actually the biggest part of the binary string
						since it goes 2^7, 2^6, 2^5, ..., 2^0
						So, the first bit we have actually has the largest value, and the next ones are smaller
						*/
					}
				}
				//System.out.println(num);
				
				this.out.write((char)num);//we "print" here, (putting it in the outputstream
				
				this.count = 0;//reset count so we can have a new binary string inputted
				//Arrays.fill(this.buffer, false);//resetting our buffer array so it's just 00000000
			}
		}
		public static String toBinary8(int a)
		{
			StringBuilder cur = new StringBuilder(Integer.toBinaryString(a));
			StringBuilder ans = new StringBuilder();
			while(cur.length()+ans.length()<8)
			{
				ans.append("0");
			}
			ans.append(cur);
			return ans.toString();
		}

		public void close() throws IOException {
			//final "print" before closing to account for leftover ones (if our total bit length%8!=0)
			//we need to check if count>0, because if it is 0, then that means there's nothing we said to print
			//so we shouldn't print 0, and instead just not print anything
			if(this.count>0)
			{
				int num = 0;
				/*
				same implementation as before, but I did something lazy here (MOST IMPORTANT PART)
				I'm printing a byte, which is 8 bits, but what if there were like 2 leftover bits instead 
				of 8?
				I decided just to put the 2 bits at the front, and lead in the back with trailing zeroes
				So, when coding your decoder, you should just keep getting each character
				(12 ones and zeroes will make up a number that will signify the character), 
				and if there are any remaining that's not divisible by 12
				those are all going to be zeroes, so just ignore them at the end.
				 */
				
				for (int index = 0; index < this.count; index++){
					if(this.buffer[index])
					{
						num += (1<<(7-index));
					}
				}
				this.out.write((char)num);
			}

			this.out.close();
		}

	}


}
