package ca.ualberta.cs.lonelytwitter;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Created by romansky on 10/20/16.
 */
public class ElasticsearchTweetController {
    private static JestDroidClient client;

    // TODO we need a function which adds tweets to elastic search
    public static class AddTweetsTask extends AsyncTask<NormalTweet, Void, Void> {

        @Override
        //multiple tweets => '...'
        protected Void doInBackground(NormalTweet... tweets) {
            verifySettings();
            //for each tweets run this code
            for (NormalTweet tweet : tweets) {
                //builds an index testing and type tweet
                Index index = new Index.Builder(tweet).index("testing").type("tweet").build();

                try {
                    // where is the client?
                    //use client to post the tweet
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){
                        //if created document in elastic search then
                        tweet.setId(result.getId());
                    } else {
                        //if not succeed
                        Log.e("Error", "Elastic search was not able to add the tweet.");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the tweets");
                }

            }
            return null;
        }
    }

    // TODO we need a function which gets tweets from elastic search
    public static class GetTweetsTask extends AsyncTask<String, Void, ArrayList<NormalTweet>> {
        @Override
        protected ArrayList<NormalTweet> doInBackground(String... search_parameters) {
            verifySettings();

            System.out.println(search_parameters[0]);

            //initial the tweet empty arraylist
            ArrayList<NormalTweet> tweets = new ArrayList<NormalTweet>();
            //building the search
            //look at documents that are tweets

            Search search = new Search.Builder(search_parameters[0])
                    .addIndex("testing")
                    .addType("tweet")
                    .build();

            try {
               //execute the search
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    //converting to normaltweet list
                    List<NormalTweet> foundTweets =
                            result.getSourceAsObjectList(NormalTweet.class);
                    tweets.addAll(foundTweets);
                }else{
                    Log.e("Error",
                            "The search query failed to find any tweets that matched.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tweets;
        }
    }



    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}