package com.eng.asu.adaptivelearning.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.eng.asu.adaptivelearning.R;

public class ParentActivity extends AppCompatActivity {
    private Button addChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        addChild =(Button)findViewById(R.id.addChild);
        addChild.setOnClickListener(view -> addYourChild());
    }

    private void addYourChild() {
        //TODO intent activity to ask for child informations
        startActivity(new Intent(ParentActivity.this, ChildInformations.class));
    }


}
