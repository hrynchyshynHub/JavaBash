package com.javabash.engine;


public class Line{

    private String user;
    private Character separator;
    private String command;

    public Line() {
        user = "root:~$";
        separator = ' ';
    }

    public Line(String user, Character separator, String command) {
        this.user = user;
        this.separator = separator;
        this.command = command;
    }

    public Line(String user, String command) {
        this.user = user;
        this.command = command;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Character getSeparator() {
        return separator;
    }

    public void setSeparator(Character separator) {
        this.separator = separator;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public static Line parseLine(String string) {
        String[] strings = string.split(" ");
        String user = strings[0];
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < strings.length; i++){
            sb.append(strings[i] + " ");
        }

        return new Line(user, sb.toString());
    }

    @Override
    public String toString() {
        return getUser() + getSeparator() + getCommand();
    }
}
