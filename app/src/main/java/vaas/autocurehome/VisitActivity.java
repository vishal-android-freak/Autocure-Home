package vaas.autocurehome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import vaas.autocurehome.utils.Utils;

public class VisitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit);

        ImageView visitor = (ImageView) findViewById(R.id.visitor);
        AppCompatButton unlock = (AppCompatButton) findViewById(R.id.first);
        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Utils(VisitActivity.this).doorLockUnlock("unlocked");
                    }
                }).start();
            }
        });

        AppCompatButton lock = (AppCompatButton) findViewById(R.id.second);
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        new Utils(VisitActivity.this).doorLockUnlock("locked");
                    }
                }).start();
            }
        });

        Picasso.with(this).load(getIntent().getStringExtra("path")).memoryPolicy(MemoryPolicy.NO_CACHE).into(visitor);
    }
}
