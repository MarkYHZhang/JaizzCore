package jaizzcore;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.MidiDevice.Info;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import com.sun.media.sound.AudioSynthesizer;
import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

public class WavCompiler {

    enum Key{
        G("G"), A("A"), GF("GF"), AF("AF");
        private final String name;
        Key(String s) {
            name = s;
        }
        public String toString() {
            return this.name;
        }
    }

    public static void convert(Key key, Sequence sequence, String audio_name) {
        try {

            System.out.println("Compiling solo wav...");
            AudioSynthesizer synth = findAudioSynthesizer();
            AudioInputStream stream = synth.openStream(null, null);
            double total = send(sequence, synth.getReceiver());
            long len = (long) (stream.getFormat().getFrameRate() * (total + 4));
            stream = new AudioInputStream(stream, stream.getFormat(), len);
            AudioSystem.write(stream, AudioFileFormat.Type.WAVE, new File(System.getProperty("user.dir")+"/views/audio/SOLO_"+audio_name+".wav"));
            synth.close();
            System.out.println("Finished compiling solo wav");

            System.out.println("Mixing solo wav with backing tack wav...");
            byte[] solo = Utils.getBytesFromWav(System.getProperty("user.dir")+"/views/audio/SOLO_"+audio_name+".wav");
            byte[] backing = Utils.getBytesFromWav(key.toString()+".wav");
            byte[] b = Utils.mixBuffers(solo,backing);
            ByteArrayInputStream byteInput = new ByteArrayInputStream(b);
            AudioInputStream ais = new AudioInputStream(byteInput, new AudioFormat(44100, 16, 2, true, false), b.length);
            File out = new File(System.getProperty("user.dir")+"/views/audio/"+audio_name+".wav");
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, out);
            System.out.println("Finished mixing wavs");

            System.out.println("Transcoding wav to mp3...");
            File target = new File(System.getProperty("user.dir")+"/views/audio/"+audio_name+".mp3");
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setFormat("mp3");
            attrs.setAudioAttributes(audio);
            Encoder encoder = new Encoder();
            encoder.encode(out, target, attrs);
            System.out.println("Finished transcoding.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static AudioSynthesizer findAudioSynthesizer()
            throws MidiUnavailableException {
        Synthesizer synth = MidiSystem.getSynthesizer();
        if (synth instanceof AudioSynthesizer)
            return (AudioSynthesizer) synth;
        Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (int i = 0; i < infos.length; i++) {
            MidiDevice dev = MidiSystem.getMidiDevice(infos[i]);
            if (dev instanceof AudioSynthesizer)
                return (AudioSynthesizer) dev;
        }
        return null;
    }

    private static double send(Sequence seq, Receiver recv) {
        float divtype = seq.getDivisionType();
        assert (seq.getDivisionType() == Sequence.PPQ);
        Track[] tracks = seq.getTracks();
        int[] trackspos = new int[tracks.length];
        int mpq = 500000;
        int seqres = seq.getResolution();
        long lasttick = 0;
        long curtime = 0;
        while (true) {
            MidiEvent selevent = null;
            int seltrack = -1;
            for (int i = 0; i < tracks.length; i++) {
                int trackpos = trackspos[i];
                Track track = tracks[i];
                if (trackpos < track.size()) {
                    MidiEvent event = track.get(trackpos);
                    if (selevent == null
                            || event.getTick() < selevent.getTick()) {
                        selevent = event;
                        seltrack = i;
                    }
                }
            }
            if (seltrack == -1)
                break;
            trackspos[seltrack]++;
            long tick = selevent.getTick();
            if (divtype == Sequence.PPQ)
                curtime += ((tick - lasttick) * mpq) / seqres;
            else
                curtime = (long) ((tick * 1000000.0 * divtype) / seqres);
            lasttick = tick;
            MidiMessage msg = selevent.getMessage();
            if (msg instanceof MetaMessage) {
                if (divtype == Sequence.PPQ)
                    if (((MetaMessage) msg).getType() == 0x51) {
                        byte[] data = ((MetaMessage) msg).getData();
                        mpq = ((data[0] & 0xff) << 16)
                                | ((data[1] & 0xff) << 8) | (data[2] & 0xff);
                    }
            } else {
                if (recv != null)
                    recv.send(msg, curtime);
            }
        }
        return curtime / 1000000.0;
    }

}
