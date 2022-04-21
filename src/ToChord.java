import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.processing.CMXController;
import jp.crestmuse.cmx.elements.*;
import java.util.*;

class ToChord{
    int per4 = 480;
    int per2 = 960;


     //全音符和音化
     public LinkedList<LinkedList<MutableMusicEvent>>  ToChordPer1(LinkedList<LinkedList<MutableMusicEvent>> section){
       LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();

        balladSection.add(section.get(0));
        for(int i=0; i<balladSection.get(0).size(); i++){
           balladSection.get(0).get(i).setOffset(balladSection.get(0).get(0).onset()+per4*4);
        }
           return balladSection;
  }


  //2分音符和音化
       public LinkedList<LinkedList<MutableMusicEvent>>  ToChordPer2(LinkedList<LinkedList<MutableMusicEvent>> section){
       LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();

       balladSection.add(section.get(0));
       balladSection.add(section.get(section.size()/2));
       for(int i=0; i<balladSection.get(0).size(); i++){
         balladSection.get(0).get(i).setOffset(balladSection.get(0).get(0).onset()+per2);
       }
       
       for(int i=0; i<balladSection.get(1).size(); i++){
          balladSection.get(1).get(i).setOffset(balladSection.get(1).get(0).onset()+per2);
       }
     
          return balladSection;

          }


  }
