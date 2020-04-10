package com.example.aaronaftab.walkable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Gravity;
import android.util.Log;


import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton button = findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddClassActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
            TextView firstEvent = findViewById(R.id.class1input);
            TextView secondEvent = findViewById(R.id.time);
            TextView thirdEvent = findViewById(R.id.class2input);
            //set text
            String[] values = getInfo(this);
            //Log.d("check", values[4]);
            firstEvent.setText(Html.fromHtml(values[0] + "\n" + values[2]));
            secondEvent.setText(values[4]);
            thirdEvent.setText(Html.fromHtml(values[1] + "\n" + values[3]));




            //MOST OF THE STUFF BELOW THIS IS FOR SETTING UP MULTIPLE TEXTVIEWS
        /*
            firstEvent.setId(count++);
            secondEvent.setId(count++);
            thirdEvent.setId(count++);
//            LayoutParams lp = (RelativeLayout.LayoutParams) firstEvent.getLayoutParams();
//            LayoutParams lp2 = (RelativeLayout.LayoutParams) secondEvent.getLayoutParams();
            RelativeLayout.LayoutParams attributes = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams attributes2 = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams attributes3 = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            attributes.addRule(RelativeLayout.BELOW, R.id.top);
            attributes2.addRule(RelativeLayout.BELOW, R.id.top);
            attributes3.addRule(RelativeLayout.BELOW, R.id.top);
            attributes.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            attributes2.addRule(RelativeLayout.ALIGN_RIGHT, firstEvent.getId());
            attributes3.addRule(RelativeLayout.ALIGN_RIGHT, secondEvent.getId());
            firstEvent.setLayoutParams(attributes);
            secondEvent.setLayoutParams(attributes2);
            thirdEvent.setLayoutParams(attributes3);
            firstEvent.setBackgroundColor(Color.GREEN);
            thirdEvent.setBackgroundColor(Color.GREEN);
            firstEvent.setLines(2);
            thirdEvent.setLines(2);
            //add textview
            //View relativeLayout = findViewById(R.id.textView);
            rellayout.addView(firstEvent, attributes);
            rellayout.addView(secondEvent, attributes2);
            rellayout.addView(thirdEvent, attributes3);
        } else {
            Log.i("Passed info", intent.getStringExtra("class1")
                    + intent.getStringExtra("class2") + intent.getStringExtra("location1")
                    + intent.getStringExtra("location2"));
            String err = "All fields must be filled.";
            Toast message = Toast.makeText(MainActivity.this, err, Toast.LENGTH_LONG);
            message.setGravity(Gravity.CENTER, 0, 0);
            message.show();
        }
        */

        //getIntent().getStringExtra("class1");
        //getIntent().getStringExtra("class2");

    }

    public static String[] getInfo(Context context) {
        String[] inputs = new String[5];
        SharedPreferences prefs = context.getSharedPreferences("ClassLoc", MODE_PRIVATE);
        inputs[0] = prefs.getString("class1", "");
        inputs[1] = prefs.getString("class2", "");
        inputs[2] = prefs.getString("location1", "");
        inputs[3] = prefs.getString("location2", "");
        inputs[4] = prefs.getString("time", "");
        return inputs;
    }

    public void openAddClassActivity() {
        Intent start = new Intent(this, Main2Activity.class);
        startActivity(start);
    }
}
