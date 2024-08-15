import javax.sound.midi.*;

public class MusicMaker {

	public static final int BAR_LENGTH = 480;

	private Sequence sequence;
	private Sequencer sequencer;
	
	public MusicMaker() {
		
		try{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequence = new Sequence(Sequence.PPQ, BAR_LENGTH);
		}
		catch (final InvalidMidiDataException e) {
			e.printStackTrace();
			System.err.println("It's internal error. Please check!");
			System.exit(1);
		}
		catch (final MidiUnavailableException e) {
			e.printStackTrace();
			System.err.println("It's internal error. Please check!");
			System.exit(1);
		}
	}
	
	public Track createTrack(final int[] noteArray, final int[] lengthArray) {
		
		if (noteArray.length != lengthArray.length) {
			System.err.println("Error! noteArray and lengthArray should be same length!");
			System.exit(1);
		}
		
		final Track track = sequence.createTrack();
		
		long offset = 0;
		for (int i=0; i<noteArray.length; ++i) {
			if (noteArray[i] < 0) {
				System.err.printf("Note number must be positive! (%d's note number is %d)\n", i + 1, noteArray[i]);
				System.exit(1);
			}
			if (lengthArray[i] < 0) {
				System.err.printf("Note length must be positive! (%d's note length is %d)\n", i + 1, lengthArray[i]);
				System.exit(1);
			}
			track.add(createNoteOnEvent(noteArray[i], offset));
			offset += lengthArray[i];
			track.add(createNoteOffEvent(noteArray[i], offset));
		}
		
		return track;
	}
	
	public Track createTrack(final int[] noteArray, final long[] lengthArray) {
		
		if (noteArray.length != lengthArray.length) {
			System.err.println("Error! noteArray and lengthArray should be same length!");
			System.exit(1);
		}
		
		final Track track = sequence.createTrack();
		
		long offset = 0;
		for (int i=0; i<noteArray.length; ++i) {
			if (noteArray[i] < 0) {
				System.err.printf("Note number must be positive! (%d's note number is %d)\n", i + 1, noteArray[i]);
				System.exit(1);
			}
			if (lengthArray[i] < 0) {
				System.err.printf("Note length must be positive! (%d's note length is %d)\n", i + 1, lengthArray[i]);
				System.exit(1);
			}
			track.add(createNoteOnEvent(noteArray[i], offset));
			offset += lengthArray[i];
			track.add(createNoteOffEvent(noteArray[i], offset));
		}
		
		return track;
	}
	
	public boolean deleteTrack(final Track track) {
		
		return sequence.deleteTrack(track);
	}
	
	public void setTempoInBPM(final float bpm) {

		sequencer.setTempoInBPM(bpm);
	}
	
	public void playMusic() {
		playMusic(0);
	}

	public void playMusic(final int loopCount) {
		try {
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(loopCount);
			sequencer.start();
		}
		catch (final IllegalStateException e) {
			e.printStackTrace();
			System.err.println("This MusicMaker has already closed.");
			System.exit(1);
		}
		catch (final InvalidMidiDataException e) {
			e.printStackTrace();
			System.err.println("It's internal error. Please check!");
			System.exit(1);
		}
	}
	
	public void stopMusic() {
		sequencer.stop();
		sequencer.close();
	}

	private static MidiEvent createNoteOnEvent(final int nKey, final long lTick) {

		return createNoteEvent(ShortMessage.NOTE_ON, nKey, 100, lTick);
	}

	private static MidiEvent createNoteOffEvent(final int nKey, final long lTick) {

		return createNoteEvent(ShortMessage.NOTE_OFF, nKey, 100,lTick);
	}

	private static MidiEvent createNoteEvent(final int nCommand, final int nKey, final int nVelocity, final long lTick) {

		final ShortMessage message = new ShortMessage();

		try {
			message.setMessage(nCommand, 0, nKey, nVelocity);
		}
		catch (final InvalidMidiDataException e) {
			e.printStackTrace();
			System.err.println("It's internal error. Please check!");
			System.exit(1);
		}

		return new MidiEvent(message, lTick);
	}
	
	public void waitForPlaying() {
		while(sequencer.isRunning()) {
			try {
				Thread.sleep(1000);
			}
			catch (Exception e) {
				//Do nothing
			}
		}
	}
	
	public static void main(String[] args){
	    
		final MusicMaker mm = new MusicMaker();
		
		final int n1 = MusicMaker.BAR_LENGTH/1;
		final int n2 = MusicMaker.BAR_LENGTH/2;
		final int n4 = MusicMaker.BAR_LENGTH/4;
		final int n8 = MusicMaker.BAR_LENGTH/8;
		final int n16 = MusicMaker.BAR_LENGTH/16;
		
		final int[] note1 =   {
			0,
			76,75,
			76,75,76,71,74,72,
			69   , 0,60,64,69,
			71   , 0,64,68,71,
			72   , 0,64,76,75,
			76,75,76,71,74,72,
			69   , 0,60,64,69,
			71   , 0,64,72,71,
			69               ,
			76,75,
			76,75,76,71,74,72,
			69   , 0,60,64,69,
			71   , 0,64,68,71,
			72   , 0,64,76,75,
			76,75,76,71,74,72,
			69   , 0,60,64,69,
			71   , 0,64,72,71,
			69   , 0,71,72,74,
			76      ,67,77,76,
			74      ,65,76,74,
			72      ,64,74,72,
			71   , 0,64,76, 0,
			 0,76,88, 0, 0,75,
			76   , 0,75,76,75,
			76,75,76,71,74,72,
			69   , 0,60,64,69,
			71   , 0,64,68,71,
			72   , 0,64,76,75,
			76,75,76,71,74,72,
			69   , 0,60,64,69,
			71   , 0,64,72,71,
			69   , 0,71,72,74,
			76      ,67,77,76,
			74      ,65,76,74,
			72      ,64,74,72,
			71   , 0,64,76, 0,
			 0,76,88, 0, 0,75,
			76   , 0,75,76,75,
			76,75,76,71,74,72,
			69   , 0,60,64,69,
			71   , 0,64,68,71,
			72   , 0,64,76,75,
			76,75,76,71,74,72,
			69   , 0,60,64,69,
			71   , 0,64,72,71,
			69
		};
		final int[] length1 = {
			n1,
			n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     +n8     +n8     ,
			n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     +n16,n16,n16,n16,
			n8     +n16,n16,n16,n16,
			n8     +n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     +n16,n16,n16,n16,
			n8     +n16,n16,n16,n16,
			n8     +n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     ,n16,n16,n16,n16,
			n8     +n8     +n8
		};
		mm.createTrack(note1, length1);
		
		final int[] note2 =   {
			0,
			0,
			0,
			45,52,57         ,
			40,52,56         ,
			45,52,57         ,
			0,
			45,52,57         ,
			40,52,56         ,
			45,52,57         ,
			0,
			0,
			45,52,57         ,
			40,52,56         ,
			45,52,57         ,
			0,
			45,52,57         ,
			40,52,56         ,
			45,52,57         ,
			48,55,60         ,
			43,55,59         ,
			45,52,57         ,
			40,52,64, 0, 0,64,
			76, 0, 0,75,76, 0,
			 0,75,76, 0, 0   ,
			0,
			45,52,57         ,
			40,52,56         ,
			45,52,57         ,
			0,
			45,52,57         ,
			40,52,56         ,
			45,52,57         ,
			48,55,60         ,
			43,55,59         ,
			45,52,57         ,
			40,52,64, 0, 0,64,
			76, 0, 0,75,76, 0,
			 0,75,76, 0, 0   ,
			0,
			45,52,57         ,
			40,52,56         ,
			45,52,57         ,
			0,
			45,52,57         ,
			40,52,56         ,
			45,52,57
		};
		final int[] length2 = {
			n1,
			n8,
			n8     +n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n8     +n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n8,
			n8     +n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n8     +n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n16,n16,n16,n16,n8     ,
			n8     +n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n8     +n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n16,n16,n16,n16,
			n16,n16,n16,n16,n16,n16,
			n16,n16,n16,n16,n8     ,
			n8     +n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n8     +n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8     ,
			n16,n16,n8     +n8
		};
		mm.createTrack(note2, length2);
		mm.setTempoInBPM(15.0f);
		
		mm.playMusic();
		mm.waitForPlaying();
		mm.stopMusic();
	}
}
