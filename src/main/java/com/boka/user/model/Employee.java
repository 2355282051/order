package com.boka.user.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_info")
public class Employee extends Designer {

    //职业
    private int profession;

    public int getProfession() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession = profession;
    }
}
