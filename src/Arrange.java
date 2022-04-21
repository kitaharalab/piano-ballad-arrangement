import java.io.*;
import java.util.*;
import javax.xml.transform.TransformerException;
import be.ac.ulg.montefiore.run.jahmm.*;

public class Arrange{
  
   public int[] arrangeI(){
       int[] I = new int[20];
       I[2] = 3;
       I[9] = 10;
       
       return I;
   }
       
   public int[] arrangeA(){
       int[] A = new int[20];
       A[3] = 4;
       A[9] = 10;
       
       return A;
   } 

    public int[] arrangeB(){
       int[] B = new int[20];
       B[1] = 2;
       B[9] = 10;
       B[12] = 13;
       
       return B;
   } 
   
   
     public int[] arrangeC(){
       int[] C = new int[20];
       C[3] = 4;
       C[9] = 10;
       
       return C;
   } 
   
    public int[] arrangeS(){
       int[] S = new int[20];
       S[1] = 2;
       S[9] = 10;
       S[10] = 11;
       S[12] = 13;
       
       
       return S;
   }
   
   
    public int[] arrangeO(){
       int[] O = new int[20];
       O[4] = 5;
       O[9] = 10;
       
       return O;
   }
   
   
    public int[] arrangeR(){
       int[] R = new int[20];
       Random r = new Random();
       
       
       
         int chordA;
         int rhythmA;
         int octaveA;
         int chord5A;
         int chord8A;
         int add9A;
         int BaCA;
    	 int openA;
    	 int closeA;
    	 int removeA;

        chordA = r.nextInt(6);
        rhythmA = r.nextInt(4)+6;
       
        octaveA = r.nextInt(2);
        if(octaveA == 1)octaveA = 10;
      
        chord5A = r.nextInt(2);
        if(chord5A == 1)chord5A = 12;
       
        chord8A = r.nextInt(2);
        if(chord8A == 1)chord8A = 13;
       
        removeA = r.nextInt(2);
        if(removeA == 1)removeA = 18;
        
        BaCA = r.nextInt(2);
        if(BaCA == 1)BaCA = 11;
       
        openA = r.nextInt(2);
        if(openA == 1)openA = 15;
       
        closeA = r.nextInt(2);
        if(closeA == 1)closeA = 16;
        
        add9A = r.nextInt(2);
        if(add9A == 1)add9A = 14;
         
          
     
       
       
      for(int i=0; i<20;  i++){
          if(i == chordA-1){
            R[i] = i+1;
          }else if(i == rhythmA-1){
            R[i] = i+1;
          }else if(i == octaveA-1){
            R[i] = i+1;
          }
        
        
        
       if(1<=chordA && chordA<=3){
         if(i == BaCA-1){
           R[i] = i+1;
         }else if(i == openA-1){
           R[i] = i+1; 
         }else if(i == add9A-1){
           R[i] = i+1; 
         }else if(i == chord5A-1){
           R[i] = i+1;
         }else if(i == chord8A-1){
            R[i] = i+1;
         }
       }
       
       
       
       if(4<=chordA && chordA<=5){
           if(i == closeA-1){
              R[i] = i+1;
           }else if(i == removeA-1){
              R[i] = i+1;
           }else if(i == chord5A-1){
              R[i] = i+1;
          }  
        }
       }
     
                    
       return R;
   }

 
}



