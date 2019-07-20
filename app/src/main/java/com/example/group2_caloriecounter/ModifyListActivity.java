package com.example.group2_caloriecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyListActivity extends Activity implements View.OnClickListener {

    private EditText titleText;
    private Button updateBtn, deleteBtn;
    private EditText descText;

    private String _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify Record");

        setContentView(R.layout.activity_modify_list);

        dbManager = new DBManager(this);
        dbManager.open();

        titleText = (EditText) findViewById(R.id.subject_edittext);
        descText = (EditText) findViewById(R.id.description_edittext);

        updateBtn = (Button) findViewById(R.id.btn_update);
        deleteBtn = (Button) findViewById(R.id.btn_delete);

        Intent intent = getIntent();
        //String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");

        //_id = id;

        titleText.setText(name);
        descText.setText(desc);

        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                String title = titleText.getText().toString();
                String desc = descText.getText().toString();

                //dbManager.update(title, desc);
                this.returnHome();
                break;

            case R.id.btn_delete:
                //dbManager.delete(_id);
                this.returnHome();
                break;
        }
    }

    public void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), ListActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}
