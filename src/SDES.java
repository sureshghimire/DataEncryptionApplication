import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class SDES {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//	Raw Text		Plain text				Cipher Text
		//	1110001110     10101010            11001010
		//	1110001110     01010101            01110000
	
		byte [] rawKey = {1,1,1,0,0,0,1,1,1,0};
		byte [] plainText= {1,0,1,0,1,0,1,0};		//8 bits
		
		
		try {	
		
			
			//keyGeneration(rawKey);
			byte [] k1 = getKey1(rawKey);
			byte [] k2 = getKey2(rawKey);
			Encrypt(rawKey, plainText, k1,k2);
			
			
			
		}
		catch(InputMismatchException e)
		{
			System.out.println("Invalid Input");
		}

	}



	//Key-1 Generation
	public static byte[] getKey1(byte[] rawKey) {
		//System.out.println("Raw Keys: "+Arrays.toString(rawKey));
		
				byte []permutedKeys = new byte[10];
				permutedKeys[0] = rawKey[1];
				permutedKeys[1] = rawKey[4];
				permutedKeys[2] = rawKey[6];
				permutedKeys[3] = rawKey[7];
				permutedKeys[4] = rawKey[0];
				permutedKeys[5] = rawKey[3];
				permutedKeys[6] = rawKey[5];
				permutedKeys[7] = rawKey[6];
				permutedKeys[8] = rawKey[8];
				permutedKeys[9] = rawKey[2];
				
				//System.out.println("Permuted Keys: "+Arrays.toString(permutedKeys));
				
				//Left Shift of 1st half LS-1 and 2nd half
				byte []LS1 = new byte[10];
				LS1[0] = permutedKeys[1];
				LS1[1] = permutedKeys[2];
				LS1[2] = permutedKeys[3];
				LS1[3] = permutedKeys[4];
				LS1[4] = permutedKeys[0];
				//2nd Half
				LS1[5] = permutedKeys[6];
				LS1[6] = permutedKeys[7];
				LS1[7] = permutedKeys[8];
				LS1[8] = permutedKeys[9];
				LS1[9] = permutedKeys[5];
				
				
				//System.out.println("Left Shift 1 of 1st Half and 2nd Half: "+ Arrays.toString(LS1));
			
				//Permutation 8 (P8) - Select and Permute	//1st and 2nd bits will be discarded
				//Index =		[		0, 1, 2, 3, 4, 5, 6, 7]
				//LS1 = 		[0, 1, 1, 1, 1, 0, 1, 1, 1, 0]
				//Output Index =[_,	_, 6, 4, 2, 1, 5, 7, 0, 3]
				
				byte []permutedKeys8 = new byte[8];
				permutedKeys8[0] = LS1[6];
				permutedKeys8[1] = LS1[4];
				permutedKeys8[2] = LS1[2];
				permutedKeys8[3] = LS1[1];
				permutedKeys8[4] = LS1[5];
				permutedKeys8[5] = LS1[7];
				permutedKeys8[6] = LS1[0];
				permutedKeys8[7] = LS1[3];
				
				//System.out.println("Key-1: "+ Arrays.toString(permutedKeys8));
				
		
		return permutedKeys8;
	}

	//Key-2 Generation
	public static byte[] getKey2(byte[] rawKey) {
		// TODO Auto-generated method stub
		
		//System.out.println("Raw Keys: "+Arrays.toString(rawKey));
		
				byte []permutedKeys = new byte[10];
				permutedKeys[0] = rawKey[1];
				permutedKeys[1] = rawKey[4];
				permutedKeys[2] = rawKey[6];
				permutedKeys[3] = rawKey[7];
				permutedKeys[4] = rawKey[0];
				permutedKeys[5] = rawKey[3];
				permutedKeys[6] = rawKey[5];
				permutedKeys[7] = rawKey[6];
				permutedKeys[8] = rawKey[8];
				permutedKeys[9] = rawKey[2];
				
				//System.out.println("Permuted Keys: "+Arrays.toString(permutedKeys));
				
				//Left Shift 2 on permuted Keys
				//Permuted Keys: [1, 0, 1, 1, 1, 0, 0, 1, 1, 1]
				//Left Shift of 1st half LS-1 and 2nd half
						byte []LS2 = new byte[10];
						LS2[0] = permutedKeys[2];
						LS2[1] = permutedKeys[3];
						LS2[2] = permutedKeys[4];
						LS2[3] = permutedKeys[0];
						LS2[4] = permutedKeys[1];
						//2nd Half
						LS2[5] = permutedKeys[7];
						LS2[6] = permutedKeys[8];
						LS2[7] = permutedKeys[9];
						LS2[8] = permutedKeys[5];
						LS2[9] = permutedKeys[6];
						
						//System.out.println("Left Shift 2 on 1st & 2nd Half:"+ Arrays.toString(LS2));
				
						//Permutation 8 (P8) on LS2 - Select and Permute	//1st and 2nd bits will be discarded
						//Index =		[		0, 1, 2, 3, 4, 5, 6, 7]
						//LS2 = 		[1, 1, 1, 1, 0, 1, 1, 1, 0, 0]
						//Output Index =[_,	_, 6, 4, 2, 1, 5, 7, 0, 3]
			
						byte []permutedKeys8_2 = new byte[8];
						permutedKeys8_2[0] = LS2[6];
						permutedKeys8_2[1] = LS2[4];
						permutedKeys8_2[2] = LS2[2];
						permutedKeys8_2[3] = LS2[1];
						permutedKeys8_2[4] = LS2[5];
						permutedKeys8_2[5] = LS2[7];
						permutedKeys8_2[6] = LS2[0];
						permutedKeys8_2[7] = LS2[3];
						
						//System.out.println("Key-2: "+ Arrays.toString(permutedKeys8_2));
						
				
		return permutedKeys8_2;
	}

	//Encrypt Method
	public static void Encrypt(byte[] rawKey, byte[] plainText, byte[] k1, byte[] k2) {		
		
		//Initial Permutation
		//Input:	[0,1,2,3,4,5,6,7]
		//Output: 	[5,7,2,3,1,6,0,4]
		
		
		System.out.println("\nPlain Text: "+ Arrays.toString(plainText));
		
		//Permuted Text:
		byte []initialPermuation = new byte[8];
		initialPermuation[0] = plainText[5];
		initialPermuation[1] = plainText[7];
		initialPermuation[2] = plainText[2];
		initialPermuation[3] = plainText[3];
		initialPermuation[4] = plainText[1];
		initialPermuation[5] = plainText[6];
		initialPermuation[6] = plainText[0];
		initialPermuation[7] = plainText[4];
		System.out.println("Initial Permuatation:"+ Arrays.toString(initialPermuation));
		
		//Operation on right half of initial permutation
		//Expand and Permuted
		//Input: 	[1,2,3,4]			Index: [0,1,2,3]
		//Output: 	[2,3,1,4,4,2,1,3]	Index: [1,2,0,3,3,1,0,2]
		byte []ep = new byte[8];
		ep[0]=initialPermuation[1];
		ep[1]=initialPermuation[2];
		ep[2]=initialPermuation[0];
		ep[3]=initialPermuation[3];
		ep[4]=initialPermuation[3];
		ep[5]=initialPermuation[1];
		ep[6]=initialPermuation[0];
		ep[7]=initialPermuation[2];
		
		System.out.println("\nExpand & Permutate:	"+ Arrays.toString(ep));
		System.out.println("Key-1:			"+Arrays.toString(k1));
		
		//DO XOR with ep and K1
		System.out.println("\n------K1 XOR ep -----");
		byte [] k1_XOR_ep = new byte [8];
		
		for(int i=0; i<k1_XOR_ep.length; i++)
		{
			if(ep[i] == k1[i])
			{
				k1_XOR_ep[i] =0;
			}else {
				k1_XOR_ep[i] =1;
				
			}
			
		}
		
		System.out.println("K1_XOR_EP: "+ Arrays.toString(k1_XOR_ep));
		//Split K1_XOR_ep into 2 4bits values
		byte []k1_XOR_ep_1 = new byte[4];
		for(int i=0; i<4; i++)
		{
			k1_XOR_ep_1[i] = k1_XOR_ep[i];
		}
		System.out.println("K1_XOR_ep_1:"+ Arrays.toString(k1_XOR_ep_1));
		
		byte []k1_XOR_ep_2 = new byte[4];
		for(int i=0; i<4; i++)
		{
			k1_XOR_ep_2[i] = k1_XOR_ep[i+4];
		}
		System.out.println("K1_XOR_ep_2:"+ Arrays.toString(k1_XOR_ep_2));
		
		
		//Use S-Box to Get S0 and S1 USING k1_XOR_ep_1
		//Get Row and Cols
		byte []S0_row = new byte[2];
		S0_row[0] = k1_XOR_ep_1[0];
		S0_row[1] = k1_XOR_ep_1[3];
		
		//********STUCKED************
		//Need to get S0_row as bits eg: 0011
		
		System.out.println("\nSO_row in Array:"+ Arrays.toString(S0_row));
		
		String S0_row_index =(S0_row[0])+""+(S0_row[1]);
		System.out.print("S0_row index bits: "+ S0_row_index);	//Decimal of this bits will be the corresponding index of S-box matrix

	
		byte []S0_col = new byte [2];
		S0_col[0] = k1_XOR_ep_1[1];
		S0_col[1] = k1_XOR_ep_1[2];
		
		System.out.println("\n\nS0_col in Array:"+ Arrays.toString(S0_col));
		
		String S0_col_index = S0_col[0]+ ""+S0_col[1];
		System.out.println("S0_col index bits:"+ S0_col_index);
		
		//Create manual S-Box
		int[][] S0_Box = {{01,00,11,10},
						{11,10,01,00},
						{00,10,01,11}, 
						{11,01,11,10}};
		
		System.out.println("\nLength of S0_Box: "+ S0_Box.length);
		for(int row =0; row<S0_Box.length; row++) {
			System.out.println("Row:"+row);
		}
		
		
		//Use S-Box to Get S0 and S1 USING k1_XOR_ep_1
		//Get Row and Cols
		byte []S1_row = new byte[2];	//S1_row -Decimal value will be the row index of S0- Box
		S1_row[0] = k1_XOR_ep_2[0];		
		S1_row[1] = k1_XOR_ep_2[3];
		System.out.println("\nS1_row in Array:"+Arrays.toString(S1_row));
		
		String S1_row_index = S1_row[0] +""+S1_row[0];
		System.out.println("S1_row_index bits:"+ S1_row_index);
		
		
		byte []S1_col = new byte [2];	////S1_col -Decimal value will be the col index of S0- Box
		S1_col[0] = k1_XOR_ep_2[1];
		S1_col[1] = k1_XOR_ep_2[2];		
		System.out.println("\nS1_Col in Array:"+Arrays.toString(S1_col));
		
		String S1_col_index = S1_col[0]+""+S1_col[1];
		System.out.println("S1_Col index bits:"+ S1_col_index);
		
		
		//Declare 4*4 S0
		/*
		 * 	S0 = [01, 00, 11, 10
		 * 		  11, 10, 01, 00
		 * 		  00, 10, 01, 11,
		 * 		  11, 01, 11, 10]
		 * 
		 * 
		 */
		//S0_row & S0_col decimal values will be the index of S-Box
		//byte [][][][] S0_Matrix =new byte [4][4][4][4];	
		//S0_Matrix[0][0][0][0] = 01;
		
		
		
				
				
		byte []SO= new byte[1];
		byte []S1 = new byte[1];
		
		
	}
	
	
	
	//Permutation P10
	//Index		   [0,1,2,3,4,5,6,7,8,9]
	//Key text = [1,2,3,4,5,6,7,8,9,10]
	
	//Permuted = [2,5,7,8,1,4,6,10,9,3]
	//Index		 [0,1,2,3,4,5,6,7,8,9]
	
	
	
	public static void keyGeneration( byte[] rawKey) {
		//System.out.println("Raw Keys: "+Arrays.toString(rawKey));
		
		byte []permutedKeys = new byte[10];
		permutedKeys[0] = rawKey[1];
		permutedKeys[1] = rawKey[4];
		permutedKeys[2] = rawKey[6];
		permutedKeys[3] = rawKey[7];
		permutedKeys[4] = rawKey[0];
		permutedKeys[5] = rawKey[3];
		permutedKeys[6] = rawKey[5];
		permutedKeys[7] = rawKey[6];
		permutedKeys[8] = rawKey[8];
		permutedKeys[9] = rawKey[2];
		
		//System.out.println("Permuted Keys: "+Arrays.toString(permutedKeys));
		
		//Left Shift of 1st half LS-1 and 2nd half
		byte []LS1 = new byte[10];
		LS1[0] = permutedKeys[1];
		LS1[1] = permutedKeys[2];
		LS1[2] = permutedKeys[3];
		LS1[3] = permutedKeys[4];
		LS1[4] = permutedKeys[0];
		//2nd Half
		LS1[5] = permutedKeys[6];
		LS1[6] = permutedKeys[7];
		LS1[7] = permutedKeys[8];
		LS1[8] = permutedKeys[9];
		LS1[9] = permutedKeys[5];
		
		
		//System.out.println("Left Shift 1 of 1st Half and 2nd Half: "+ Arrays.toString(LS1));
	
		//Permutation 8 (P8) - Select and Permute	//1st and 2nd bits will be discarded
		//Index =		[		0, 1, 2, 3, 4, 5, 6, 7]
		//LS1 = 		[0, 1, 1, 1, 1, 0, 1, 1, 1, 0]
		//Output Index =[_,	_, 6, 4, 2, 1, 5, 7, 0, 3]
		
		byte []permutedKeys8 = new byte[8];
		permutedKeys8[0] = LS1[6];
		permutedKeys8[1] = LS1[4];
		permutedKeys8[2] = LS1[2];
		permutedKeys8[3] = LS1[1];
		permutedKeys8[4] = LS1[5];
		permutedKeys8[5] = LS1[7];
		permutedKeys8[6] = LS1[0];
		permutedKeys8[7] = LS1[3];
		
		//System.out.println("Key-1: "+ Arrays.toString(permutedKeys8));
		
		//Left Shift 2 on permuted Keys
		//Permuted Keys: [1, 0, 1, 1, 1, 0, 0, 1, 1, 1]
		//Left Shift of 1st half LS-1 and 2nd half
				byte []LS2 = new byte[10];
				LS2[0] = permutedKeys[2];
				LS2[1] = permutedKeys[3];
				LS2[2] = permutedKeys[4];
				LS2[3] = permutedKeys[0];
				LS2[4] = permutedKeys[1];
				//2nd Half
				LS2[5] = permutedKeys[7];
				LS2[6] = permutedKeys[8];
				LS2[7] = permutedKeys[9];
				LS2[8] = permutedKeys[5];
				LS2[9] = permutedKeys[6];
				
				//System.out.println("Left Shift 2 on 1st & 2nd Half:"+ Arrays.toString(LS2));
		
				//Permutation 8 (P8) on LS2 - Select and Permute	//1st and 2nd bits will be discarded
				//Index =		[		0, 1, 2, 3, 4, 5, 6, 7]
				//LS2 = 		[1, 1, 1, 1, 0, 1, 1, 1, 0, 0]
				//Output Index =[_,	_, 6, 4, 2, 1, 5, 7, 0, 3]
	
				byte []permutedKeys8_2 = new byte[8];
				permutedKeys8_2[0] = LS2[6];
				permutedKeys8_2[1] = LS2[4];
				permutedKeys8_2[2] = LS2[2];
				permutedKeys8_2[3] = LS2[1];
				permutedKeys8_2[4] = LS2[5];
				permutedKeys8_2[5] = LS2[7];
				permutedKeys8_2[6] = LS2[0];
				permutedKeys8_2[7] = LS2[3];
				
				//System.out.println("Key-2: "+ Arrays.toString(permutedKeys8_2));
				
				
				
	}
	
}
;