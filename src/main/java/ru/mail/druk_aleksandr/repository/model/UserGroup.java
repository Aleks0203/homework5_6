package ru.mail.druk_aleksandr.repository.model;

public class UserGroup {
    private Integer id;
    private String name;

    private UserGroup(UserGroup.Builder builder) {
        id = builder.id;
        name = builder.name;

    }

    public static UserGroup.Builder newBuilder() {
        return new UserGroup.Builder();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public static final class Builder {

        private Integer id;
        private String name;

        private Builder() {
        }

        public UserGroup.Builder id(Integer val) {
            id = val;
            return this;
        }

        public UserGroup.Builder name(String val) {
            name = val;
            return this;
        }

        public UserGroup build() {
            return new UserGroup(this);
        }
    }
}
