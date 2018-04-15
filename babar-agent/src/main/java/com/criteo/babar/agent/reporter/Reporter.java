package com.criteo.babar.agent.reporter;

import com.criteo.babar.agent.config.AgentConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class Reporter {

    public abstract void start();
    public abstract void stop();
    public abstract void reportEvent(String metric, String label, Double value, Long time);

    protected static final String container;

    static {
        container = getContainerId();
    }

    public Reporter(AgentConfig config) {
        System.out.println("Using container id '" + container + "' in profiles");
    }

    private static String getContainerId() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            return hostname + "_" + getRandomString(8);
        } catch (UnknownHostException e) {
            System.err.println("Unable to get the hostname, using random value instead");
            e.printStackTrace();
            return getRandomString(32);
        }
    }

    private static String getRandomString(int length) {
        char[] randomChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int j = (int)Math.round(Math.random() * randomChars.length);
            sb.append(randomChars[j]);
        }
        return sb.toString();
    }
}
