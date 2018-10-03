/**
*Lonely tweeter class runs the main application activity
*
*@author: vinay
*@since 1.0
*@see java.io
*/


package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
* lonelyTwitterAcitivity class, to provide the UI of the application
*/

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;

	//Create list of tweets
	ArrayList<Tweet> tweetList;
	ArrayAdapter<Tweet> adapter;


	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//First function that gets triggered

		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		Button clearButton = (Button) findViewById(R.id.clear);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		clearButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				//Clears all previous tweets up to now

				tweetList.clear();

				saveInFile();
				adapter.notifyDataSetChanged();

				//saveInFile(text, new Date(System.currentTimeMillis()));
				// don't close everytime it's saved ==> finish();

			}
		});

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				//Tweet gets created here

				Tweet tweet = new NormalTweet(text);
				tweetList.add(tweet);

				saveInFile();
				adapter.notifyDataSetChanged();

				//saveInFile(text, new Date(System.currentTimeMillis()));
				// don't close everytime it's saved ==> finish();

			}
		});
	}

	@Override
	/**
	* start of the application
	*/
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		//Load all the save tweets to our
		loadFromFile(); //Old function that reads from traditional way now changing to object oriented
		adapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item,tweetList);

		//Not list of strings but a list of Tweets now
		//String[] tweets = loadFromFile();
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		//		R.layout.list_item, tweets);
		oldTweetsList.setAdapter(adapter);
	}

	private void loadFromFile() {
		ArrayList<String> tweets = new ArrayList<String>(); // create a list of strings

		//ArrayList<CurrentMood> mymoods = new ArrayList<CurrentMood>();
		//mymoods.add(new Mood1());
		//mymoods.add(new Mood2());

		//NormalTweet myTweet = new NormalTweet();
		//myTweet.setMoods(mymoods);

		//tweetList.add(myTweet);
		try {
			FileInputStream fis = openFileInput(FILENAME);  //looks in the file of data storing
			BufferedReader in = new BufferedReader(new InputStreamReader(fis)); //read each line

			Gson gson = new Gson(); //library to save objects
			//returning the type of the NormalTweets
			Type listType = new TypeToken<ArrayList<NormalTweet>>(){}.getType();

			tweetList = gson.fromJson(in, listType);

			/**
			 * Old stuff no longer needed since it's converted to object oriented not string anymore
			 * String line = in.readLine();
			 while (line != null) { //reading the line in the loop; line by line and putting in array list
				tweets.add(line);
				line = in.readLine();


			}*/

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			tweetList = new ArrayList<Tweet>(); //an array of tweets
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return tweets.toArray(new String[tweets.size()]);
	}
	/**
	*Save the list in a file
	*@throws FileNotFoundException
	*/
	private void saveInFile(){//String text, Date date) {
		try {

			//NormalTweet myTweet = new NormalTweet("");
			//myTweet.setMessage("Yes");

			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_PRIVATE);//Context.MODE_APPEND);
			//MODE_PRIVATE due to no longer dealing with strings
			//fos.write(new String(date.toString() + " | " + text)
			//		.getBytes());
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

			Gson gson = new Gson();
			gson.toJson(tweetList, out);
			out.flush(); //or else print garbage

			//instead of creating string tweet make object w own properties
			//directly get objects rather than strings
			//gson -> dependcy

			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
