package de.nikolas.schoolbot.mysql;

import de.nikolas.schoolbot.exam.Exam;
import de.nikolas.schoolbot.storage.Mediator;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Database {

    private MySQL mySQL;
    private DatabaseConfiguration configuration;

    private Mediator<Exam> examMediator;

    public Database() {
        this.configuration = new DatabaseConfiguration("embered.net", "einbot", "embered", "pw", 3306);
        this.mySQL = new MySQL(configuration);
        this.examMediator = new Mediator<>();

        this.createTables();
    }

    public void createTables() {
        mySQL.connect();
        mySQL.update("CREATE TABLE IF NOT EXISTS exams (id varchar(64), subject TEXT, dat TEXT);");
        mySQL.update("CREATE TABLE IF NOT EXISTS users (id varchar(64), level INT, xp INT, xpTo INT);");
    }

    public int userExists(User user) {
        ResultSet resultSet = mySQL.asyncQuery("SELECT * FROM " + "users" + " WHERE id= '" + user.getId() + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getInt("level");

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void createUser(User user) {
        if(userExists(user) >0){
            System.out.println("user exists!!!");
            return;
        }
        int xpTo =  5 * (1 * 1) + 50 * 1 + 200;
        mySQL.update("INSERT INTO users (id, level, xp, xpTo) VALUES ('" + user.getId() + "', '" + 1 + "' ,'" + 0 + "', '" + xpTo + "');");
    }

    public int getLevel(User user) {
        createUser(user);
        ResultSet resultSet = mySQL.asyncQuery("SELECT level FROM " + "users" + " WHERE id= '" + user.getId() + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getInt("level");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getXp(User user) {
        createUser(user);
        ResultSet resultSet = mySQL.asyncQuery("SELECT xp FROM " + "users" + " WHERE id= '" + user.getId() + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getInt("xp");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void incrementLevel(User user) {
        int level = getLevel(user);
        mySQL.update("UPDATE users SET level= '" + (level +1) + "' WHERE id= '" + user.getId() + "';");
        mySQL.update("UPDATE users SET xp= '" + 10 + "' WHERE id= '" + user.getId() + "';");
    }

    public int getXpTo(User user) {
        int level = getLevel(user);
        return 5 * (level * level) + 50 * level + 200;
    }

    public void setXpTo(User user) {
        int level = getLevel(user);
        int xpTo =  5 * (level * level) + 50 * level + 200;
        mySQL.update("UPDATE users SET xpTo= '" + xpTo + "' WHERE id= '" + user.getId() + "';");
    }

    public void addRandomXp(User user) {
        Random random = new Random();
        int xpToAdd = random.nextInt((20 - 20) +1) + 15;
        int xp = getXp(user);
        mySQL.update("UPDATE users SET xp= '" + (xp + xpToAdd) + "' WHERE id= '" + user.getId() + "';");
    }

    public int handleXP(User user, MessageReceivedEvent event) {
        System.out.println("1");
        if(!event.getAuthor().isBot()&& !event.getMessage().getContentDisplay().startsWith("!")) {
            createUser(event.getAuthor());
            addRandomXp(user);

            int xp = getXp(user);

            int xpTo = getXpTo(user);
            if (xp >= xpTo) {
                incrementLevel(user);

                setXpTo(user);
                event.getTextChannel().sendMessage(
                        ":tada:  **__LEVELUP__** :tada:\n" +
                                "| :video_game: » " + event.getAuthor().getAsMention() + " befindet sich nun auf einem neuen Level! \n" +
                                "| :boom: »  Level » **" + getLevel(user) + "**").queue();

            }
            return xpTo;
        }else System.out.println("bot has written");
        return 0;
    }

    public boolean exists(int id, String table) {
        ResultSet resultSet = mySQL.asyncQuery("SELECT subject FROM " + table + " WHERE id= '" + id + "'");
        try {
            if(resultSet.next()) {
                return resultSet.getString("subject") != null;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createExam(Exam exam) {
        if(!exists(exam.getId(), "exams")) {
            mySQL.update("INSERT INTO exams (id, subject, dat) VALUES ('" + exam.getId() + "', '" + exam.getSubject() + "' ,'" + exam.getDate() + "');");
            examMediator.setValue(String.valueOf(exam.getId()), exam);
            examMediator.addObserver(String.valueOf(exam.getId()), () -> {
                System.out.println("Exam(" + exam.getId() + ") sucessfully updated!");
                editExam(getExam(exam.getId()));
            });
            System.out.println("Exam(" + exam.getId() + ") successfully created!");
        }
    }

    public List<Exam> getExams() {
        ArrayList<Exam> list = new ArrayList<>();
        examMediator.getStorageMap().values().forEach(examStorage -> {
            list.add(examStorage.value);
        });

        return list;
    }

    public int getNextId() {
        ResultSet resultSet = mySQL.asyncQuery("SELECT MAX(id) FROM exams;");
        try {
            if(resultSet.next()) {
                return resultSet.getInt("id") +1;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void editExam(Exam toSave) {
        mySQL.update("UPDATE exams SET subject= '" + toSave.getSubject() + "', dat= '" + toSave.getDate() + "' WHERE id= '" + toSave.getId() + "'");
    }

    public void deleteExam(int id) {
        mySQL.update("DELETE FROM exams WHERE id= '" + id + "';");
    }

    public Exam getExam(int id) {
        return examMediator.getValue(String.valueOf(id)).orElseThrow(RuntimeException::new);
    }

    public Mediator<Exam> getExamMediator() {
        return examMediator;
    }

}
