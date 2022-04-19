import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.processing.CMXController;
import jp.crestmuse.cmx.elements.*;
import java.util.*;

class Options{
     int oct = 12;
     int per16 = 120;
     int per8 = 240;
     int per4 = 480;
     int per2 = 960;

     SCCDataSet sccdataset;
     Options(SCCDataSet sccdataset){
       this.sccdataset = sccdataset;
     }


    //オクターブ化
      public LinkedList<LinkedList<MutableMusicEvent>>  toOctave(LinkedList<LinkedList<MutableMusicEvent>> section){
        LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
        for(int i=0; i<section.size(); i++){
              balladSection.add(section.get(i));
              for(int j=0; j<balladSection.getLast().size(); j++){
                if(balladSection.getLast().get(j) instanceof MutableMusicEvent){
                  MutableNote note = (MutableNote)balladSection.getLast().get(j);
                  note.setNoteNum(balladSection.getLast().get(j).notenum()+oct);
                //System.out.println(balladSection.getLast().get(j).notenum());
                }
              }
          }
            return balladSection;
 	  }

 	  //分散和音に和音取り入れ(最後の音符)
      public LinkedList<LinkedList<MutableMusicEvent>>  brokenChordAndChord(LinkedList<LinkedList<MutableMusicEvent>> section){
          LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
          for(int i=0; i<section.size(); i++){
            balladSection.add(section.get(i));
           }
           
         
          for(int i=1; i<balladSection.size(); i++){
            if(balladSection.get(i-1).getLast().notenum() > balladSection.get(i).getLast().notenum() || i == balladSection.size()-1){

              SCCDataSet optionsScc = new SCCDataSet(sccdataset.getDivision());
              SCCDataSet.Part optionsPart = optionsScc.addPart(1,1);

              int n = i-1;
              if(i == balladSection.size()-1) n = i;

              if(balladSection.get(n).getLast().notenum() % 12 == balladSection.get(0).get(0).notenum() % 12){
                 optionsPart.addNoteElement(balladSection.get(n).getLast().onset(),balladSection.get(n).getLast().offset(),balladSection.get(0).get(0).notenum()+oct+7,100,100);
               }else{
                 optionsPart.addNoteElement(balladSection.get(n).getLast().onset(),balladSection.get(n).getLast().offset(),balladSection.get(0).get(0).notenum()+oct,100,100);
               }
              SCCDataSet.Part part = optionsScc.getPart(0);
              MutableMusicEvent[] note = part.getNoteOnlyList();
              balladSection.get(n).add(note[0]);

            }
          }
          for(int i=0; i<balladSection.size(); i++){
          //  System.out.println(balladSection.get(i));
         }
         //System.out.println("");
            return balladSection;
        

 	  }

    //分散和音に和音取り入れ三拍目
 /*   public LinkedList<LinkedList<MutableMusicEvent>>  brokenChordAndChord(LinkedList<LinkedList<MutableMusicEvent>> section){
        LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
        for(int i=0; i<section.size(); i++){
          balladSection.add(section.get(i));
         }

        for(int i=0; i<balladSection.size(); i++){
          if(i % 4 == 2){

            SCCDataSet optionsScc = new SCCDataSet(sccdataset.getDivision());
            SCCDataSet.Part optionsPart = optionsScc.addPart(1,1);


            if(balladSection.get(i).getLast().notenum() % 12 == balladSection.get(0).get(0).notenum() % 12){
              optionsPart.addNoteElement(balladSection.get(i).getLast().onset(),balladSection.get(i).getLast().offset(),balladSection.get(0).get(0).notenum()+oct+4,100,100);
            }else{
               optionsPart.addNoteElement(balladSection.get(i).getLast().onset(),balladSection.get(i).getLast().offset(),balladSection.get(0).get(0).notenum()+oct,100,100);
           }
            SCCDataSet.Part part = optionsScc.getPart(0);
            MutableMusicEvent[] note = part.getNoteOnlyList();
            balladSection.get(i).add(note[0]);

          }
        }
        for(int i=0; i<balladSection.size(); i++){
        // System.out.println(balladSection.get(i));
       }
       //System.out.println("");
          return balladSection;

  }

*/
 	     //和音の音数を１減らす
      public LinkedList<LinkedList<MutableMusicEvent>> removeNote(LinkedList<LinkedList<MutableMusicEvent>> section){
           LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
           for(int i=0; i<section.size(); i++){
              balladSection.add(section.get(i));
              if(balladSection.getLast().size() >= 2){
              balladSection.getLast().remove(1);
            }
       }
            return balladSection;
     }


     //５度和音化
      public LinkedList<LinkedList<MutableMusicEvent>> add5Chord(LinkedList<LinkedList<MutableMusicEvent>> section){
          LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
          for(int i=0; i<section.size(); i++){
            balladSection.add(section.get(i));
           }

          for(int i=0; i<balladSection.size(); i++){
             SCCDataSet optionsScc = new SCCDataSet(sccdataset.getDivision());
              SCCDataSet.Part optionsPart = optionsScc.addPart(1,1);
              optionsPart.addNoteElement(balladSection.get(i).getLast().onset(),balladSection.get(i).getLast().offset(),balladSection.get(i).get(0).notenum()+7,100,100);
              SCCDataSet.Part part = optionsScc.getPart(0);
              MutableMusicEvent[] note = part.getNoteOnlyList();
              
              boolean add = false;
              for(int j=0; j<balladSection.get(i).size()-1; j++){
                 if(balladSection.get(i).get(j).notenum()<note[0].notenum() && note[0].notenum()<balladSection.get(i).get(j+1).notenum() ){ 
                   balladSection.get(i).add((j+1), note[0]);
                   add = true;
                  }
              }
              if(add == false)balladSection.get(i).add(note[0]);
          }
          return balladSection;
    }


     //オクターブ和音化
       public LinkedList<LinkedList<MutableMusicEvent>> add3Chord(LinkedList<LinkedList<MutableMusicEvent>> section){
          LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
          for(int i=0; i<section.size(); i++){
           balladSection.add(section.get(i));
          }

         for(int i=0; i<balladSection.size(); i++){
         //  if(balladSection.get(i).size() < 2){
             SCCDataSet optionsScc = new SCCDataSet(sccdataset.getDivision());
             SCCDataSet.Part optionsPart = optionsScc.addPart(1,1);
             optionsPart.addNoteElement(balladSection.get(i).get(0).onset(),balladSection.get(i).get(0).offset(),balladSection.get(i).get(0).notenum()+12,100,100);
             SCCDataSet.Part part = optionsScc.getPart(0);
             MutableMusicEvent[] note = part.getNoteOnlyList();
             balladSection.get(i).add(note[0]);
        //   }
         }
         return balladSection;

    }


 	   //add9にする(クローズ)
 	     public LinkedList<LinkedList<MutableMusicEvent>> toAdd9ForClose(LinkedList<LinkedList<MutableMusicEvent>> section){
          LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
          for(int i=0; i<section.size(); i++){
            balladSection.add(section.get(i));
           }

           int n = 480;
           if(balladSection.get(0).get(0).offset() - balladSection.get(0).get(0).onset() <= 240) n = 240;

          for(int i=0; i<balladSection.size(); i++){
            if(i == 1 || i == 5){
              SCCDataSet optionsScc = new SCCDataSet(sccdataset.getDivision());
              SCCDataSet.Part optionsPart = optionsScc.addPart(1,1);

              optionsPart.addNoteElement(balladSection.get(i).getLast().onset(),balladSection.get(i).getLast().offset(),balladSection.get(i-1).get(0).notenum()+2,100,100);
              SCCDataSet.Part part = optionsScc.getPart(0);
              MutableMusicEvent[] note = part.getNoteOnlyList();
              LinkedList<MutableMusicEvent> list = new LinkedList();
              list.add(note[0]);
              balladSection.add(i,list);

            }
          }


          for(int i=0; i<balladSection.size(); i++){
            if(i%4 == 2){
              balladSection.get(i).getLast().setOnset(balladSection.get(i).getLast().onset()+n);
              balladSection.get(i).getLast().setOffset(balladSection.get(i).getLast().offset()+n);
            }
            if(i%4 == 3){
              balladSection.get(i).getLast().setOnset(balladSection.get(i).getLast().onset()+n);
            }
          }

          for(int i=0; i<balladSection.size(); i++){
           //System.out.println(balladSection.get(i));
         }
         //System.out.println("");


          return balladSection;

 	  }

    //add9にする(オープン)
       public LinkedList<LinkedList<MutableMusicEvent>> toAdd9ForOpen(LinkedList<LinkedList<MutableMusicEvent>> section){
         LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
         for(int i=0; i<section.size(); i++){
           balladSection.add(section.get(i));
          }

          int n = 480;
          if(balladSection.get(0).get(0).offset() - balladSection.get(0).get(0).onset() <= 240) n = 240;
          
         for(int i=0; i<balladSection.size(); i++){
           if(i == 2 || i == 6){
             SCCDataSet optionsScc = new SCCDataSet(sccdataset.getDivision());
             SCCDataSet.Part optionsPart = optionsScc.addPart(1,1);

             optionsPart.addNoteElement(balladSection.get(i).getLast().onset(),balladSection.get(i).getLast().offset(),balladSection.get(i-2).get(0).notenum()+2+oct,100,100);
             SCCDataSet.Part part = optionsScc.getPart(0);
             MutableMusicEvent[] note = part.getNoteOnlyList();
             LinkedList<MutableMusicEvent> list = new LinkedList();
             list.add(note[0]);
             balladSection.add(i,list);

           }
         }


         for(int i=0; i<balladSection.size(); i++){
           if(i%4 == 2){
            // balladSection.get(i).getLast().setOnset(balladSection.get(i).getLast().onset()+n);
            // balladSection.get(i).getLast().setOffset(balladSection.get(i).getLast().offset()+n);
           }
           if(i%4 == 3){
             balladSection.get(i).getLast().setOnset(balladSection.get(i).getLast().onset()+n);
           }
         }

         for(int i=0; i<balladSection.size(); i++){
        //  System.out.println(balladSection.get(i));
        }
      //  System.out.println("");

           return balladSection;

    }

 	   //オープンボイシング化
      public LinkedList<LinkedList<MutableMusicEvent>>  toOpenVoicing(LinkedList<LinkedList<MutableMusicEvent>> section){
         LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();
         for(int i=0; i<section.size(); i++){
           balladSection.add(section.get(i));
          }

            if(balladSection.size() % 3 == 0){
              for(int i=0; i<balladSection.size(); i++){
                if(i % 3 == 1){
                  if(balladSection.get(i).getLast() instanceof MutableMusicEvent){
                    MutableNote note = (MutableNote)balladSection.get(i).getLast();
                    note.setNoteNum(balladSection.get(i).getLast().notenum()+oct);
                    //System.out.println(balladSection.get(i).get(j).notenum());
                  }
                }
              }
            }


            if(balladSection.size() % 4 == 0){
              for(int i=0; i<balladSection.size(); i++){
                if(i % 4 == 1){
                  if(balladSection.get(i).getLast() instanceof MutableMusicEvent){
                    MutableNote note = (MutableNote)balladSection.get(i).getLast();
                    note.setNoteNum(balladSection.get(i).getLast().notenum()+oct);
                    //System.out.println(balladSection.get(i).get(j).notenum());
                  }
                }
              }
            }

         if(balladSection.size() % 3 == 0){
            for(int i=0; i<balladSection.size(); i++){
              if(i%3 == 2){
                int change = balladSection.get(i).getLast().notenum();
                if(balladSection.get(i).getLast() instanceof MutableMusicEvent){
                  MutableNote note = (MutableNote)balladSection.get(i).getLast();
                  note.setNoteNum(balladSection.get(i-1).getLast().notenum());
                  //System.out.println(balladSection.get(i).get(j).notenum());
                }
                if(balladSection.get(i-1).getLast() instanceof MutableMusicEvent){
                  MutableNote note = (MutableNote)balladSection.get(i-1).getLast();
                  note.setNoteNum(change);
                  //System.out.println(balladSection.get(i).get(j).notenum());
                }

              }

            }

          }

          if(balladSection.size() % 4 == 0){
            for(int i=0; i<balladSection.size(); i++){
              if(i%4 == 3){
                int change = balladSection.get(i).getLast().notenum();
                if(balladSection.get(i).getLast() instanceof MutableMusicEvent){
                  MutableNote note = (MutableNote)balladSection.get(i).getLast();
                  note.setNoteNum(balladSection.get(i-2).getLast().notenum());
                  //System.out.println(balladSection.get(i).get(j).notenum());
                }
                if(balladSection.get(i-2).getLast() instanceof MutableMusicEvent){
                  MutableNote note = (MutableNote)balladSection.get(i-2).getLast();
                  note.setNoteNum(change);
                  //System.out.println(balladSection.get(i).get(j).notenum());
                }

                 change = balladSection.get(i-1).getLast().notenum();
                if(balladSection.get(i-1).getLast() instanceof MutableMusicEvent){
                  MutableNote note = (MutableNote)balladSection.get(i-1).getLast();
                  note.setNoteNum(balladSection.get(i-2).getLast().notenum());
                  //System.out.println(balladSection.get(i).get(j).notenum());
                }
                if(balladSection.get(i-2).getLast() instanceof MutableMusicEvent){
                  MutableNote note = (MutableNote)balladSection.get(i-2).getLast();
                  note.setNoteNum(change);
                  //System.out.println(balladSection.get(i).get(j).notenum());
                }

              }

            }

          }



            return balladSection;

 	  }

 	   //クローズボイシング化(和音)
      public LinkedList<LinkedList<MutableMusicEvent>>  toCloseVoicingForChord(LinkedList<LinkedList<MutableMusicEvent>> section){
        LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();


        for(int i=0; i<section.size(); i++){
          balladSection.add(section.get(i));
         }


         for(int i=0; i<section.size(); i++){
           for(int j=1; j<section.get(i).size(); j++){
            for(int n = balladSection.get(i).get(0).notenum(); n<balladSection.get(i).getLast().notenum(); n++){
              if(n%12 == balladSection.get(i).get(j).notenum() % 12){
                if(balladSection.get(i).get(j) instanceof MutableMusicEvent){
                  MutableNote note = (MutableNote)balladSection.get(i).get(j);
                  note.setNoteNum(n);
                  //System.out.println(balladSection.get(i).get(j).notenum());
                }
              }
            }
           }
          }

          for(int i=0; i<balladSection.size(); i++){
        //   System.out.println(balladSection.get(i));
         }
        // System.out.println("");



            return balladSection;

 	  }


    //クローズボイシング化(分散和音)
     public LinkedList<LinkedList<MutableMusicEvent>>  toCloseVoicingForBroken(LinkedList<LinkedList<MutableMusicEvent>> section){
       LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();


       for(int i=0; i<section.size(); i++){
         balladSection.add(section.get(i));
        }


        for(int i=1; i<balladSection.size(); i++){
           for(int n = balladSection.get(0).getLast().notenum(); n<balladSection.getLast().getLast().notenum(); n++){
             if(n%12 == balladSection.get(i).getLast().notenum() % 12){
               if(balladSection.get(i).getLast() instanceof MutableMusicEvent){
                 MutableNote note = (MutableNote)balladSection.get(i).getLast();
                 note.setNoteNum(n);
                 //System.out.println(balladSection.get(i).get(j).notenum());
               }
             }
           }
         }


        if(balladSection.size() % 3 == 0){
          for(int i=0; i<balladSection.size(); i++){
            if(i%3 == 2){
              int change = balladSection.get(i).getLast().notenum();
              if(balladSection.get(i).getLast() instanceof MutableMusicEvent){
                MutableNote note = (MutableNote)balladSection.get(i).getLast();
                note.setNoteNum(balladSection.get(i-1).getLast().notenum());
                //System.out.println(balladSection.get(i).get(j).notenum());
              }
              if(balladSection.get(i-1).getLast() instanceof MutableMusicEvent){
                MutableNote note = (MutableNote)balladSection.get(i-1).getLast();
                note.setNoteNum(change);
                //System.out.println(balladSection.get(i).get(j).notenum());
              }

            }

          }

        }



         for(int i=0; i<balladSection.size(); i++){
          //System.out.println(balladSection.get(i));
        }
        //System.out.println("");



           return balladSection;

    }
 	    //最後の小節
      public LinkedList<LinkedList<MutableMusicEvent>>  lastSection(LinkedList<LinkedList<MutableMusicEvent>> section){
          LinkedList<LinkedList<MutableMusicEvent>> balladSection = new LinkedList();

          SCCDataSet optionsScc = new SCCDataSet(sccdataset.getDivision());
          SCCDataSet.Part optionsPart = optionsScc.addPart(1,1);

            optionsPart.addNoteElement(section.get(0).get(0).onset(),section.get(0).get(0).onset()+per8,section.get(0).get(0).notenum(),100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8,section.get(0).get(0).onset()+per8*2,section.get(0).get(0).notenum()+7,100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*2,section.get(0).get(0).onset()+per8*3,section.get(0).get(0).notenum()+12,100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*3,section.get(0).get(0).onset()+per8*4,section.get(0).get(0).notenum()+14,100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*4,section.get(0).get(0).onset()+per8*5,section.get(0).get(0).notenum()+16,100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*5,section.get(0).get(0).onset()+per8*6,section.get(0).get(0).notenum()+19,100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*6,section.get(0).get(0).onset()+per8*7,section.get(0).get(0).notenum()+24,100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*7,section.get(0).get(0).onset()+per8*8,section.get(0).get(0).notenum()+26,100,100);

            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*8,section.get(0).get(0).onset()+per8*16,section.get(0).get(0).notenum()+24,100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*8,section.get(0).get(0).onset()+per8*16,section.get(0).get(0).notenum()+26,100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*8,section.get(0).get(0).onset()+per8*16,section.get(0).get(0).notenum()+26,100,100);
            optionsPart.addNoteElement(section.get(0).get(0).onset()+per8*8,section.get(0).get(0).onset()+per8*16,section.get(0).get(0).notenum()+31,100,100);


          SCCDataSet.Part part = optionsScc.getPart(0);
          MutableMusicEvent[] note = part.getNoteOnlyList();
          LinkedList<MutableMusicEvent> list = new LinkedList();

         for(int i=0; i<note.length; i++){
            list.add(note[i]);
          }
          balladSection.add(list);

            return balladSection;

 	  }

}
