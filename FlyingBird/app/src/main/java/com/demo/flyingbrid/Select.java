package com.demo.flyingbrid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Select extends AppCompatActivity {
    Button FlyingBall;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        FlyingBall = (Button) findViewById(R.id.FlyingBall);
        FlyingBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(Select.this, MainActivity.class);
                Select.this.startActivity(intent);
                Toast.makeText(getApplicationContext(),"Jump to Flying Ball",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
