package com.yongquan.listenyourvocabularies.MainActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.yongquan.listenyourvocabularies.Database.WorkWithDB;
import com.yongquan.listenyourvocabularies.MainActivity.Adapter.DoneAdapter;
import com.yongquan.listenyourvocabularies.MainActivity.Adapter.ListeningAdapter;
import com.yongquan.listenyourvocabularies.R;

import ak.sh.ay.musicwave.MusicWave;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.rcv_listening)
    RecyclerView rcvListening;
    @BindView(R.id.rcv_done)
    RecyclerView rcvDone;
    @BindView(R.id.musicWave)
    MusicWave musicWave;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    MainPresenter mainPresenter;

    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 9976;
    private MediaPlayer mMediaPlayer;
    private Visualizer mVisualizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mainPresenter = new MainPresenter(this);
        mainPresenter.init(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
        } else {
            initialise();
        }
        VobModel vobModel = new VobModel("position","(n)","//","vị trí");
        WorkWithDB.addVob(this,vobModel);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean selectReturn = mainPresenter.onOptionItemSelect(item);
        if (selectReturn) {
            return selectReturn;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        mainPresenter.onNavigationClick(item);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void setListenAdapter(ListeningAdapter listenAdapter) {
        rcvListening.setLayoutManager(new LinearLayoutManager(this));
        rcvListening.setAdapter(listenAdapter);
    }

    @Override
    public void setDoneAdapter(DoneAdapter doneAdapter) {
        rcvDone.setLayoutManager(new LinearLayoutManager(this));
        rcvDone.setAdapter(doneAdapter);
    }
    private void initialise() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.unrelenting);
        prepareVisualizer();
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mVisualizer.setEnabled(false);
                fab.setImageResource(android.R.drawable.ic_media_play);
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status;
                if (mMediaPlayer.isPlaying()) {
                    status = "Sound Paused";
                    mMediaPlayer.pause();
                    fab.setImageResource(android.R.drawable.ic_media_play);
                } else {
                    status = "Sound Started";
                    mVisualizer.setEnabled(true);
                    mMediaPlayer.start();
                    fab.setImageResource(android.R.drawable.ic_media_pause);
                }
                Snackbar.make(view, status, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }
    private void prepareVisualizer() {
        mVisualizer = new Visualizer(mMediaPlayer.getAudioSessionId());
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        musicWave.updateVisualizer(bytes);
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 2, true, false);
        mVisualizer.setEnabled(true);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECORD_AUDIO: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initialise();
                } else {
                    Toast.makeText(MainActivity.this, "Allow Permission from settings", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}
