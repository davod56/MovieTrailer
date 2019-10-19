package com.example.MovieTrailer;

public class Contact {

    private String name;
    private String des;
    private String image;
    private  String downdoad;
    private  String id;
    private String subject;
    private String time;
    private String year;
    private String Director;
    private String Actors;
    private String language;


    public Contact(String id,String name,String image_url, String des,
                   String downdoad,String subject,String time,String year,
                   String director,String actors,String language){

        this.setId(id);
        this.setName(name);
        this.setDes(des);
        this.setImage(image_url);
        this.setDowndoad(downdoad);
        this.setSubject(subject);
        this.setTime(time);
        this.setActors(actors);
        this.setDirector(director);
        this.setYear(year);
        this.setLanguage(language);

    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public void setActors(String actors) {
        Actors = actors;
    }

    public String getSubject() {
        return subject;
    }

    public String getTime() {
        return time;
    }

    public String getYear() {
        return year;
    }

    public String getDirector() {
        return Director;
    }

    public String getActors() {
        return Actors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDowndoad(String downdoad) {
        this.downdoad = downdoad;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDes() {
        return des;
    }

    public String getImage() {
        return image;
    }

    public String getDowndoad() {
        return downdoad;
    }

    public String getId() {
        return id;
    }
}

