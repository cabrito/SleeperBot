package io.github.scalrx.sleeperbot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Actually make a real activity here. Instead, we're just making a placeholder button
        Button placeholderButton = findViewById(R.id.button_placeholder);
        placeholderButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent alarmsIntent = new Intent(getBaseContext(), AlarmsActivity.class);
                startActivity(alarmsIntent);
            }
        });
    }
}
