/** This program allows creation of a student record that stores each student's name, address, unique number
 *  login id, GPA, etc. and allows user to get, create, and modify the information of a specified student.
 *
 *  Note: Letter grades are calculated based on the percentage-to-grade table given in the course outline for CPSC 1181
 *  and GPA values are provided based on the letter to grade point conversion using: http://back2college.com/gpa.htm
 *  with the only changes being A+ = 4.00 and A = 3.90, the rest of the grades being the same as the table conversion
 */

public class Student {
    private String name, address, login_id;
    private int studentNum;
    private static int student_numbers = 10000; // student numbers start at 10001 and increment by 1
    private double totalCredits, totalPoints, GPA;

    /** Constructor creates new Student object using name parameter, assigns a student number, login id, and initializes their grades/credits.
     *  @param newStudent student's name
     */
    public Student(String newStudent) { // constructor taking only student's name
        name = newStudent.trim();
        student_numbers++;
        studentNum = student_numbers;
        login_id = getLoginId();
        totalPoints = 0;
        totalCredits = 0;
    }

    /** Constructor creates new Student object using name and address parameter, assigns a student number, login id, and initializes their grades/credits.
     *  @param newStudent student's name
     *  @param newAddress address of new student
     */
    public Student(String newStudent, String newAddress) { // constructor taking student's name and address
        name = newStudent.trim();
        student_numbers++;
        studentNum = student_numbers;
        login_id = getLoginId();
        totalPoints = 0;
        totalCredits = 0;
        address = newAddress;
    }

    /** No argument method returns the student's full name.
     *  @return name of student
     */
    public String getName() { // returns student's name
        return name;
    }

    /** No argument method returns the student's unique number.
     *  @return student number of student
     */
    public int getStudentNum() {
        return studentNum;
    }

    /** No argument method returns the student's address -- if none is entered, returns N/A.
     *  @return address of student
     */
    public String getAddress() { // returns student's address if applicable, otherwise returns N/A
        if (address == null) {
            return "N/A";
        }
        return address;
    }

    /** Allows user to add a course to the student's overall transcript record, using parameters of their grade and amount of credits the course was worth.
     *  Using these arguments, they are calculated based on the 4.00 GPA and the total points are added to the student's record.
     *  @param gradePoint in terms of 0-100
     *  @return totalPoint calculation for GPA
     */
    public void addCourse (double gradePoint, double credits) { // adds course grade points and credits towards GPA
        totalCredits += credits;
        if (gradePoint >= 90) { // A+ = 4.00
            totalPoints += 4.00 * credits;
        } else if (gradePoint >= 85) { // A = 3.90
            totalPoints += 3.90 * credits;
        } else if (gradePoint >= 80) { // A- = 3.70
            totalPoints += 3.70 * credits;
        } else if (gradePoint >= 76) { // B+ = 3.33
            totalPoints += 3.33 * credits;
        } else if (gradePoint >= 72) { // B = 3.00
            totalPoints += 3.00 * credits;
        } else if (gradePoint >= 68) { // B- = 2.70
            totalPoints += 2.70 * credits;
        } else if (gradePoint >= 64) { // C+ = 2.30
            totalPoints += 2.30 * credits;
        } else if (gradePoint >= 60) { // C = 2.00
            totalPoints += 2.00 * credits;
        } else if (gradePoint >= 55) { // C- = 1.70
            totalPoints += 1.70 * credits;
        } else if (gradePoint >= 50) { // D = 1.00
            totalPoints += credits;
        } else { // F = 0.00
            totalPoints += 0;
        }
    }

    /** No argument method returns the student's overall grade point average according to the courses that have been added.
     * @return calculated GPA using totalPoints/totalCredits rounded to 2 decimal places
     */
    public double calculateGPA () { // calculates GPA using 4.00 grade scale, percentage, and credits taken
        return Math.round((totalPoints/totalCredits) * 100.00) / 100.00;
    }

    /** No argument method generates an automatic login id for the student using the first letter of their first name,
     *  up to 3 letters of their last name, and the last 2 digits of their student number. Their generated student number string is returned.
     *  @return generated login_id
     */
    public String getLoginId() {
        // first letter of the login id
        char first_letter = name.toLowerCase().charAt(0);
        // next 3 or less letters of the login id
        int index = name.lastIndexOf(' ');
        String last_name = name.toLowerCase().substring(index+1);
        if (last_name.length() > 3) { // if last name is > 3 letters, takes the first 3 letters
            last_name = name.toLowerCase().substring(index+1, index+4);
        }
        // last 2 numbers of the login id
        String num;
        num = String.valueOf(studentNum); // converts the assigned student number into a string
        num = num.substring(num.length()-2); // gets the last 2 digits of student number

        return login_id = first_letter + last_name + num;
    }

    /** Gives a text representation of the College student list.
     *  @return a string in the format College = [(name1,studentNum1,address1,login_id1,GPA1),(name2,studentNum2...etc.)]
     */
    @Override
    public String toString()
    {
        return "\n(" + name + "," + studentNum + "," + address + "," + login_id + "," + calculateGPA() + ")";
    }
}
