package projects.geology.textsenderv1;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;

//Press Alt + enter to import all the classes you are missing!


public class HomePage extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "projects.geology.textsenderv1.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

 /*     This is the fancy floating button. Temporarily removed since I added a boring button.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*Yay! It's finally java again.
        sends a message to the message_destination, defined in strings.xml
        called when send button is pressed.
     */
    public void sendMessage(View view) {
        EditText milesDrivenField = (EditText) findViewById(R.id.weekly_miles_driven); //do late binding stuffs to be tricksy
        displayMessage(milesDrivenField.getText().toString(), true);
        String message = getString(R.string.message_prefix) + "\n" + getString(R.string.weekly_miles_driven_desc) + ": " + milesDrivenField.getText().toString();
        sendEmail(getString(R.string.message_destination), message);
    }

    public void sendEmail(String destination, String text) {
        /*Normal way to send a text message. Will not send to email address
        SmsManager manager = SmsManager.getDefault();
        manager.sendDataMessage(destination, null, text, null, null);
        */
        /*This launches a chooser to pick which app to send the message with.
        //Clunky, but sending an sms to an email address programmatically requires specific formatting per carrier (SMS gateway)
        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{destination});
        i.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name + ": Automated Message");
        i.putExtra(Intent.EXTRA_TEXT, text);
        try {
            startActivity(Intent.createChooser(i, "Select email app and send..."));
        } catch (android.content.ActivityNotFoundException ex) {
            displayMessage("\n\nCould not send message");
        }

        */

        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + ": Automated Message");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setData(Uri.parse("mailto:" + destination)); // or just "mailto:" for blank
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
        try {
            startActivity(intent);
        } catch (Exception e) {
            displayMessage("Email could not be sent. (This is normal for emulators)", false);
        }

    }

    //a pop up dialog thing would be better
    public void displayMessage(String message, boolean addPrefix) {
        //First param is "Context", Activites are subclasses of Context
        //Second param is the class that should receive the itent. Basically, the DisplayMessageActivity
        //class knows to send a message when it is poked by HomePage
        if (addPrefix) {
            //This is how to get a string resource from strings.xml:
            String dest = getString(R.string.message_destination);
            message = getString(R.string.display_prefix) + dest +"\nMessage:\n" + message;
        }

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message); //Slap a message into the poke I'm about to send
        //EXTRA_MESSAGE is defined at top. Key value pair.
        startActivity(intent);
    }
}
