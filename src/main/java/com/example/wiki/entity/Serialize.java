package com.example.wiki.entity;

public final class Serialize {
    public interface PostView {
    }

    public interface PostWithUser extends PostView {
    }

    public interface UserView {
    }

    public interface UserWithPosts extends UserView {
    }
}
