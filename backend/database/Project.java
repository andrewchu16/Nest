package database;

import java.util.ArrayList;

import database.utils.CsvSerializable;

public class Project implements CsvSerializable {
    private int id;
    private String name;
    private int creator;
    private ArrayList<Integer> orgs;

    public Project(String[] CsvData) {
        this.id = Integer.parseInt(CsvData[0]);
        this.name = CsvData[1];
        this.creator = Integer.parseInt(CsvData[2]);

        this.orgs = new ArrayList<Integer>();

        String[] orgIdStrings = CsvData[3].split("\t");
        for (String userId : orgIdStrings) {
            this.orgs.add(Integer.parseInt(userId));
        }
    }

    public Project(int id, String name, int creator, ArrayList<Integer> orgs) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.orgs = orgs;
    }

    public Project(int id, String name, int creatorId, String orgsString) {
        this.id = id;
        this.name = name;
        this.creator = creatorId;
        this.orgs = new ArrayList<Integer>();

        String[] orgIdStrings = orgsString.split("\t");
        for (String userId : orgIdStrings) {
            this.orgs.add(Integer.parseInt(userId));
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

    public void setName(String projectName) {
        this.name = projectName;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public ArrayList<Integer> getOrgs() {
        return orgs;
    }

    public void addOrg(int orgId) {
        if (this.orgs.contains(orgId)) {
            this.orgs.add(orgId);
        }
    }

    public void removeMember(int orgId) {
        this.orgs.remove(orgId);
    }

    @Override
    public String toCsvString() {
        return this.toString();
    }

    @Override
    public String toString() {
        String orgsString = "";
        for (int i = 0; i < orgs.size(); i++) {
            int member = orgs.get(i);
            orgsString += member;

            if (i < orgs.size() - 1) {
                orgsString += "\t";
            }
        }

        return id + "," + name + "," + creator + "," + orgsString;
    }
}
