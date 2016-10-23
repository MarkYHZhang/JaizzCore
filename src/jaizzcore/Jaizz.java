package jaizzcore;

import org.jfugue.player.Player;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class Jaizz {

    static String curDir = System.getProperty("user.dir");

    /**
     * args[0] = scale key
     * args[1] = instruments
     * args[2] = range[short medium long]
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception{

        String GPiano = "C3 Db3 D3 F3 G3 Bb3 C4 Db4 D4 F4 G4 Bb4 C5 Db5 D5 F5 G5 Bb5 C6 Db6 D6 F6 G6";
        String GClarinet = "E4 G4 A4 C5 D5 Eb5 E5 G5 A5 C6 D6 Eb6";
        String GTrumpet = "C4 D4 Eb4 E4 G4 A4 C5 D5 Eb5 E5 G5 A5 C6 D6 Eb6 E6 G6 A6";
        String GSax = "D4 E4 G4 A4 Bb4 B4 D5 E5 G5 A5 Bb5 B5 D6 E6 G6 A6 Bb6 B6";

        String APiano = "C3 D3 Eb3 E3 G3 A3 C4 D4 Eb4 E4 G4 A4 C5 D5 Eb5 E5 G5 A5 C6 D6 Eb6 E6 G6 A6";
        String AClarinet = "F#4 A4 B4 D5 E5 F5 F#5 A5 B5 D6 E6 F6";
        String ATrumpet = "B3 D4 E4 F4 F#4 A4 B4 D5 E5 F5 F#5 A5 B5 D6 E6 F6 F#6 A6 B6";
        String ASax = "C4 C#4 E4 F#4 A4 B4 C5 C#5 E5 F#5 A5 B5 C6 C#6 E6 F#6 A6 B6";

        String patternstr = "16\n" +
                "4\n" +
                "3 q,?,0 q,?,-1 q,?,-2 r,?,0\n" +
                "2 q,?,-4 q,?,-5 e,?,-6 e,?,-5 r,?,0\n" +
                "1 e,?,0 e,?,0 q,?,-1 q,?,-2 r,?,0\n" +
                "0 q,?,-4 e,?,-5 e,?,-6 q.,?,-6 re,?,0\n" +
                "2\n" +
                "1 q,?,0 q,?,-1 e,?,-4 e,?,-3 e,?,-2 e,?,1\n" +
                "0 w,?,1\n" +
                "2\n" +
                "1 q,?,0 e,?,1 e,?,0 e,?,1 e,?,2 r,?,0\n" +
                "0 q,?,1 e,?,0 e,?,1 h,?,1\n" +
                "2\n" +
                "1 q,?,0 e,?,1 e,?,0 e,?,1 e,?,2 r,?,0\n" +
                "0 e,?,-1 e,?,-1 e,?,-2 e,?,-3 h,?,-3\n" +
                "1\n" +
                "0 e,?,0 s,?,0 rs,?,0 e,?,1 s,?,1 rs,?,0 h,?,2\n" +
                "3\n" +
                "2 q,?,0 e,?,1 e,?,+2 h,?,2\n" +
                "1 q,?,2 q,?,1 q,?,0 q,?,-1\n" +
                "0 e,?,-3 s,?,-2 rs,?,0 s,?,-1 rs,?,0 e,?,2 h,?,2\n" +
                "4\n" +
                "3 e,?,0 e,?,0 r,?,0 e,?,-3 s,?,-3 rs,?,-3 e,?,-4 e,?,-3\n" +
                "2 q,?,-3 e,?,-3 e,?,-4 q,?,-3 q,?,-1\n" +
                "1 e,?,0 e,?,0 r,?,0 q,?,-2 r,?,0\n" +
                "0 q,?,-3 e,?,-2 e,?,-1 h,?,-1\n" +
                "3\n" +
                "2 q,?,0 e,?,-1 e,?,-1 e,?,-2 e,?,-2 e,?,-3 e,?,-3\n" +
                "1 q,?,-4 e,?,-2 e,?,-4 q,?,-2 s,?,0 rs,?,0 e,?,-1\n" +
                "0 h.,?,-1 r,?,0\n" +
                "4\n" +
                "3 e,?,0 s,?,0 rs,?,0 e,?,-1 e,?,-2 h,?,-2\n" +
                "2 q,?,-2 e,?,-1 e,?,0 h,?,0\n" +
                "1 q,?,1 e,?,2 e,?,1 e,?,2 e,?,1 r,?,0\n" +
                "0 e,?,1 e,?,0 e,?,1 e,?,0 q,?,-1 q,?,0\n" +
                "4\n" +
                "3 q,?,0 e,?,1 e,?,0 e,?,1 e,?,0 r,?,0\n" +
                "2 q,?,1 e,?,0 e,?,-2 e,?,-3 e,?,-4 e,?,-5 e,?,-4\n" +
                "1 h.,?,-4 e,?,-3 e,?,-4\n" +
                "0 h,?,-4 e,?,-1 e,?,-2 e,?,-3 e,?,-2\n" +
                "2\n" +
                "1 q,?,0 e,?,-1 e,?,-2 q,?,-1 s,?,1 rs,?,1 e,?,0\n" +
                "0 q,?,0 e,?,0 e,?,-1 e,?,-2 e,?,-1 e,?,0 e,?,2\n" +
                "2\n" +
                "1 e,?,0 e,?,-5 e,?,-4 e,?,-3 q,?,-1 e,?,-2 e,?,-3\n" +
                "0 h.,?,-3 r,?,0\n" +
                "6\n" +
                "5 q,?,0 e.,?,0 s,?,-1 h,?,-1\n" +
                "4 h.,?,-1 rs,?,0 s,?,-5 s,?,-4 s,?,-4\n" +
                "3 e.,?,0 s,?,-5 e.,?,0 s,?,1 h,?,1\n" +
                "2 h,?,1 rs,?,0 s,?,1 re,?,0 e.,?,1 s,?,-5\n" +
                "1 q,?,0 e.,?,0 s,?,-1 h,?,-1\n" +
                "0 q,?,0 e.,?,0 s,?,1 h,?,1, r,?,0\n" +
                "2\n" +
                "1 q,?,0 re,?,0 e,?,-4 e,?,-3 q,?,-2 e,?,-5\n" +
                "0 w,?,-4\n" +
                "2\n" +
                "1 q,?,0 re,?,0 e,?,-4 e,?,-3 q,?,-2 s,?,-5 s,?,-2\n" +
                "0 w,?,-4\n" +
                "2\n" +
                "1 q,?,0 e,?,2 e,?,0 e,?,1 e,?,0 e,?,-2 e,?,-5\n" +
                "0 q,?,-3 e,?,-5 e,?,-3 e,?,0 q.,?,2";

        Player p = new Player();

        Scanner sc = new Scanner(patternstr);
        List<String> patterns = new ArrayList<>();

        int cnt = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < cnt; i++) {
            int numLine = Integer.parseInt(sc.nextLine());
            for (int j = 0; j < numLine; j++) {
                patterns.add(sc.nextLine());
            }
        }

        HashMap<String, HashMap<String, String>> scales = new HashMap<>();

        HashMap<String, String>  GScale = new HashMap<>();
        GScale.put("piano",GPiano);
        GScale.put("clarinet",GClarinet);
        GScale.put("trumpet",GTrumpet);
        GScale.put("alto_sax",GSax);
        scales.put("G", GScale);

        HashMap<String, String> AScale = new HashMap<>();
        AScale.put("piano",APiano);
        AScale.put("clarinet",AClarinet);
        AScale.put("trumpet",ATrumpet);
        AScale.put("alto_sax",ASax);
        scales.put("A", AScale);

        String key = args[0];
        String instrument = args[1].toLowerCase();
        String[] scaleNotes = scales.get(String.valueOf(key.charAt(0))).get(instrument).split(" ");


        String lengthIn = args[2];
        int length=1;
        if (lengthIn.equalsIgnoreCase("short")) length=35;
        else if (lengthIn.equalsIgnoreCase("medium")) length=70;
        else if (lengthIn.equalsIgnoreCase("long")) length=150;

        String music = "", parsedMusic = "";
        for (int i = 0; i < length; i++) {
            int patternLoc = Utils.randomInt(0,patterns.size()-1);
            String[] pattern = patterns.get(patternLoc).split(" ");
            int randomNoteLoc = Utils.randomInt(0,scaleNotes.length-1);
            int firstNoteLoc = randomNoteLoc;
            int connectedBars = Integer.parseInt(pattern[0]);
            for (int j = 1; j < pattern.length; j++){
                String[] noteFormats = pattern[j].split(",");
                String noteLength = noteFormats[0];
                String tieStart = "", tieEnd = "";
                if (!noteFormats[1].equalsIgnoreCase("?")){
                    if (noteFormats[1].contains("-")) tieStart="-";
                    if (noteFormats[1].contains("+")) tieEnd="-";
                }
                int relativeLoc = 0;
                if (j!=1) relativeLoc = Integer.parseInt(noteFormats[2]);

                randomNoteLoc=Math.abs((firstNoteLoc+relativeLoc)%(scaleNotes.length-1));

                if (scaleNotes[randomNoteLoc].charAt(0)=='r'){
                    music += scaleNotes[randomNoteLoc];

                    String tmp = scaleNotes[randomNoteLoc];
                    if (tmp.length()==1) tmp+=4;
                    else tmp = tmp.replace("e","8").replace("s","16");
                    parsedMusic += tmp.toLowerCase() + " ";
                } else{
                    music += scaleNotes[randomNoteLoc] + tieStart + noteLength + tieEnd + " ";

                    String noteSig = noteLength;
                    String note = scaleNotes[randomNoteLoc];

                    String octStr = "";

                    int dif = Integer.parseInt(note.substring(note.length()-1,note.length()))-4;
                    if (dif>0){
                        for (int k = 0; k < dif; k++) {
                            octStr+="'";
                        }
                    }else if (dif<0){
                        for (int k = 0; k < Math.abs(dif); k++) {
                            octStr+=",";
                        }
                    }

                    noteSig = noteSig.replace("r","").replace("w","1").replace("h","2").replace("q","4").replace("e","8").replace("s","16");

                    note = note.substring(0,note.length()-1);

                    String output = note.replace("b", "es").replace("#", "is")+octStr+noteSig;
                    if (tieStart.equalsIgnoreCase("-")) output+="~";
                    parsedMusic+=output.toLowerCase()+" ";
                }


            }
            for (int j = 1; j <= connectedBars; j++) {
                String[] patternConnected = patterns.get(patternLoc+j).split(" ");
               for (int k = 1; k < patternConnected.length; k++) {
                    String[] noteFormats = patternConnected[k].split(",");
                    String noteLength = noteFormats[0];
                    String tieStart = "", tieEnd = "";
                    if (!noteFormats[1].equalsIgnoreCase("?")){
                        if (noteFormats[1].contains("-")) tieStart="-";
                        if (noteFormats[1].contains("+")) tieEnd="-";
                    }
                    int relativeLoc = Integer.parseInt(noteFormats[2]);

                   randomNoteLoc=Math.abs((firstNoteLoc+relativeLoc)%(scaleNotes.length-1));

                    if (scaleNotes[randomNoteLoc].charAt(0)=='r'){
                        music += scaleNotes[randomNoteLoc];

                        String tmp = scaleNotes[randomNoteLoc];
                        if (tmp.length()==1) tmp+=4;
                        else tmp = tmp.replace("e","8").replace("s","16");
                        parsedMusic += tmp.toLowerCase() + " ";
                    } else{
                        music += scaleNotes[randomNoteLoc] + tieStart + noteLength + tieEnd + " ";

                        String noteSig = noteLength;
                        String note = scaleNotes[randomNoteLoc];

                        String octStr = "";

                        int dif = Integer.parseInt(note.substring(note.length()-1,note.length()))-4;
                        if (dif>0){
                            for (int c = 0; c < dif; c++) {
                                octStr+="'";
                            }
                        }else if (dif<0){
                            for (int c = 0; c < Math.abs(dif); c++) {
                                octStr+=",";
                            }
                        }

                        noteSig = noteSig.replace("r","").replace("w","1").replace("h","2").replace("q","4").replace("e","8").replace("s","16");

                        note = note.substring(0,note.length()-1);

                        String output = note.replace("b", "es").replace("#", "is")+octStr+noteSig;
                        if (tieStart.equalsIgnoreCase("-")) output+="~";
                        parsedMusic+=output.toLowerCase()+" ";
                    }
                }
            }
        }

        WavCompiler.Key k = WavCompiler.Key.G;
        String speed = "T";

        if (key.equalsIgnoreCase("G")){
            speed+=100*2;
            k = WavCompiler.Key.G;
        }else if (key.equalsIgnoreCase("A")){
            speed+=55*2;
            k = WavCompiler.Key.A;
        }else if (key.equalsIgnoreCase("GF")){
            speed+=152*2;
            k = WavCompiler.Key.GF;
        }else if (key.equalsIgnoreCase("AF")){
            speed+=175*2;
            k = WavCompiler.Key.AF;
        }

        WavCompiler.convert(k, p.getSequence("I["+args[1]+"] "+speed+" "+music), args[3]);
        File dir = new File(System.getProperty("user.dir")+"/views/audio/");
        File f = new File(System.getProperty("user.dir")+"/views/audio/"+args[3]+".txt");
        dir.mkdirs();
        PrintWriter pw = new PrintWriter(f);
        pw.println("\\version \"2.18.2\"");
        pw.println("{");
        pw.println("\\time 4/4");
        pw.println("\\clef treble");
        pw.println(parsedMusic);
        pw.println("}");
        pw.close();
    }

}
