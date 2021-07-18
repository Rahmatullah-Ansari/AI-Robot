package com.example.airobot;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;
import com.example.airobot.databinding.ActivityMainBinding;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private SpeechRecognizer speechRecognizer;
    private TextToSpeech textToSpeech;
    private Intent intent;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Dexter.withContext(this).withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                textView=binding.show;
                textToSpeech=new TextToSpeech(MainActivity.this, status -> {});
                intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speechRecognizer=SpeechRecognizer.createSpeechRecognizer(MainActivity.this);
                speechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle params) {
                    }
                    @Override
                    public void onBeginningOfSpeech() {
                    }
                    @Override
                    public void onRmsChanged(float rmsdB) {
                    }
                    @Override
                    public void onBufferReceived(byte[] buffer) {
                    }
                    @Override
                    public void onEndOfSpeech() {
                    }
                    @Override
                    public void onError(int error) {
                    }
                    @Override
                    public void onResults(Bundle results) {
                        ArrayList<String> arrayList=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                        String string;
                        textView.setText("");
                        if (arrayList!=null){
                            string=arrayList.get(0);
                            textView.setText(string);
                            if (string.equals("how are you")){
                                textToSpeech.speak("I am fine sir how are you sir?",TextToSpeech.QUEUE_FLUSH,null,null);
                            }
                            else if (string.equals("ok good")){
                                textToSpeech.speak("Ok Sir thank you good night sir have a nice day.",TextToSpeech.QUEUE_FLUSH,null,null);
                            }
                        }
                    }
                    @Override
                    public void onPartialResults(Bundle partialResults) {
                    }
                    @Override
                    public void onEvent(int eventType, Bundle params) {
                    }
                });
            }
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
        binding.click.setOnClickListener(v ->{
            textToSpeech.speak("Hello sir i am here how can i help you?",TextToSpeech.QUEUE_FLUSH,null,null);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            speechRecognizer.startListening(intent);
        });
    }
}