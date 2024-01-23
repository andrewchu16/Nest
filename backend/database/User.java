package database;

import database.utils.CsvSerializable;

public class User implements CsvSerializable {
    private int id;
    private String username;
    private String name;
    private String hash;
    private String salt;

    public User(String[] CsvData) {
        this.id = Integer.parseInt(CsvData[0]);
        this.username = CsvData[1];
        this.name = CsvData[2];
        this.hash = CsvData[3];
        this.salt = CsvData[4];
    }

    public User(int id, String username, String name, String hash, String salt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.hash = hash;
        this.salt = salt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toCsvString() {
        return this.toString();
    }

    @Override
    public String toString() {
        return id + "," + username + "," + name + "," + hash + "," + salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
