import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.processing.CMXController;
import jp.crestmuse.cmx.elements.*;
import java.util.*;

class ToBrokenChord{
    //LinkedList<LinkedList< MutableMusicEvent>> section = new LinkedList();   //1小節

    int per4 = 480;
    int per8 = 240;
    int per2 = 960;


    //４分音符分散和音化
      public LinkedList<LinkedList< MutableMusicEvent>>  brokenPer4(LinkedList<LinkedList<MutableMusicEvent>> section){
        LinkedList<LinkedList< MutableMusicEvent>> balladSection = new LinkedList();   //1小節
        int i;
        for(i=0; i<section.get(0).size(); i++){
            LinkedList<MutableMusicEvent> list = new LinkedList();
            list.add(section.get(0).get(i));
            balladSection.add(list);
        }
        
        
        for(i=0; i<balladSection.size()-1; i++){
            balladSection.get(i).get(0).setOffset(balladSection.get(0).get(0).onset()+per4*(i+1));
            balladSection.get(i+1).get(0).setOnset(balladSection.get(0).get(0).onset()+per4*(i+1));
         }
            balladSection.get(i).get(0).setOffset(balladSection.get(0).get(0).onset()+per4*4);

            return balladSection;

 	  }

      //8分音符分散和音化
      public LinkedList<LinkedList< MutableMusicEvent>>  brokenPer8(LinkedList<LinkedList<MutableMusicEvent>> section){
        LinkedList<LinkedList< MutableMusicEvent>> balladSection = new LinkedList();   //1小節
         int i;
        for(i=0; i<section.get(0).size(); i++){
            LinkedList<MutableMusicEvent> list = new LinkedList();
            list.add(section.get(0).get(i));
            balladSection.add(list);
        }
        
        for(i=0; i<balladSection.size()-1; i++){
            balladSection.get(i).get(0).setOffset(balladSection.get(0).get(0).onset()+per8*(i+1));
            balladSection.get(i+1).get(0).setOnset(balladSection.get(0).get(0).onset()+per8*(i+1));
         }
            balladSection.get(i).get(0).setOffset(balladSection.get(0).get(0).onset()+per8*8);

            return balladSection;

 	  }


 	   //2和音2分音符分散和音化
      public LinkedList<LinkedList<MutableMusicEvent>>  second2BrokenPer2(LinkedList<LinkedList<MutableMusicEvent>> section){
        LinkedList<LinkedList< MutableMusicEvent>> balladSection = new LinkedList();   //1小節

        for(int i=0; i<section.get(0).size(); i++){
            LinkedList<MutableMusicEvent> list = new LinkedList();
            list.add(section.get(0).get(i));
            balladSection.add(list);
         }
         
         int i;
         if(balladSection.size() >= 2){
            balladSection.get(0).get(0).setOffset(balladSection.get(0).get(0).onset()+per2);
            balladSection.get(1).get(0).setOnset(balladSection.get(0).get(0).onset()+per2);
            balladSection.get(1).get(0).setOffset(balladSection.get(0).get(0).onset()+per2*2);
         }else if(balladSection.size() < 2){
            for(i=0; i<balladSection.size()-1; i++){
              balladSection.get(i).get(0).setOffset(balladSection.get(0).get(0).onset()+per8*(i+1));
              balladSection.get(i+1).get(0).setOnset(balladSection.get(0).get(0).onset()+per8*(i+1));
            }
            balladSection.get(i).get(0).setOffset(balladSection.get(0).get(0).onset()+per8*8);
         }

            return balladSection;

 	  }

 }
