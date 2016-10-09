// Daniel Bevilacqua, johnluan (coyote963), zackHarnett, Judah Maendel
package com.example.daniel.applicationflash;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map MorseMap = new HashMap();
    private CameraManager mCameraManager;
    private String mCameraId;
    private Boolean isTorchOn;
    private Button mTorchOnOffButton;
    private Button textSubmitButton;
    private EditText mEdit;
    private int dotLength = 350;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isTorchOn = false;
        initializeDictionary(MorseMap);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTorchOnOffButton = (Button) findViewById(R.id.flashbutton);
        textSubmitButton = (Button) findViewById(R.id.submit_button);
        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);



        if (!isFlashAvailable) {

            AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // closing the application
                            finish();
                            System.exit(0);
                        }
                    });
            alert.show();
            return;
        }
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        mTorchOnOffButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (isTorchOn) {
                        turnOffFlashLight();
                        isTorchOn = false;
                    } else {
                        turnOnFlashLight();
                        isTorchOn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        textSubmitButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit = (EditText) findViewById(R.id.editText);
                String translatedMessage = translateMorse(mEdit.getText().toString(),MorseMap );
                displayMorse(translatedMessage);
            }
        });
    }
    public void turnOffFlashLight()
    {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
                mTorchOnOffButton.setText(R.string.off);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void turnOnFlashLight()
    {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
                mTorchOnOffButton.setText(R.string.on);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void displayMorse(String morsemessage)
    {
        for (char c : morsemessage.toCharArray())
        {
            if(c == '+')
        {
            try {
                Thread.sleep((dotLength*3));
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
            else if (c=='-')
            {
                longPress();
            }
            else if (c=='.')
            {
                shortPress();
            }
            else if(c == '|')
            {
                try {
                    Thread.sleep((dotLength*3));
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            else if(c == '/')
            {
                try {
                    Thread.sleep((dotLength*7));
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    public String translateMorse(String message, Map map)
    {
        StringBuilder myStringBuilder = new StringBuilder();
        for (char c : message.toCharArray()) {
            myStringBuilder.append(map.get(c));
        }
        return myStringBuilder.toString();
    }
    public void longPress()
    {
        turnOnFlashLight();
        try {
            Thread.sleep((dotLength*3));
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        turnOffFlashLight();

    }
    public void shortPress() {
        turnOnFlashLight();
        try {
            Thread.sleep(dotLength);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        turnOffFlashLight();
    }
    public void initializeDictionary(Map MorseMap)
    {
        MorseMap.put('A',"+.+-|");
        MorseMap.put('B',"+-+.+.+.|");
        MorseMap.put('C',"+-+.+-+.|");
        MorseMap.put('D',"+-+.+.|");
        MorseMap.put('E',"+.|");
        MorseMap.put('F',"+.+.+-+.|");
        MorseMap.put('G',"+-+-+.|");
        MorseMap.put('H',"+.+.+.+.|");
        MorseMap.put('I',"+.+.|");
        MorseMap.put('J',"+.+-+-+-|");
        MorseMap.put('K',"+-+.+-|");
        MorseMap.put('L',"+.+-+.+.|");
        MorseMap.put('M',"+.+-+-|");
        MorseMap.put('N',"+-+.|");
        MorseMap.put('O',"+-+-+-|");
        MorseMap.put('P',"+.+-+-.|");
        MorseMap.put('Q',"+-+-+.+-|");
        MorseMap.put('R',"+.+-+.|");
        MorseMap.put('S',"+.+.+.|");
        MorseMap.put('T',"+-|");
        MorseMap.put('U',"+.+.+-|");
        MorseMap.put('V',"+.+.+.+-|");
        MorseMap.put('W',"+.+-+-|");
        MorseMap.put('X',"+-+.+.+-|");
        MorseMap.put('Y',"+-+.+-+-|");
        MorseMap.put('Z',"+-+-+.+.|");
        MorseMap.put('a',"+.+-|");
        MorseMap.put('b',"+-+.+.+.|");
        MorseMap.put('c',"+-+.+-+.|");
        MorseMap.put('d',"+-+.+.|");
        MorseMap.put('e',"+.|");
        MorseMap.put('f',"..-.|");
        MorseMap.put('g',"+-+-+.|");
        MorseMap.put('h',"+.+.+.+.|");
        MorseMap.put('i',"+.+.|");
        MorseMap.put('j',"+.+-+-+-|");
        MorseMap.put('k',"+-+.+-|");
        MorseMap.put('l',"+.+-+.+.|");
        MorseMap.put('m',"+.+-+-|");
        MorseMap.put('n',"+-+.|");
        MorseMap.put('o',"+-+-+-|");
        MorseMap.put('p',"+.+-+-+.|");
        MorseMap.put('q',"+-+-+.+-|");
        MorseMap.put('r',"+.+-+.|");
        MorseMap.put('s',"+.+.+.|");
        MorseMap.put('t',"+-|");
        MorseMap.put('u',"+.+.+-|");
        MorseMap.put('v',"+.+.+.+-|");
        MorseMap.put('w',"+.+-+-|");
        MorseMap.put('x',"+-+.+.+-|");
        MorseMap.put('y',"+-+.+-+-|");
        MorseMap.put('z',"+-+-+.+.|");
        MorseMap.put('.', "+.+-+.+-+.+-|");
        MorseMap.put(',', "+-+-+.+.+-+-|");
        MorseMap.put('?', "+.+.+-+-+.+.|");
        MorseMap.put(' ', "/");
    }
}
