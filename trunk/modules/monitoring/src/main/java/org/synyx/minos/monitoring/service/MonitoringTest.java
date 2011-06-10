package org.synyx.minos.monitoring.service;

public abstract class MonitoringTest {

    private String name;
    private String description;

    public MonitoringTest() {
    }


    public MonitoringTest(String name, String description) {

        setName(name);
        setDescription(description);
    }

    /**
     * Implement this method in your <code>MonitoringTest</code> subclass and return the appropriate
     * {@link MonitoringTestResult}. This should not return null on error, but a result that reflects the error that
     * occurred.
     *
     * @return  a test result
     */
    public abstract MonitoringTestResult execute();


    public void setName(String name) {

        this.name = name;
    }


    public String getName() {

        if (name == null) {
            return getClass().getSimpleName();
        } else {
            return name;
        }
    }


    public void setDescription(String description) {

        this.description = description;
    }


    public String getDescription() {

        if (description == null) {
            return getClass().getName();
        } else {
            return description;
        }
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
}
