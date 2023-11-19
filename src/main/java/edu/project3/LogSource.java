package edu.project3;

public record LogSource(String path, LogSource.LogType type) {

    public enum LogType {
        PATH,
        URI
    }

}

