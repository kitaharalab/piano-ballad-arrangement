import jp.crestmuse.cmx.filewrappers.*;
import jp.crestmuse.cmx.processing.CMXController;
import jp.crestmuse.cmx.elements.*;
import jp.crestmuse.cmx.sound.*;
import javax.sound.midi.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;


class MyMutableNote extends MutableNote {
  MyMutableNote(MutableNote n, int ticksPerBeat) {
    super(n.onset(), n.offset(), n.notenum(), n.velocity(),
          n.offVelocity(), ticksPerBeat);
  }
}

public class PianoBallad extends JFrame implements ChangeListener{

static MIDIXMLWrapper midi;
static SMFPlayer player1;
static SMFPlayer player2;
static JSlider[] slider;
static JLabel[] aLabel;
static ResourceBundle rb;


  public static void main(String[] args){
       rb = ResourceBundle.getBundle("app");
       CMXController cmx = CMXController.getInstance();
         midi = cmx.readSMFAsMIDIXML(args[0]);
         try {
          player1 = new SMFPlayer();
          player1.readSMF(args[0]);

          PianoBallad frame = new PianoBallad("PianoBallad");
          frame.setVisible(true);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          }catch(Exception e){
          e.printStackTrace();
        }

  }

  PianoBallad(String title){
      try{
           SCCXMLWrapper scc =  midi.toSCCXML();
           SCCDataSet sccdataset = scc.toDataSet().clone();
           SCCDataSet.Part[] partlist = sccdataset.getPartList();
           MutableMusicEvent[]  notelist0 = partlist[0].getNoteOnlyList().clone();  //右手
           MutableMusicEvent[]  notelist1 = partlist[1].getNoteOnlyList().clone();  //左手



        //小節和音リスト
        //左手
        LinkedList<LinkedList<LinkedList<MutableMusicEvent>>> sectionList1 = new LinkedList();
         for(int i = 0; i<notelist1.length; i++){
           if((i==0) || (notelist1[i].offset() > sectionList1.size()*1920)){
               LinkedList<LinkedList<MutableMusicEvent>> NSList = new LinkedList();
               LinkedList<MutableMusicEvent> SEQList = new LinkedList();
               SEQList.add(notelist1[i]);
               NSList.add(SEQList);
               sectionList1.add(NSList);
           }else if(notelist1[i].offset() <= sectionList1.size()*1920){
               if(notelist1[i].onset() != notelist1[i-1].onset()){
                 LinkedList<MutableMusicEvent> SEQList = new LinkedList();
                 SEQList.add(notelist1[i]);
                 sectionList1.getLast().add(SEQList);
            }else{
               sectionList1.getLast().getLast().add(notelist1[i]);
            }
          }
       }

      //右手
      LinkedList<LinkedList<LinkedList<MutableMusicEvent>>> sectionList0 = new LinkedList();
       for(int i = 0; i<notelist0.length; i++){
           if((i==0) || (notelist0[i].offset() > sectionList0.size()*1920)){
               LinkedList<LinkedList<MutableMusicEvent>> NSList = new LinkedList();
               LinkedList<MutableMusicEvent> SEQList = new LinkedList();
               SEQList.add(notelist0[i]);
               NSList.add(SEQList);
               sectionList0.add(NSList);
           }else if(notelist0[i].offset() <= sectionList0.size()*1920){
               if(notelist0[i].onset() != notelist0[i-1].onset()){
                 LinkedList<MutableMusicEvent> SEQList = new LinkedList();
                 SEQList.add(notelist0[i]);
                 sectionList0.getLast().add(SEQList);
            }else{
               sectionList0.getLast().getLast().add(notelist0[i]);
            }
          }
        }




      setTitle(title);
      setSize(1070, 620);
      setLocationRelativeTo(null);




       JPanel p = new JPanel();
       p.setBackground(Color.black);
       p.setLayout(null);


       JPanel editPanel = new JPanel();
       editPanel.setPreferredSize(new Dimension(60*sectionList1.size(), 20*25));
  	   editPanel.setLayout(null);

         //制約を格納した配列
        //  String[] Rules = {"4分音符分散","8分音符分散", "2分音符分散", "全音符和音", "2分音符和音", "16分→4分", "16分→2分", "8分→4分", "8分→2分", "オクターブ","分散+和音", "5度音追加", "オクターブ音追加","add9","オープンボイシング", "クローズ(和音)", "クローズ(分散)", "和音簡略", "最後の小節適用"};
         String[] Rules = {rb.getString("broken.chord.quarter"),
         rb.getString("broken.chord.eighth"), 
         rb.getString("broken.chord.half"), 
         rb.getString("chord.whole"), 
         rb.getString("chord.half"), 
         rb.getString("sixteenth.to.quarter"), 
         rb.getString("sixteenth.to.half"), 
         rb.getString("eighth.to.quarter"), 
         rb.getString("eighth.to.half"), 
         rb.getString("octave"),
         rb.getString("broken.and.simultaneous"), 
         rb.getString("add.fifth"), 
         rb.getString("add.octave"),
         rb.getString("add9"),
         rb.getString("open.voicing.chord"), 
         rb.getString("close.voicing.chord"), 
         rb.getString("close.voicing.chord.broken"), 
         rb.getString("simplify.chord"), 
         rb.getString("apply.last.bar")};

	    Color c = new Color(239, 64, 80);
	    Random r = new Random();


	      //ルールの表示
          JLabel rules[] = new JLabel[26];
          for(int i=0; i<Rules.length; i++){
             rules[i] = new JLabel(Rules[i]);
             rules[i].setToolTipText(Rules[i]);
             rules[i].setBounds(20, 50+i*25, 120, 25);
            // else rules[i].setBounds(10+120, 80+(i%13)*30, 120, 30);
             rules[i].setBackground(new Color(255, 192, 203));
             rules[i].setBorder(new LineBorder(c, 1));
             rules[i].setOpaque(true);
             p.add(rules[i]);
          }





      //小節番号の表示
      JLabel sectionNums[] = new JLabel[sectionList1.size()];
      for(int i=0; i<sectionList1.size(); i++){
         sectionNums[i] = new JLabel(Integer.toString(i+1));
         sectionNums[i].setBounds(i*60, 0, 60, 25);
         sectionNums[i].setBackground(new Color(255, 192, 203));
         sectionNums[i].setBorder(new LineBorder(c, 1));
         sectionNums[i].setOpaque(true);
         editPanel.add(sectionNums[i]);
      }

     //採用する制約の編集
      JButton editArea[][] = new JButton[sectionList1.size()][Rules.length];
      for(int i=0; i<sectionList1.size(); i++){
          for(int j=0; j<Rules.length; j++){
            editArea[i][j] = new JButton();
            editArea[i][j].setBounds(i*60, 25+j*25, 60, 25);
           // else editArea[i][j].setBounds(i*100+50, 30+(j%13)*30, 50, 30);
            editArea[i][j].setOpaque(true);
            editArea[i][j].setBackground(new Color(255,255,255));
            editArea[i][j].setBorder(new LineBorder(c, 1));
            editPanel.add(editArea[i][j]);
         }
      }




            //制約の採用の有無の状態を格納した配列
           int RuleSelect[][] = new int[sectionList1.size()][25];
           //採用したいエリアを押すと色が変わる
            ActionListener event = new ActionListener(){
             public void actionPerformed(ActionEvent e) {
                  for(int i=0; i<sectionList1.size(); i++){
                      for(int j=0; j<Rules.length; j++){
                           if(e.getSource() == editArea[i][j]){
                               if(RuleSelect[i][j] == 0){
                                    RuleSelect[i][j] = j+1;
                                    editArea[i][j].setBackground(new Color(234, 145, 152));
                                  //  System.out.println(RuleSelect[i][j]);
                               }else{
                                    RuleSelect[i][j] = 0;
                                    editArea[i][j].setBackground(new Color(255, 255, 255));
                               }
                           }
                      }
                    }
                 }
            };

            //編集エリアを押すと関数が実行される
           for(int i = 0; i < sectionList1.size(); i++) {
               for(int j=0; j<Rules.length; j++){
                  editArea[i][j].addActionListener(event);
               }
           }




             ActionListener clearSelect = new ActionListener(){
               public void actionPerformed(ActionEvent e) {
                 for(int i=0; i<sectionList1.size(); i++){
                   for(int j=0; j<Rules.length; j++){
                      RuleSelect[i][j] = 0;
                      editArea[i][j].setBackground(new Color(255, 255, 255));
                   }
                  }
               }
            };


           JButton clear = new JButton(rb.getString("clear.edit"));
           clear.setBounds(930, 30, 120, 60);
           clear.setOpaque(true);
           clear.addActionListener(clearSelect);
           p.add(clear);

           JButton auto = new JButton(rb.getString("show.automatic.arrangement.form"));
           auto.setToolTipText(rb.getString("show.automatic.arrangement.form"));
           auto.setBounds(930, 120, 120, 60);
           auto.setOpaque(true);
           p.add(auto);


           JButton randomButton = new JButton(rb.getString("randamize"));
           randomButton.setBounds(995, 120, 55, 60);
           randomButton.setOpaque(true);
           //p.add(randomButton);


           JButton play1 = new JButton(rb.getString("play.original"));
           play1.setBounds(930, 200, 120, 60);
           play1.setOpaque(true);
           p.add(play1);

           JButton play2 = new JButton(rb.getString("play.arranged"));
           play2.setBounds(930, 270, 120, 60);
           play2.setOpaque(true);
           play2.setVisible(false);
           p.add(play2);

           JButton stop = new JButton(rb.getString("stop.playing"));
           stop.setBounds(930, 340, 120, 60);
           stop.setOpaque(true);
           p.add(stop);

          JButton start = new JButton(rb.getString("generate"));
          start.setBounds(930, 480, 120, 60);
          start.setOpaque(true);
          p.add(start);





         int[] SPS = new int[6];   //StartPartSection;
         int[] EPS = new int[6];   //EndPartSection;

              //パート入力フォーム
               JFrame partFrame = new JFrame(rb.getString("automatic.arrangement.settings"));
               partFrame.setBounds(300,100,600,420);
               partFrame.setVisible(false);

               JPanel setPanel = new JPanel();
               //setPanel.setBackground(Color.black);
               setPanel.setLayout(null);
               setPanel.setVisible(true);


               String[] song = {rb.getString("intro"), 
               rb.getString("verse1"), 
               rb.getString("verse2"), 
               rb.getString("bridge"), 
               rb.getString("chorus"), 
               rb.getString("ending")};
               
               JLabel sLabel = new JLabel(rb.getString("randomization"));
               sLabel.setBounds(400,20,100,30);
               setPanel.add(sLabel);
               
               JLabel pLabel = new JLabel(rb.getString("each.section.setting"));
               pLabel.setBounds(50,20,200,30);
               setPanel.add(pLabel);

               JLabel[] partLabel = new JLabel[song.length];
               JTextField[] startP = new JTextField[song.length];
               JTextField[] endP = new JTextField[song.length];
               slider = new JSlider[song.length];
               aLabel = new JLabel[song.length];

               for(int i=0; i<partLabel.length; i++){
                   //System.out.println(SPS[i] + " " + EPS[i]);

                    partLabel[i] = new JLabel(song[i]);
                    partLabel[i].setBounds(30,50+i*50,70,30);
                    partLabel[i].setOpaque(true);
                    setPanel.add(partLabel[i]);

                    startP[i] = new JTextField(Integer.toString(SPS[i]));
                    startP[i].setBounds(100,50+i*50,40,30);
                    startP[i].setBorder(new LineBorder(c, 1));
                    startP[i].setOpaque(true);
                    setPanel.add(startP[i]);

                    JLabel label1 = new JLabel(rb.getString("bar") + "  〜  ");
                    label1.setBounds(150,50+i*50,70,30);
                    label1.setOpaque(true);
                    setPanel.add(label1);

                    endP[i] = new JTextField(Integer.toString(SPS[i]));
                    endP[i].setBounds(220,50+i*50,40,30);
                    endP[i].setBorder(new LineBorder(c, 1));
                    endP[i].setOpaque(true);
                    setPanel.add(endP[i]);

                    JLabel label2 = new JLabel(rb.getString("bar"));
                    label2.setBounds(280,50+i*50,50,30);
                    label2.setOpaque(true);
                    setPanel.add(label2);
                    
                    slider[i] = new JSlider(0,100);
                    
                    slider[i].setBounds(330,50+i*50,200,30);
                    slider[i].setValue(100);
                    slider[i].addChangeListener(this);
                                      
                    
                    setPanel.add(slider[i]);  
                    
                    aLabel[i] = new JLabel(Integer.toString(slider[i].getValue()));
                    aLabel[i].setBounds(540,50+i*50,40,30);
                    setPanel.add(aLabel[i]);  

               }
               
           
               

            auto.addActionListener(
             new ActionListener(){
              public void actionPerformed(ActionEvent event){
                partFrame.setVisible(true);  //パート入力フォームを表示する

               JButton arrangeButton = new JButton(rb.getString("submit"));
               arrangeButton.setBounds(240,350, 120, 30);
               arrangeButton.setOpaque(true);
               arrangeButton.setBackground(new Color(255, 192, 203));
              // arrange.setBorder(new LineBorder(c, 1));
               setPanel.add(arrangeButton);

                  arrangeButton.addActionListener(
                   new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                	  int[] partNums = new int[sectionNums.length];
                	  for(int i=0; i<6; i++){
                	      SPS[i] = Integer.parseInt(startP[i].getText());
                	      EPS[i] = Integer.parseInt(endP[i].getText());
                	    // System.out.println(SPS[i] + " " + EPS[i]);

                	     if((0<SPS[i] && SPS[i]<=partNums.length) && (0<EPS[i] && EPS[i]<=partNums.length) ){
                	        for(int j=SPS[i]-1; j<=EPS[i]-1; j++){
                	            partNums[j] = i;
                	            //System.out.pringln(partNums[j]);
                	        }
                	      }
                	  }

                	  
                	  int[] a = new int[song.length];
                	  for(int i=0; i<song.length; i++){
                	      a[i] = slider[i].getValue();   //ランダム度
                	    // System.out.println(a[i]);
                	   }
                	   
                	  
                	  
                	  for(int i=0; i<partNums.length; i++){ 
                	   Arrange arrange = new Arrange();
                	   int[] select = new int[20];   //制約の採用の状態を格納
                	   int num;    //乱数  0-100
                	   boolean random = false;
                	  
                	  
                	    num = r.nextInt(100);
                	    //System.out.println(num);
                	    for(int j=0; j<a.length; j++){
                	      if(partNums[i] == j){
                	         if(num < a[j])random = true;
                	      }
                	    }
            	      
            	        if(random == true){
            	           select = arrange.arrangeR();
            	        }else{
            	          if(partNums[i] == 0)  select = arrange.arrangeI();
            	          else if(partNums[i] == 1)select = arrange.arrangeA();
            	          else if(partNums[i] == 2)select = arrange.arrangeB();
            	          else if(partNums[i] == 3)select = arrange.arrangeC();
            	          else if(partNums[i] == 4)select = arrange.arrangeS();
            	          else if(partNums[i] == 5)select = arrange.arrangeO();
            	        }
                	      
                	   
                	    
                	    
                	    for(int j=0; j<Rules.length; j++){
                          RuleSelect[i][j] = select[j];
                          if(RuleSelect[i][j] != 0) editArea[i][j].setBackground(new Color(234, 145, 152));
                          else editArea[i][j].setBackground(new Color(255, 255, 255));
                        }
                	      
                	      
	    
                      }
                	   
                	 
                      partFrame.setVisible(false);  //パート入力フォームを閉じる

                   }
                  }
                );

               partFrame.getContentPane().add(setPanel, BorderLayout.CENTER);
            }
          }
        );
        
        
          randomButton.addActionListener(
           new ActionListener(){
            public void actionPerformed(ActionEvent event){
        	  int[] chordA = new int[sectionNums.length];
              int[] rhythmA = new int[sectionNums.length];
              int[] octaveA = new int[sectionNums.length];
              int[] chord5A = new int[sectionNums.length];
              int[] chord8A = new int[sectionNums.length];
              int[] add9OA = new int[sectionNums.length];
              int[] add9CA = new int[sectionNums.length];
        	  int[] BaCA = new int[sectionNums.length];
        	  int[] openA = new int[sectionNums.length];
        	  int[] closeA = new int[sectionNums.length];
        	  int[] removeA = new int[sectionNums.length];
        	  
        	 
              for(int i=0 ; i<sectionNums.length ; i++) {
                chordA[i] = r.nextInt(6);
                rhythmA[i] = r.nextInt(4)+6;
               
                octaveA[i] = r.nextInt(2);
                if(octaveA[i] == 1)octaveA[i] = 10;
              
                chord5A[i] = r.nextInt(2);
                if(chord5A[i] == 1)chord5A[i] = 12;
               
                chord8A[i] = r.nextInt(2);
                if(chord8A[i] == 1)chord8A[i] = 13;
               
                removeA[i] = r.nextInt(2);
                if(removeA[i] == 1)removeA[i] = 19;
                
                BaCA[i] = r.nextInt(2);
                if(BaCA[i] == 1)BaCA[i] = 11;
               
                openA[i] = r.nextInt(2);
                if(openA[i] == 1)openA[i] = 16;
               
                closeA[i] = r.nextInt(2);
                if(closeA[i] == 1)closeA[i] = 17;

                add9OA[i] = r.nextInt(2);
                if(add9CA[i] == 1)add9CA[i] = 15;
                
                add9CA[i] = r.nextInt(2);
                if(add9OA[i] == 1)add9OA[i] = 14;
                
                add9CA[i] = r.nextInt(2);
                if(add9CA[i] == 1)add9CA[i] = 15;
             
                for(int j=0; j<Rules.length; j++){
                  RuleSelect[i][j] = 0;
                  editArea[i][j].setBackground(new Color(255, 255, 255));
                 
                  if(j == chordA[i]-1){
                    RuleSelect[i][j] = chordA[i];
                    editArea[i][j].setBackground(new Color(234, 145, 152));
                  }else if(j == rhythmA[i]-1){
                    RuleSelect[i][j] = rhythmA[i];
                    editArea[i][j].setBackground(new Color(234, 145, 152));
                  }else if(j == octaveA[i]-1){
                    RuleSelect[i][j] = octaveA[i];
                    editArea[i][j].setBackground(new Color(234, 145, 152));
                  }

                 if(1<=chordA[i] && chordA[i]<=3){
                     //  System.out.println(i + ":分散");
                     if(j == BaCA[i]-1){
                       RuleSelect[i][j] = BaCA[i];
                       editArea[i][j].setBackground(new Color(234, 145, 152));
                     }else if(j == openA[i]-1){
                       RuleSelect[i][j] = openA[i];
                       editArea[i][j].setBackground(new Color(234, 145, 152));
                     }else if(j == chord8A[i]-1){
                       RuleSelect[i][j] = chord8A[i];
                       editArea[i][j].setBackground(new Color(234, 145, 152));
                     }
                   }
                   
                   
                  if(openA[i] == 16){
                     if(j == add9OA[i]-1){
                       RuleSelect[i][j] = add9OA[i];
                       editArea[i][j].setBackground(new Color(234, 145, 152));
                     }
                  }

                 if(closeA[i] == 17){
                     if(j == add9CA[i]-1){
                       RuleSelect[i][j] = add9CA[i];
                       editArea[i][j].setBackground(new Color(234, 145, 152));
                     }
                  }

                    if(4<=chordA[i] && chordA[i]<=5){
                        // System.out.println(i + ":和音");
                       if(j == closeA[i]-1){
                          RuleSelect[i][j] = closeA[i];
                          editArea[i][j].setBackground(new Color(234, 145, 152));
                       }else if(j == removeA[i]-1){
                          RuleSelect[i][j] = removeA[i];
                          editArea[i][j].setBackground(new Color(234, 145, 152));
                       }else if(j == chord5A[i]-1){
                          RuleSelect[i][j] = chord5A[i];
                        editArea[i][j].setBackground(new Color(234, 145, 152));
                      }  
                   }
                }
            }

           }
          }
        );

    
          //生成ボタンを押す
           start.addActionListener(
              new ActionListener(){
                public void actionPerformed(ActionEvent event){
                  try {


                     //バラード調の小節生成
                     LinkedList<LinkedList<LinkedList<MutableMusicEvent>>> newSectionList0 = new LinkedList();  //右手
                     LinkedList<LinkedList<LinkedList<MutableMusicEvent>>> newSectionList1 = new LinkedList();  //左手

                       // 新しいパート生成
                     SCCDataSet balladScc = new SCCDataSet(sccdataset.getDivision());
                     SCCDataSet.Part balladPart0 = balladScc.addPart(1,1);
                     SCCDataSet.Part balladPart1 = balladScc.addPart(2,2);



               for(int i=0; i<RuleSelect.length; i++){

                  LinkedList<LinkedList<MutableMusicEvent>> toBallad0 = new LinkedList();  //右手
                  LinkedList<LinkedList<MutableMusicEvent>> toBallad1 = new LinkedList();  //左手

                 /*
                   ReturnNote returnnote0 = new ReturnNote(sectionList0.get(i));  //アレンジなし
                   ReturnNote returnnote1 = new ReturnNote(sectionList1.get(i));  //アレンジなし
                   toBallad0 = returnnote0.noChange();
                   toBallad1 = returnnote1.noChange();
                  */

                  for (LinkedList<MutableMusicEvent> l1 : sectionList0.get(i)) {
                    LinkedList<MutableMusicEvent> l2 = new LinkedList<MutableMusicEvent>();
                    for (MutableMusicEvent me : l1) {
                      if (me instanceof MutableNote) {
                        l2.add(new MyMutableNote((MutableNote)me, sccdataset.getDivision()));
                      } else {
                        l2.add(me);
                      }
                    }
                    toBallad0.add(l2);
                  }

                  for (LinkedList<MutableMusicEvent> l1 : sectionList1.get(i)) {
                    LinkedList<MutableMusicEvent> l2 = new LinkedList<MutableMusicEvent>();
                    for (MutableMusicEvent me : l1) {
                      if (me instanceof MutableNote) {
                        l2.add(new MyMutableNote((MutableNote)me, sccdataset.getDivision()));
                      } else {
                        l2.add(me);
                      }
                    }
                    toBallad1.add(l2);
                  }

//                   toBallad0 = (LinkedList) sectionList0.get(i).clone();
//                   toBallad1 = (LinkedList) sectionList1.get(i).clone();

                  //System.out.println("変更前のsectionList");
                  //System.out.println(sectionList1.get(i).get(0));


                  ToBrokenChord tobrokenchord = new ToBrokenChord();  //分散和音化
                  ToChord tochord = new ToChord();     //和音化
                  ChangeRhythm changerhythm = new ChangeRhythm();      //リズム簡略
                  Options options = new Options(sccdataset);      //その他の制約

                 // System.out.println(RuleSelect[i][20]);
                  //和音系
                    for(int j=0; j<25; j++){
                     switch(RuleSelect[i][j]){
                        case 4 :  //全音符和音化
                            toBallad1 = tochord.ToChordPer1(toBallad1);
                            break;
                        case 5 :  //2分音符和音化
                            toBallad1 = tochord.ToChordPer2(toBallad1);
                            break;
                      }
                   }


                   //リズム簡略系
                    for(int j=0; j<25; j++){
                     switch(RuleSelect[i][j]){
                        case 6 :  //16分音符の4分音符化
                            toBallad1 = changerhythm.sixteenth2Per4(toBallad1);
                            break;
                        case 7 :  //16分音符の2分音符化
                            toBallad1 = changerhythm.sixteenth2Per2(toBallad1);
                            break;
                        case 8 :  //8分音符の4分音符化
                            toBallad1 = changerhythm.eighth2Per4(toBallad1);
                            break;
                        case 9 :  //8分音符の2分音符化
                            toBallad1 = changerhythm.eighth2Per2(toBallad1);
                            break;
                      }
                    }


                   //音の追加、削除
                    for(int j=0; j<25; j++){
                     switch(RuleSelect[i][j]){
                        case 12 :  //5度和音追加
                            toBallad1 = options.add5Chord(toBallad1);
                            break;
                        case 13 :  //オクターブ度和音追加
                            toBallad1 = options.add3Chord(toBallad1);
                            break;
                        case 18 : //和音の音数を１減らす
                            toBallad1 = options.removeNote(toBallad1);
                            break;

                      }
                    }




                  //分散和音系
                  for(int j=0; j<25; j++){
                     switch(RuleSelect[i][j]){
                        case 1 :  //4分分散和音化
                            toBallad1 = tobrokenchord.brokenPer4(toBallad1);
                            break;
                        case 2 :  //８分音符分散和音化
                            toBallad1 = tobrokenchord.brokenPer8(toBallad1);
                            break;
                        case 3 :  //2分分散和音化
                            toBallad1 = tobrokenchord.second2BrokenPer2(toBallad1);
                            break;
                      }
                 }




                  //オプション
                    for(int j=0; j<25; j++){
                     switch(RuleSelect[i][j]){
                        case 10 :  //オクタ-ーブ化
                            toBallad0 = options.toOctave(toBallad0);
                            toBallad1 = options.toOctave(toBallad1);
                            break;
                        case 11 :  //分散和音に和音取り入れ（最後の音符）
                            toBallad1 = options.brokenChordAndChord(toBallad1);
                            break;
                        case 15 :  //オープンボイシング化
                            toBallad1 = options.toOpenVoicing(toBallad1);
                            break;
                        case 16 : // クローズボイシング化（和音）
                            toBallad1 = options.toCloseVoicingForChord(toBallad1);
                            break;
                        case 17 : // クローズボイシング化（分散和音）
                            toBallad1 = options.toCloseVoicingForBroken(toBallad1);
                            break;
                        case 19 : //最後の小節で実行
                            toBallad1 = options.lastSection(toBallad1);
                            System.out.println("選択されてる");
                            break;
                        }
                   }



                   //add9
                  for(int j=0; j<25; j++){
                     switch(RuleSelect[i][j]){
                        /* case 14 :  //add9化(オープンボイシング)
                            toBallad1 = options.toAdd9ForOpen(toBallad1);
                            break; */
                         case 14 :  //add9化(クローズボイシング)
                            toBallad1 = options.toAdd9ForClose(toBallad1);
                            break;

                     }
                  }
                  
                  
                 System.out.println(i+1);
                 for(int j=0; j<Rules.length; j++){
                     System.out.printf(RuleSelect[i][j] + " ");
                  }
                   System.out.println("");



              //   System.out.println("変更後のsectionList");
               //  System.out.println(sectionList1.get(i).get(0));




                 newSectionList0.add(toBallad0) ;
                 newSectionList1.add(toBallad1) ;
              }

               //右手パート
                      for(int i=0; i<newSectionList0.size(); i++){
                                 for(int j=0; j<newSectionList0.get(i).size(); j++){
                                      //書き出し
                                      //System.out.println(newSectionList0.get(i).get(j));

                                      for(int k=0; k<newSectionList0.get(i).get(j).size(); k++){
                                          //パートに追加
                                          balladPart0.addNoteElement(newSectionList0.get(i).get(j).get(k).onset(),newSectionList0.get(i).get(j).get(k).offset(),newSectionList0.get(i).get(j).get(k).notenum(),100,100);
                                      }
                                   }
                                     // System.out.println("");

                            }


                        //左手パート
                         for(int i=0; i<newSectionList1.size(); i++){
                                 for(int j=0; j<newSectionList1.get(i).size(); j++){
                                      //書き出し
                                      //System.out.println(newSectionList1.get(i).get(j));

                                      for(int k=0; k<newSectionList1.get(i).get(j).size(); k++){
                                          //パートに追加
                                          balladPart1.addNoteElement(newSectionList1.get(i).get(j).get(k).onset(),newSectionList1.get(i).get(j).get(k).offset(),newSectionList1.get(i).get(j).get(k).notenum(),100,100);
                                      }
                                   }
                                     // System.out.println("");

                            }

                            balladScc.toMIDIXML().writefileAsSMF("output.mid");
                            play2.setVisible(true);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

               }
              }
            );


          play1.addActionListener(
           new ActionListener(){
            public void actionPerformed(ActionEvent event){
        	try {
        	    player1.back();
        	    player1.play();
        	}
        	catch (Exception e) {
        	    System.err.println("File I/O Error");
        	}

           }
          }
        );



           play2.addActionListener(
           new ActionListener(){
            public void actionPerformed(ActionEvent event){
        	try {
      	        player2 = new SMFPlayer();  //変更後（バラード）
                player2.readSMF("output.mid");
                player2.setTempoInBPM(player1.getTempoInBPM()*2/3);
                player2.back();
                player2.play();
        	}
        	catch (Exception e) {
        	    System.err.println("File I/O Error");
        	}

           }
          }
        );



        stop.addActionListener(
           new ActionListener(){
            public void actionPerformed(ActionEvent event){
        	try {
        	    player1.stop();
        	    player2.stop();
        	}
        	catch (Exception e) {
        	    System.err.println("File I/O Error");
        	}
           }
          }
        );



	   JScrollPane scrollpane = new JScrollPane(editPanel);
	   scrollpane.setBounds(20+120, 23, 770, 20*25+20);
	   scrollpane.getVerticalScrollBar().setUnitIncrement(25);
       p.add(scrollpane);

       getContentPane().add(p, BorderLayout.CENTER);

      }catch(Exception e){
         e.printStackTrace();
      }

    }
   
      public void stateChanged(ChangeEvent e) {
         for(int i=0; i<6; i++){
           if(e.getSource() == slider[i]){
             aLabel[i].setText(Integer.toString(slider[i].getValue()));
          }
         }
       }
    
  
}
