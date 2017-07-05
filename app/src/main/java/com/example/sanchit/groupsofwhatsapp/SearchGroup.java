package com.example.sanchit.groupsofwhatsapp;

public class SearchGroup {
    private String name,icon,link;

    public  SearchGroup(String name,String icon, String link){
        this.name = name;
        this.icon = icon;
        this.link = link;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
