import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class BackgroundMusicPlayer {
	private static Clip backgroundClip;
	private static boolean isMusicOn = true;
	private static long pausedTime = 0;


	public void playBackgroundMusic() {
		if (backgroundClip == null || !backgroundClip.isRunning()) {
			try {
				String audioFilePath = "/audioFile/b_sound.wav";
				AudioInputStream audioStream = AudioSystem
				    .getAudioInputStream(getClass().getResource(audioFilePath));
				AudioFormat format = audioStream.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);

				backgroundClip = (Clip) AudioSystem.getLine(info);
				backgroundClip.open(audioStream);

				FloatControl gainControl = (FloatControl) backgroundClip
				    .getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-25.0f);

				backgroundClip.setMicrosecondPosition(pausedTime);
				backgroundClip.start();
				backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);

			} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
				e.printStackTrace();
			}

		}

	}


	public boolean isMusicOn() {
		return isMusicOn;
	}


	public void stopBackgroundMusic() {
		if (backgroundClip != null && backgroundClip.isOpen()) {
			pausedTime = backgroundClip.getMicrosecondPosition();
			backgroundClip.stop();
			backgroundClip.close();
		}

	}


	public void toggleMusic() {
		if (isMusicOn) {
			stopBackgroundMusic();
		} else {
			playBackgroundMusic();
		}

		isMusicOn = !isMusicOn;
	}
}
