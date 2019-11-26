package com.sylvan.algorithm.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: com.sylvan.algorithm.bean
 * @Author: sylvan
 * @Date: 19-11-26
 */
public class ListItemBean {

    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ListItemBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public static List<ListItemBean> parse(JSONObject object) {
        List<ListItemBean> beanList = new ArrayList<>();
        if (object != null) {
            JSONArray data = object.optJSONArray("data");
            if (data != null && data.length() > 0) {
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.optJSONObject(i);
                    ListItemBean itemBean = new ListItemBean();
                    itemBean.setTitle(jsonObject.optString("title"));
                    itemBean.setUrl(jsonObject.optString("url"));
                    beanList.add(itemBean);
                }
            }
        }
        return beanList;
    }
}
