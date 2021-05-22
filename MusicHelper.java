    import java.util.Random;

    import javax.sound.midi.*;

    /**
     * Handles the playing of scales and chords. Replacement for removed class MusicPlayer.
     *
     * @author Caleb Copeland
     * @version 4/6/21
     */
    public class MusicHelper extends QuickListener
    {
    
        private int[] savedNotes;
        private Sequence mySequence;
        private Sequencer sequencer;
        private final int timeMult = 5;
        private final int length = 5;
    
        private boolean activated = false;
        static private Random r;
    
        /**
         * Constructor for objects of class MusicHelper
         */
        public MusicHelper(int[] keyAsInts)
        {
        savedNotes = keyAsInts;
        r = new Random();
    }

    private Sequence toMIDI(int[] notes, String type) 
    throws javax.sound.midi.InvalidMidiDataException
    {
        Sequence seq = new Sequence(Sequence.PPQ,5,20);
        Track myTrack = seq.getTracks()[0];
        switch (type)
        {
            case "Listen":
            {
                int counter = 0;
                for (int note : savedNotes)
                {
                    addFullNote(myTrack, note, counter);
                    counter++;
                }
                addFullNote(myTrack,savedNotes[0] + 12, counter);

                break;
            }
            case "Play Chord":
            {
                addClump(myTrack,savedNotes,0);
                break;
            }
            case "Start": //sampler player
            {
                for(int i = 0; i < 7; i++)
                {
                    addClump(myTrack,TheoryObj.getRawChordAt(savedNotes,i+1),i*timeMult);
                    int counter = 0;
                    for (int j = 0; j < 4; j++)
                    {
                        addFullNote(myTrack, savedNotes[getRandomNumberInRange(0,6)] + 12, counter + i * 4);
                        counter++;
                    }
                }
                break;
            }
            default:
            {
                try 
                {
                    Integer.valueOf(type);
                    return toMIDI(notes,"Play Chord");
                }
                catch (java.lang.NumberFormatException e) // if the string is not a number, its not a chord
                {

                    return toMIDI(notes,"Listen");
                }
            }
        }

        return seq;
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max must be greater than min");
        }
        
        return r.nextInt((max - min) + 1) + min;
    }

    private void addClump(Track myTrack, int[] notes, int offset)
    throws javax.sound.midi.InvalidMidiDataException
    {
        for (int note : notes)
        {
            myTrack.add(toNote(note,offset     )[0]);
            myTrack.add(toNote(note,offset + 20)[1]);
        }
    }

    private void addFullNote(Track myTrack, int pitch, int counter)
    throws javax.sound.midi.InvalidMidiDataException
    {
        for (MidiEvent event : toNote(pitch,counter))
        {
            myTrack.add(event);
        }
    }

    private MidiEvent[] toNote(int pitch, int index)
    throws javax.sound.midi.InvalidMidiDataException
    {
        MidiEvent[] events = new MidiEvent[2];
        events[0] = new MidiEvent(makeMessage(pitch+StateWatcher.getOffset(),true),index*timeMult);
        events[1] = new MidiEvent(makeMessage(pitch+StateWatcher.getOffset(),false),(index+1)*timeMult);
        return events;

    }

    private MidiMessage makeMessage(int note,boolean isOn)
    throws javax.sound.midi.InvalidMidiDataException
    {
        int active = ShortMessage.NOTE_ON;
        if (!isOn)
        {
            active = ShortMessage.NOTE_OFF;
        }
        ShortMessage myMsg = new ShortMessage();
        myMsg.setMessage(active, 0, 60 + (note - 1), 93); //middle c plus note value

        long timeStamp = -1;
        return myMsg;

    }

    /**
     * Sets this MusicPlayer's tempo to a given number.
     */
    public void setTempo(float newTempo)
    {
        if (newTempo < 1)
        {
            throw new IllegalArgumentException("Tempo must be greater than zero!");
        }

        sequencer.setTempoInBPM(newTempo);

    }

    /**
     * Stops the sequencer.
     */
    public void stop()
    {
        sequencer.stop();
    }

    private void activate(String type) throws javax.sound.midi.InvalidMidiDataException
    {
        mySequence = toMIDI(savedNotes,type);
        sequencer.setSequence(mySequence);

        activated = true;
    }

    /**
     * Sets up the sequencer.
     */
    public void seqSetup() throws javax.sound.midi.MidiUnavailableException
    {
        sequencer = MidiSystem.getSequencer();
        sequencer.open();

    }

    /**
     * Either stops or starts the player.
     */
    public void act(String id)
    {
        if (id.equals("Stop"))
        {
            stop();
        }
        else
        {
            try
            {
                if (!activated)
                {
                    seqSetup();
                    activate(id);
                }
                sequencer.setTickPosition(0);
                StateWatcher.requestControl(this);
            }

            catch (MidiUnavailableException | InvalidMidiDataException mue)
            {
                mue.printStackTrace();
            }

            sequencer.start();
        }
    }

}
