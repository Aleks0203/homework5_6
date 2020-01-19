package ru.mail.druk_aleksandr.repository.model;

public class User {
    private Integer id;
    private String name;
    private String password;
    private Boolean isActive;
    private Integer userGroupId;
    private Integer age;

    private User(User.Builder builder) {
        id = builder.id;
        name = builder.name;
        password=builder.password;
        isActive = builder.isActive;
        userGroupId=builder.userGroupId;
        age = builder.age;
    }
    public static User.Builder newBuilder() {
        return new User.Builder();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId) {
        this.userGroupId = userGroupId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", userGroupId=" + userGroupId +
                ", age=" + age +
                '}';
    }
    public static final class Builder {

        private Integer id;
        private String name;
        private String password;
        private Boolean isActive;
        private Integer userGroupId;
        private Integer age;

        private Builder() {}

        public User.Builder id(Integer val) {
            id = val;
            return this;
        }

        public User.Builder name(String val) {
            name = val;
            return this;
        }

        public User.Builder password(String val) {
            password = val;
            return this;
        }

        public User.Builder isActive(Boolean val) {
            isActive = val;
            return this;
        }
        public User.Builder userGroupId(Integer val) {
            userGroupId = val;
            return this;
        }
        public User.Builder age(Integer val) {
            age = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
