import jp.crestmuse.cmx.filewrappers.*;

public class CMXTest2{
	public static void main(string[] args){
		try{
			SCCCMXWrapper scc = (SCCXMLWrapper)CMXFileWrapper.readfile(args[0]);
			scc.toMIDIXML().writefileAsSMF("output.mid");
		}cathc(){
		}
	}
}


<SCC division="480">
<header>
<meta time="0" name = "TEMPO" content = "100"
</header>
<paert ch="1" pn="1" serial="0" vol="100">
<note>0 480 60 64 64</note>
<note>480 960 62 64 64</note>


