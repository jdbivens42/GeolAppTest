package projects.geology.textsenderv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        */

        //Added

        Intent intent = getIntent(); //Who summoned me? What do I need to send?
        String message = intent.getStringExtra(HomePage.EXTRA_MESSAGE); //Get the text sent by the HomePage

        TextView textView = new TextView(this);
        textView.setTextSize(20);

        textView.setText(message);

        CoordinatorLayout layout = (CoordinatorLayout) findViewById(R.id.content);
        layout.addView(textView);

    }

}
