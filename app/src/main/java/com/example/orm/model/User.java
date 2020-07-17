package com.example.orm.model;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.converter.PropertyConverter;
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String username;
    private String password;
    @Convert(converter = RoleConverter.class , columnType = Integer.class)
    private Role role;

    public enum Role{
        ADMIN(0) , GUEST(1) , NORMAL(2);
        private int i;
        Role(int i){
            this.i = i;
        }
        public int getI() {
            return i;
        }
    }
    @Generated(hash = 1779853688)
    public User(Long id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return this.role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public static class RoleConverter implements PropertyConverter<Role,Integer>{

        @Override
        public Role convertToEntityProperty(Integer databaseValue) {
            for (Role role : Role.values()){
                if (role.getI() == databaseValue)
                    return role;
            }
            return null;
        }

        @Override
        public Integer convertToDatabaseValue(Role entityProperty) {
            return entityProperty.getI();
        }
    }
}
