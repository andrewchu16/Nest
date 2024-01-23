package database;

import java.util.ArrayList;

import database.utils.Csv;
import database.utils.Password;

public class Database {
    private String userFileName;
    private String orgsFileName;
    private String projectsFileName;

    private ArrayList<User> users;
    private ArrayList<Project> projects;
    private ArrayList<Organization> orgs;

    public Database(String userFileName, String orgsFileName, String projectsFileName) {
        this.userFileName = userFileName;
        this.orgsFileName = orgsFileName;
        this.projectsFileName = projectsFileName;

        this.users = new ArrayList<User>();
        ArrayList<String[]> usersCsvData = Csv.read(this.userFileName);
        for (String[] userData : usersCsvData) {
            this.users.add(new User(userData));
        }

        this.projects = new ArrayList<Project>();
        ArrayList<String[]> orgsCsvData = Csv.read(this.orgsFileName);
        for (String[] orgData : orgsCsvData) {
            this.orgs.add(new Organization(orgData));
        }

        this.orgs = new ArrayList<Organization>();
        ArrayList<String[]> projectsCsvData = Csv.read(this.projectsFileName);
        for (String[] projData : projectsCsvData) {
            this.projects.add(new Project(projData));
        }
    }

    public void close() {
        ArrayList<String> usersCsvData = new ArrayList<String>();
        for (User user : this.users) {
            usersCsvData.add(user.toCsvString());
        }

        ArrayList<String> orgsCsvData = new ArrayList<String>();
        for (Organization org : this.orgs) {
            orgsCsvData.add(org.toCsvString());
        }

        ArrayList<String> projectsCsvData = new ArrayList<String>();
        for (Project project : this.projects) {
            projectsCsvData.add(project.toCsvString());
        }

        Csv.write(this.userFileName, usersCsvData);
        Csv.write(this.orgsFileName, orgsCsvData);
        Csv.write(this.projectsFileName, projectsCsvData);
    }

    public User getUserById(int id) {
        if (id >= this.users.size()) {
            return null;
        }

        for (User user : this.users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public Project getProjectById(int id) {
        if (id >= this.projects.size()) {
            return null;
        }

        for (Project project : this.projects) {
            if (project.getId() == id) {
                return project;
            }
        }

        return null;
    }

    public Organization getOrgById(int id) {
        if (id >= this.orgs.size()) {
            return null;
        }

        for (Organization org : this.orgs) {
            if (org.getId() == id) {
                return org;
            }
        }

        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public boolean login(String username, String password) {
        User user = this.getUserByUsername(username);
        String salt = user.getSalt();
        String hash = Password.hash(password, salt);

        return hash.equals(user.getHash());
    }

    public boolean signUp(String name, String username, String password) {
        User user = this.getUserByUsername(username);
        if (user != null) {
            return false;
        }

        int id = this.users.size();
        String salt = Password.generateSalt();
        String hash = Password.hash(password, salt);
        user = new User(id, username, name, hash, salt);
        this.users.add(user);

        return true;
    }

    public boolean renameUser(int userId, String newUsername) {
        User user = this.getUserById(userId);

        if (user == null) {
            return false;
        }

        if (this.getUserByUsername(newUsername) != null) {
            return false;
        }

        user.setUsername(newUsername);
        return true;
    }
}
