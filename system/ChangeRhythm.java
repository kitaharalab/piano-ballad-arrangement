import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.processing.CMXController;
import jp.crestmuse.cmx.elements.*;
import java.util.*;

class ChangeRhythm{
   // LinkedList<LinkedList<MutableMusicEvent>> section = new LinkedList();
   // LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
    int per16 = 120;
    int per8 = 240;
    int per4 = 480;
    int per2 = 960;

    /*  ChangeRhythm(LinkedList<LinkedList<MutableMusicEvent>> section){
            this.section = section;
     }
    */
     //16分音符4分音符化
     public LinkedList<LinkedList<MutableMusicEvent>>  sixteenth2Per4(LinkedList<LinkedList<MutableMusicEvent>> section){
       LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
         for(int i=0; i<section.size(); i++){
       	  balladSection.add(section.get(i));
          if(balladSection.getLast().get(0).offset()-balladSection.getLast().get(0).onset() == per16){
		   	  for(int j=0; j<balladSection.getLast().size(); j++){
		    	  balladSection.getLast().get(j).setOffset(balladSection.getLast().get(0).onset()+per16*4);
		      }
	       	  	i += 3;
       	  }
       }
           return balladSection;
      }

      //16分音符2分音符化
     public LinkedList<LinkedList<MutableMusicEvent>>  sixteenth2Per2(LinkedList<LinkedList<MutableMusicEvent>> section){
       LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
       // System.out.println(section.size());
        for(int i=0; i<section.size(); i++){
       	  balladSection.add(section.get(i));
          if(balladSection.getLast().get(0).offset()-balladSection.getLast().get(0).onset() == per16){
		   	  for(int j=0; j<balladSection.getLast().size(); j++){
		    	balladSection.getLast().get(j).setOffset(balladSection.getLast().get(0).onset()+per16*8);
		      }
	       	  	i += 7;
       	  }
        }
           return balladSection;
      }

      //8分音符4分音符化
     public  LinkedList<LinkedList<MutableMusicEvent>>  eighth2Per4(LinkedList<LinkedList<MutableMusicEvent>> section){
       // System.out.println(section.size());
        LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
        for(int i=0; i<section.size(); i++){
       	   balladSection.add(section.get(i));
           if(balladSection.getLast().get(0).offset()-balladSection.getLast().get(0).onset() == per8){
		   	  for(int j=0; j<balladSection.getLast().size(); j++){
		    	balladSection.getLast().get(j).setOffset(balladSection.getLast().get(0).onset()+per8*2);
		      }
	       	  	i += 1;
       	  }
        }
           return balladSection;
      }

        //8分音符2分音符化
     public LinkedList<LinkedList<MutableMusicEvent>>  eighth2Per2(LinkedList<LinkedList<MutableMusicEvent>> section){
      LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
      for(int i=0; i<section.size(); i++){
       	  balladSection.add(section.get(i));
          if(balladSection.getLast().get(0).offset()-balladSection.getLast().get(0).onset() == per8){
		   	  for(int j=0; j<balladSection.getLast().size(); j++){
		    	balladSection.getLast().get(j).setOffset(balladSection.getLast().get(0).onset()+per8*4);
		      }
	       	  	i += 3;
       	  }
        }

           return balladSection;
      }



}
