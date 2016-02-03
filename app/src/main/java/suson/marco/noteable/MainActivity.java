package suson.marco.noteable;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button prevButton, playButton, nextButton;
    private ImageView centerImage;
    private int[] notes = {
            R.raw.string_1_note_e,
            R.raw.string_2_note_b,
            R.raw.string_3_note_g,
            R.raw.string_4_note_d,
            R.raw.string_5_note_a,
            R.raw.string_6_note_e};
    private int currentNote = new Random().nextInt(notes.length);
    private int previousNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        centerImage = (ImageView) findViewById(R.id.centerImage);
        final MediaPlayer player = new MediaPlayer();
        AssetFileDescriptor afd = MainActivity.this.getResources().openRawResourceFd(notes[currentNote]);
        try {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
        } catch(IOException e) {
            e.printStackTrace();
        }
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerImage.setImageResource(R.drawable.ferrocircle_active);
                if (player.isPlaying()) {
                    player.stop();
                    try {
                        player.reset();
                        AssetFileDescriptor afd = MainActivity.this.getResources().openRawResourceFd(notes[currentNote]);
                        player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                        player.prepareAsync();
                        afd.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    player.prepareAsync();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });
        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerImage.setImageResource(R.drawable.ferrocircle);
                Random rand = new Random();
                previousNote = currentNote;
                currentNote = rand.nextInt(notes.length);
                AssetFileDescriptor afd = MainActivity.this.getResources().openRawResourceFd(notes[currentNote]);
                if (player.isPlaying())
                    player.stop();
                try {
                    player.reset();
                    player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                    afd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        prevButton = (Button) findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerImage.setImageResource(R.drawable.ferrocircle);
                AssetFileDescriptor afd = MainActivity.this.getResources().openRawResourceFd(notes[previousNote]);
                if (player.isPlaying())
                    player.stop();
                try {
                    player.reset();
                    player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                    afd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
