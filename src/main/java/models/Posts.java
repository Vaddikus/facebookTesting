package models;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class Posts {

    public Data[] data;
    public Paging paging;

    public class Data {

        @SerializedName("created_time")
        public Date time;
        public String message;
        public String id;
    }

    public class Paging {
        public Cursors cursors;
    }

    public class Cursors {
        public String before;
        public String after;
    }
}

