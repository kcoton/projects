import java.util.ArrayList;

/** This program stores Student objects in the class College and allows the user to perform use an interactive menu of
 *  options that performs different functions to modify, search, and retrieve information from the Student objects.
 */

public class College {
    private ArrayList<Student> students;

    /**
     * No argument constructor that creates a new ArrayList of students for College class.
     */
    public College() {
        students = new ArrayList<Student>();
    }

    /**
     * Adds a new student object to College ArrayList.
     * @param a name and address (optional) of Student to add
     */
    public void addStudent(Student a) {
        students.add(a);
    }

    /**
     * Returns a student's name (if exists in the list) using their student number as a parameter.
     * If student number is not matched, returns that student cannot be found.
     * @param studentNum student number to be matched to students in College
     * @return student's information (student number, name, address, GPA) if found
     */
    public void findStudent(int studentNum) {
        boolean isStudent = false;
        for (int i = 0; i < students.size(); i++) {
            Student a = students.get(i);
            if (a.getStudentNum() == studentNum) { // returns name of student if student number argument matches
                System.out.println("Student number '" + studentNum + "' is '" + a.getName() + "', lives at address '" + a.getAddress()
                        + "', and has a GPA of '" + a.calculateGPA() + "'.");
                isStudent = true;
            }
            if (i == students.size()-1 && !isStudent) {
                System.out.println("No match for student number found.");
            }
        }
    }

    /**
     * Deletes student from the list if a matching student number parameter is found.
     * @param studentNum student to delete
     * @return confirmation if student is deleted or not
     */
    public void deleteStudent(int studentNum) {
        boolean isDeleted = false;
        for (int i = 0; i < students.size(); i++) {
            Student a = students.get(i); // stores the information of that student into a
            if (a.getStudentNum() == studentNum) { // gets student number of a
                students.remove(i); // removes the student found at index i
                isDeleted = true;
                System.out.println("Student '" + studentNum + "' deleted.");
            }
            if (i == students.size()-1 && !isDeleted) { // if reaches the end of the array and isDeleted is still false, means nothing was deleted
                System.out.println("Student '" + studentNum + "' not found.");
            }
        }
    }

    /**
     * Adds grade point and credit value of a course to the student's overall transcript using their student number as a parameter.
     * @param studentNum student's number
     * @param gradePoint grade added 0-100
     * @param credits credit worth of course
     * @return confirmation if course added or not
     */
    public boolean addCourse(int studentNum, double gradePoint, double credits) {
        for (int i = 0; i < students.size(); i++) {
            Student a = students.get(i); // stores the information of that student into a
            if (a.getStudentNum() == studentNum) { // gets student number of a
                a.addCourse(gradePoint, credits); // uses addCourse from Student class to add to student's GPA transcript
                return true; // returns true means course successfully added to student number
            }
        }
        return false;
    }

    /**
     * Returns a student's login id (if exists in the list) using their student number as a parameter for searching.
     * If student number is not matched, returns that student cannot be found.
     * @param studentNum student number
     * @return login id of matching student number or returns no match found
     */
    public String getLoginId(int studentNum) {
        for (int i = 0; i < students.size(); i++) {
            Student a = students.get(i);
            if (a.getStudentNum() == studentNum) { // returns login id of student if student number argument matches
                return a.getLoginId();
            }
        }
        return "No match for student number found";
    }

    /**
     * Returns a statement showing the name and GPA of the student with the highest GPA in the college.
     * @return statement for student's name with highest GPA and their respective GPA
     */
    public String getHighestGPA() {
        double max = 0;
        String name = "";
        for (Student a : students) {
            if (a.calculateGPA() > max) {
                max = a.calculateGPA();
                name = a.getName();
            }
        }
        return "Student with the college's highest GPA is '" + name + "' with a GPA of '" + max + "'.";
    }

    /**
     * Provides statement of all the students in the College, along with each student's information
     * @return a string in the format Students in College = [(name1,studentNum1,address1,login_id1,GPA1),(name2,studentNum2...etc.)]
     */
    public String toString() {
        return "Students in College = " + students.toString();
    }
}

