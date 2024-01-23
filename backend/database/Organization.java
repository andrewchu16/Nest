package database;

import java.util.ArrayList;

import database.utils.CsvSerializable;

public class Organization implements CsvSerializable {
    private int id;
    private String name;
    private String type;
    private ArrayList<Integer> members;

    public Organization(String[] CsvData) {
        this.id = Integer.parseInt(CsvData[0]);
        this.name = CsvData[1];
        this.type = CsvData[2];

        this.members = new ArrayList<Integer>();
        String[] userIdStrings = CsvData[3].split("\t");
        for (String userId : userIdStrings) {
            this.members.add(Integer.parseInt(userId));
        }
    }

    public Organization(int id, String name, String type, String membersString) {
        this.id = id;
        this.name = name;
        this.type = type;

        this.members = new ArrayList<Integer>();
        String[] userIdStrings = membersString.split("\t");
        for (String userId : userIdStrings) {
            this.members.add(Integer.parseInt(userId));
        }
    }

    public Organization(int id, String name, String type, ArrayList<Integer> members) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.members = members;
    }

    @Override
    public String toCsvString() {
        return this.toString();
    }

    @Override
    public String toString() {
        String membersString = "";
        for (int i = 0; i < members.size(); i++) {
            int member = members.get(i);
            membersString += member;

            if (i < members.size() - 1) {
                membersString += "\t";
            }
        }

        return id + "," + name + "," + type + "," + membersString;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Integer> getMembers() {
        return members;
    }

    public void addMember(int userId) {
        if (this.members.contains(userId)) {
            this.members.add(userId);
        }
    }

    public void removeMember(int userId) {
        this.members.remove(userId);
    }
}
