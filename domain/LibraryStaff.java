package domain;

public class LibraryStaff extends LibraryPerson {
    private String staffId;
    private String jobTitle;

    public LibraryStaff(String name, String email, Long addressId, String staffId, String jobTitle) {
        super(name, email, addressId);
        this.staffId = staffId;
        this.jobTitle = jobTitle;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return String.format("%s, Staff ID: %s, Job Title: %s", super.toString(), staffId, jobTitle);
    }
}
