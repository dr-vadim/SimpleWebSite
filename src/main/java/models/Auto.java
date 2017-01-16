package models;

import javax.persistence.*;

@Entity
@Table(name = "auto")
public class Auto {

    @Id
    @SequenceGenerator(name = "seq", sequenceName = "auto_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    @Column(name = "id")
    private int id;

    @Column(name = "model")
    private String model;

    @Column(name = "color")
    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Auto(Builder builder){
        this.id = builder.id;
        this.model = builder.model;
        this.color = builder.color;
        this.user = builder.user;
    }

    public Auto() {
    }

    public static class Builder{
        private int id;
        private String model;
        private String color;
        private User user;

        public Builder setId(int id){
            this.id = id;
            return this;
        }

        public Builder setModel(String model){
            this.model = model;
            return this;
        }

        public Builder setColor(String color){
            this.color = color;
            return this;
        }

        public Builder setUser(User user){
            this.user = user;
            return this;
        }

        public Auto build(){
            return new Auto(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
