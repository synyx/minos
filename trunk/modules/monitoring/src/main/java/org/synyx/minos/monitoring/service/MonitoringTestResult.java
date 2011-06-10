package org.synyx.minos.monitoring.service;

public class MonitoringTestResult {

    public enum MonitoringStatus {

        SUCCESS,
        ERROR,
        WARNING
    }

    private MonitoringStatus status;
    private String message;

    private MonitoringTestResult(MonitoringStatus status, String message) {

        this.status = status;
        this.message = message;
    }

    public void setStatus(MonitoringStatus status) {

        this.status = status;
    }


    public MonitoringStatus getStatus() {

        return status;
    }


    public void setMessage(String message) {

        this.message = message;
    }


    public String getMessage() {

        return message;
    }


    public static MonitoringTestResult error(String message) {

        return new MonitoringTestResult(MonitoringStatus.ERROR, message);
    }


    public static MonitoringTestResult warning(String message) {

        return new MonitoringTestResult(MonitoringStatus.WARNING, message);
    }


    public static MonitoringTestResult success(String message) {

        return new MonitoringTestResult(MonitoringStatus.SUCCESS, message);
    }
}
