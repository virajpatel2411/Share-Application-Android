package royal.com.shareapplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.barteksc.pdfviewer.PDFView;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;

public class PDFviewer extends AppCompatActivity {

    Button btnShare;
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        btnShare = findViewById(R.id.btn_share);
        pdfView = findViewById(R.id.pdfView);
        Intent i = getIntent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        final String name = i.getStringExtra("pdf");
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AssetManager assetManager = getAssets();
                //replace the name by your file name, make sure file is inside your assets folder
                InputStream in = null;
                try {
                    in = assetManager.open(name);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (in != null) {
                    try {
                        File attachment = stream2file(in);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setType("application/pdf");
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(attachment));
                        startActivity(Intent.createChooser(intent, "Share File Using"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("a", "getFilesFromAssets: file not found");
                }

                }
        });

        pdfView.fromAsset(name).load();

    }
    public File stream2file(InputStream in) throws IOException {
        final File tempFile = File.createTempFile("sample", ".pdf",
                PDFviewer.this.getExternalCacheDir());
        tempFile.deleteOnExit();

        FileOutputStream out = new FileOutputStream(tempFile);

        // for this you need add the following dependency in your build.gradle
        // compile 'org.apache.commons:commons-io:1.3.2'

        IOUtils.copy(in, out);
        return tempFile;
    }

}
