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



public class MainActivity extends Activity {

    Button button;
    ImageView imageView;
    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
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
    
    public void specCalendarEvent (String tit, String loc, String desc, String year, String month, String day)
    {
        title = tit;
        location = loc;
        description = desc;
        this.year = year;
        this.month = month;
        this.day = day;

        int intYear = Integer.parseInt(year);
        int intMonth = Integer.parseInt(month);
        int intDay = Integer.parseInt(day);

        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        calendarIntent.setType("vnd.android.cursor.item/event");
        calendarIntent.putExtra(CalendarContract.Events.TITLE, title);
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, description);

        GregorianCalendar calDate = new GregorianCalendar(intYear, intMonth, intDay);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calDate.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, calDate.getTimeInMillis());
        startActivity(calendarIntent);
    }
}

