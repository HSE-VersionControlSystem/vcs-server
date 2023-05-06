package ru.hse.vcsserver.service;

public interface FilesSender {

    /**
     * Sends files from a specified directory.
     *
     * @param directoryName Directory's name.
     * @return true if all files sent and the response code is >= 200 && < 300, otherwise false.
     */
    boolean sendFiles(String directoryName);
}
