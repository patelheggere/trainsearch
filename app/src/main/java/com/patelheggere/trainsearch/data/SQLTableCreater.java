package com.patelheggere.trainsearch.data;

import android.provider.BaseColumns;

/**
 * Created by Talkative Parents on 8/24/2017.
 */

public class SQLTableCreater {
    SQLTableCreater(){}
    public static class TrainConnections implements BaseColumns {
        public static final String TABLE_NAME = "trainconnections";
        public static final String COLUMN_NAME_CONNECTIONS = "connections";
    }

}
