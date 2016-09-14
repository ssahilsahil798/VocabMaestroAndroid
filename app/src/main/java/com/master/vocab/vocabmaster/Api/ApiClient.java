package com.master.vocab.vocabmaster.Api;

import com.master.vocab.vocabmaster.GlobalClass;

import retrofit.RestAdapter;

/**
 * Created by sahildeswal on 02/09/16.
 */
public class ApiClient {

        private static FeedApiInterface feedApiInterface;


        public static RestAdapter getRestAdapter() {
            return GlobalClass.getInstance().getRestAdapter();
        }

        public static FeedApiInterface getFeedApiInterface() {
            if (feedApiInterface == null) {
                feedApiInterface = getRestAdapter().create(FeedApiInterface.class);
            }
            return feedApiInterface;
        }


}
