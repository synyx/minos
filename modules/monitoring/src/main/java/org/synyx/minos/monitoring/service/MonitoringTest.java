package org.synyx.minos.monitoring.service;

public class MonitoringTest {

    private String name;
    private String description;

    private MonitoringTestCommand command;

    public MonitoringTest(MonitoringTestCommand command) {

        this.name = command.getClass().getSimpleName();
        this.description = command.getClass().getName();
        this.command = command;
    }


    public MonitoringTest(String name, String description, MonitoringTestCommand command) {

        super();
        this.setName(name);
        this.setDescription(description);
        this.command = command;
    }

    protected MonitoringTestResult execute() {

        return command.getResult();
    }


    public void setName(String name) {

        this.name = name;
    }


    public String getName() {

        return name;
    }


    public void setDescription(String description) {

        this.description = description;
    }


    public String getDescription() {

        return description;
    }


    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());

        return result;
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        MonitoringTest other = (MonitoringTest) obj;

        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;

        return true;
    }

    public interface MonitoringTestCommand {

        MonitoringTestResult getResult();
    }
}
