package suson.marco.noteable;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView centerImage;
    private ImageButton prevButton, playButton, nextButton;
    private ImageButton string1, string2, string3, string4, string5, string6;
    private int[] notes = {
            R.raw.string_1_note_e,
            R.raw.string_2_note_b,
            R.raw.string_3_note_g,
            R.raw.string_4_note_d,
            R.raw.string_5_note_a,
            R.raw.string_6_note_e};
    private String[] noteName = {"e", "B", "G", "D", "A", "E"};
    private int currentNote = new Random().nextInt(notes.length);
    private int previousNote;
    private final MediaPlayer player = new MediaPlayer();
    private boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        centerImage = (ImageView) findViewById(R.id.centerImage);
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        playButton = (ImageButton) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready = true;
                centerImage.setImageResource(R.drawable.active2);
                AssetFileDescriptor afd = MainActivity.this.getResources().openRawResourceFd(notes[currentNote]);
                try {
                    player.reset();
                    player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                    player.prepareAsync();
                    afd.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        nextButton = (ImageButton) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready = false;
                centerImage.setImageResource(R.drawable.sound_hole);
                Random rand = new Random();
                previousNote = currentNote;
                currentNote = rand.nextInt(notes.length);
                setNote(currentNote);
            }
        });
        prevButton = (ImageButton) findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready = false;
                centerImage.setImageResource(R.drawable.sound_hole);
                currentNote = previousNote;
                setNote(currentNote);
            }
        });
        string6 = (ImageButton) findViewById(R.id.string6);
        string1 = (ImageButton) findViewById(R.id.string1);
        string2 = (ImageButton) findViewById(R.id.string2);
        string3 = (ImageButton) findViewById(R.id.string3);
        string4 = (ImageButton) findViewById(R.id.string4);
        string5 = (ImageButton) findViewById(R.id.string5);
        string6 = (ImageButton) findViewById(R.id.string6);
        ImageButton[] stringButtons = {string1, string2, string3, string4, string5, string6};
        for(int i = 0; i < notes.length; i++) {
            final int index = i;
            final MediaPlayer stringPlayer = new MediaPlayer();
            stringPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
            stringButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(index == currentNote && ready) {
                        Toast toast = Toast.makeText(MainActivity.this, noteName[index], Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, -190);
                        toast.show();
                        centerImage.setImageResource(R.drawable.correct_response2);
                    }
                    else if(ready)
                        centerImage.setImageResource(R.drawable.incorrect_response2);
                    AssetFileDescriptor afd = MainActivity.this.getResources().openRawResourceFd(notes[index]);
                    try {
                        stringPlayer.reset();
                        stringPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
                        stringPlayer.prepareAsync();
                        afd.close();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.testmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.capo:
                Intent capoIntent = new Intent(MainActivity.this, CapoActivity.class);
                startActivity(capoIntent);
                return true;
            case R.id.headstock:
                Intent headIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(headIntent);
                return true;
        }
        return true;
    }
    protected void setNote(int currentNote) {
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
}