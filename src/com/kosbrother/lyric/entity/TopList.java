package com.kosbrother.lyric.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

public class TopList {
	
	int           id;
    String        name;
    int topListId;

    static String message = "[{\"id\":1,\"name\":\"Hit FM \u6392\u884c\u699c\",\"top_list_id\":0},{\"id\":2,\"name\":\"Hito \u4e2d\u6587\u699c\",\"top_list_id\":1},{\"id\":3,\"name\":\"\u6bcf\u9031\u6d41\u884c\u91d1\u66f2\u699c\",\"top_list_id\":1},{\"id\":4,\"name\":\"2011\u5e74\u5ea6\u55ae\u66f2\u699c\",\"top_list_id\":1},{\"id\":5,\"name\":\"2012\u5e74\u5ea6\u55ae\u66f2\u699c\",\"top_list_id\":1},{\"id\":6,\"name\":\"FOX\u97f3\u6a02\u98c6\u699c(\u539fChannel[V])\",\"top_list_id\":0},{\"id\":7,\"name\":\"\u97f3\u6a02\u98c6\u699c\uff08\u4e2d\u6587\u699c\uff09\",\"top_list_id\":6},{\"id\":8,\"name\":\"\u97f3\u6a02\u98c6\u699c\uff08\u65e5\u97d3\u699c\uff09\",\"top_list_id\":6},{\"id\":9,\"name\":\"Pop Radio\u6392\u884c\u699c\",\"top_list_id\":0},{\"id\":10,\"name\":\"\u5168\u7403\u83ef\u8a9e\u6b4c\u66f2\u6392\u884c\u699c\",\"top_list_id\":9},{\"id\":11,\"name\":\"Kiss Radio\u6392\u884c\u699c\",\"top_list_id\":0},{\"id\":12,\"name\":\"\u83ef\u8a9e\u6392\u884c\u699c\",\"top_list_id\":11},{\"id\":13,\"name\":\"\u6771\u6d0b\u6392\u884c\u699c\",\"top_list_id\":11},{\"id\":14,\"name\":\"MTV \u5c01\u795e\u699c\",\"top_list_id\":0},{\"id\":15,\"name\":\"MTV \u83ef\u8a9e\u5c01\u795e\u699c\",\"top_list_id\":14},{\"id\":16,\"name\":\"MTV \u6771\u6d0b\u5c01\u795e\u699c\",\"top_list_id\":14},{\"id\":17,\"name\":\"MTV \u97d3\u8a9e\u5c01\u795e\u699c\",\"top_list_id\":14},{\"id\":18,\"name\":\"\u9322\u6ac3\u9ede\u6b4c\u6392\u884c\u699c\",\"top_list_id\":0},{\"id\":19,\"name\":\"\u570b\u8a9e\u65b0\u6b4c\u6392\u884c\u699c\",\"top_list_id\":18},{\"id\":20,\"name\":\"\u53f0\u8a9e\u65b0\u6b4c\u6392\u884c\u699c\",\"top_list_id\":18},{\"id\":21,\"name\":\"\u570b\u8a9e\u9ede\u64ad\u7e3d\u6392\u884c\",\"top_list_id\":18},{\"id\":22,\"name\":\"\u53f0\u8a9e\u9ede\u64ad\u7e3d\u6392\u884c\",\"top_list_id\":18}]";
    public TopList() {
        this(1, "",1);
    }

    public TopList(int i, String name, int topListId) {
        this.id = i;
        this.name = name;
        this.topListId = topListId;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public static ArrayList<TopList> getMainTopLists() {
        ArrayList<TopList> lists = new ArrayList<TopList>();
        JSONArray jArray;
        try {
        	jArray = new JSONArray(message.toString());
            for (int i = 0; i < jArray.length(); i++) {
            	int top_list_id = jArray.getJSONObject(i).getInt("top_list_id");
                int id = jArray.getJSONObject(i).getInt("id");
                String name = jArray.getJSONObject(i).getString("name");

				if(top_list_id==0){
					TopList list = new TopList(id, name,top_list_id);
					lists.add(list);
				}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }
    
    public static ArrayList<TopList> getSubTopLists(int mainTopListId) {
        ArrayList<TopList> lists = new ArrayList<TopList>();
        JSONArray jArray;
        try {
        	jArray = new JSONArray(message.toString());
            for (int i = 0; i < jArray.length(); i++) {
            	int top_list_id = jArray.getJSONObject(i).getInt("top_list_id");
                int id = jArray.getJSONObject(i).getInt("id");
                String name = jArray.getJSONObject(i).getString("name");

				if(top_list_id== mainTopListId){
					TopList list = new TopList(id, name,top_list_id);
					lists.add(list);
				}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lists;
    }

}
