package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private int age;
    private List<Auto> auto;

    public User(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
        this.auto = builder.auto;
    }

    public static class Builder{
        public int id;
        public String name;
        public int age;
        public static List<Auto> auto;

        static{
            auto = new ArrayList<>();
        }

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Builder setAge(int age){
            this.age = age;
            return this;
        }

        public Builder setAuto(List<Auto> auto){
            this.auto.addAll(auto);
            return this;
        }

        public Builder setAuto(Auto auto){
            this.auto.add(auto);
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Auto> getAuto() {
        return auto;
    }

    public void setAuto(List<Auto> auto) {
        this.auto.addAll(auto);
    }

    public void setAuto(Auto auto) {
        this.auto.add(auto);
    }
}
