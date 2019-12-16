package royal.com.shareapplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        AssetManager assetManager = getAssets();
        try {
            String[] str = assetManager.list("");
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.raw_list,R.id.tv_data,str);
            listView.setAdapter(arrayAdapter);



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    String item = listView.getItemAtPosition(i).toString();
                    Intent intent = new Intent(getApplicationContext(),PDFviewer.class);
                    intent.putExtra("pdf",item);
                    startActivity(intent);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
