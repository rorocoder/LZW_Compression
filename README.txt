Hey this code has a bunch of comments that you should read, but I'll also give a quick rundown.
First, the code reads from "original.txt"
The code is using 12 BITS, so like 11001 will actually be as 000000011001. 
After getting the 12 bits, we make a byte, which is 8 bits, and print that to the file "encoded.txt"
This will give a bunch of characters that look like question marks and stuff.
I also have a decoder class (Decoder.java) that you should probably work in. So far, it only reads all of the input (the weird
characters) and just prints out the binary. 
So, you just have to convert to numbers by taking 12 bits at a time and getting the number value for those 12 bits, and then
do the decoding algorithm. 

There's also a bunch of explanation about BitWriter, a writer class I made to write bits out, so you can
look at the comments for that if you want.
