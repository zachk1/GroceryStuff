package com.example.zach.imagecalendar;



import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class MainActivity extends Activity {

    Button button;
    ImageView imageView;
    Button calendarbutton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String title = "SuperCoolStuff";
    String location = "SuperCoolPlace";
    String description = "Super Cool";
    String year = "2017";
    String month = "December";
    String day = "7";
    String time = "2300";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        calendarbutton = (Button) findViewById(R.id.calendarbutton);
        imageView = (ImageView) findViewById(R.id.window);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri photoURI = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", getFile());
                camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(camera_intent, REQUEST_IMAGE_CAPTURE);
            }
        });
        calendarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                calendarIntent.setType("vnd.android.cursor.item/event");
                calendarIntent.putExtra(CalendarContract.Events.TITLE, "Lit Party");
                calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Pritchard");
                calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, "BBQ");

                GregorianCalendar calDate = new GregorianCalendar(2017, 10, 8);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());
                startActivity(calendarIntent);
            }
        });

    }


    private File getFile() {

        File folder = new File(Environment.getExternalStorageDirectory(),"image_calendar");
        File image_file = new File(folder, "cam_image.jpg");
        return image_file;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = "sdcard/image_calendar/cam_image.jpg";
        imageView.setImageDrawable(Drawable.createFromPath(path));
    }
}
