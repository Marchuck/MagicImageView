package lukmarr.pl.magic;

import android.nfc.Tag;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    final static String TAG = MainActivity.class.getSimpleName();
    static Handler handler = new Handler();
    static ImageView serce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView serce = (ImageView) findViewById(R.id.serce);
        final Runnable lukasz = stepTimer(serce);
        serce.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                serce.setScaleX(0.01f);
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_UP: {
                        if (handler != null)
                            handler.removeCallbacks(lukasz);
                        if (scale > .5f) {
                            serce.setScaleY(scale);
                            serce.setScaleX(scale);
                        } else {
                            serce.setScaleY(1);
                            serce.setScaleX(1);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_DOWN:
                    default: {
                        scale = 0.01f;
                        if (handler == null)
                            handler = new Handler();
                        handler.postDelayed(lukasz, 20);
                        break;
                    }
                }
                return false;
            }
        });
        serce.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick ");
                serce.setImageResource(R.drawable.serce_puste);
                return false;
            }
        });
        serce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick ");
                serce.setImageResource(R.drawable.serce_pelne);
            }
        });
    }

    private static Runnable stepTimer(final ImageView serce) {
        return new Runnable() {
            @Override
            public void run() {
                if (scale < 1) {
                    scale += 0.01;
                    serce.setScaleX(scale);
                    serce.setScaleY(scale);
                    handler.postDelayed(this, 20); //LoopAction() is invoked every 2 seconds
                } else {
                    scale = 1;
                    serce.setScaleX(scale);
                    serce.setScaleY(scale);
                }
            }
        };

    }

    private static float scale = 0.01f;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
