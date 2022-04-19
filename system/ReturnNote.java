import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.processing.CMXController;
import jp.crestmuse.cmx.elements.*;
import java.util.*;

class ReturnNote{
    LinkedList<LinkedList<MutableMusicEvent>> section = new LinkedList();
    LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();


    ReturnNote(LinkedList<LinkedList<MutableMusicEvent>> section){
         this.section = section;
        }




    public LinkedList<LinkedList<MutableMusicEvent>> noChange(){
     for(int i=0; i<section.size(); i++){
      balladSection.add(section.get(i));
      }

    return balladSection;
   	}
  }
