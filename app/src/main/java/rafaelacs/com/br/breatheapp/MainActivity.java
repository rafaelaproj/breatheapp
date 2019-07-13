package rafaelacs.com.br.breatheapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import java.text.MessageFormat;

import rafaelacs.com.br.breatheapp.util.Prefs;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView breatheText;
    private TextView timeText;
    private TextView sessionText;
    private TextView guideText;
    private Button startButton;
    private Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.lotusImage);
        breatheText = findViewById(R.id.breathTakenTxt);
        timeText = findViewById(R.id.lastBreathTxt);
        sessionText = findViewById(R.id.todayMinutesTxt);
        guideText = findViewById(R.id.guideTxt);
        prefs = new Prefs(this);

        startIntroAnimation();

        sessionText.setText(MessageFormat.format("{0} min today", prefs.getSessions()));
        breatheText.setText(MessageFormat.format("{0} Breathes", prefs.getBreathes()));
        timeText.setText(prefs.getDate());

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });


        ViewAnimator
                .animate(imageView)
                .translationY(-1000, 0)
                .alpha(0,1)
                //.andAnimate(text)
                .dp().translationX(-20, 0)
                .decelerate()
                .duration(2000)
                .thenAnimate(imageView)
                .scale(1f, 0.5f, 1f)
                .rotation(270)
                .repeatCount(3)
                .accelerate()
                .duration(2000)
                .start();
    }


    private void startIntroAnimation(){
        ViewAnimator
                .animate(guideText)
                .scale(0, 1)
                .duration(1500)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideText.setText("Breathe");
                    }
                })
                .start();

    }

    private void startAnimation(){
        ViewAnimator
                .animate(imageView)
                .alpha(0, 1)
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        guideText.setText("Inhale... Exhale");
                    }
                })
                .decelerate()
                .duration(1000)
                .thenAnimate(imageView)
                .scale(0.02f, 1.5f, 0.02f)
                .rotation(360)
                .repeatCount(5)
                .accelerate()
                .duration(5000)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        guideText.setText("Good Job!!!");
                        imageView.setScaleX(1.0f);
                        imageView.setScaleY(1.0f);

                        prefs.setSessions(prefs.getSessions() + 1);
                        prefs.setBreathes(prefs.getBreathes() + 1);
                        prefs.setDate(System.currentTimeMillis());

                        //refresh activity
                        new CountDownTimer(2000, 1000) {

                            @Override
                            public void onTick(long l) {
                                //put code to show ticking... 1, 2, 3 ...
                            }

                            @Override
                            public void onFinish() {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }.start();

                    }
                })
                .start();

    }

}
