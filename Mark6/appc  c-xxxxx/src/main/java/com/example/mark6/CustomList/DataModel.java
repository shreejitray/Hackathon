package com.example.mark6.CustomList;

public class DataModel {

     String name;
     String age;
     String experience;

    public DataModel(String name, String age, String experience){
        this.age = age;
        this.name = name;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
