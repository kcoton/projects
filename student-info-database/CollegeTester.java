import java.util.Scanner;

/** This program tests College.java by creating multiple students and running each function provided
 *  in the menu to demonstrate the various capabilities of accessing each student's information.
 *  Functions tested include creating new students for the college, looking up an existing student,
 *  deleting a student, retrieving login id, highest GPA, and modifying course grades / credits earned.
 */

public class CollegeTester {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        College langaraCollege = new College();

        // manually adding students with id 10001 - 10005, and corresponding grades to use for the example
        langaraCollege.addStudent(new Student("John Smith", "140 Georgian Bay"));
        langaraCollege.addStudent(new Student("Clark Kent", "72 Small Ville"));
        langaraCollege.addStudent(new Student("Louis Lane"));
        langaraCollege.addStudent(new Student("Barry Allen"));
        langaraCollege.addStudent(new Student("Dick Grayson", "100 Townsville"));
        langaraCollege.addCourse(10001, 60, 3);
        langaraCollege.addCourse(10001, 50, 2);
        langaraCollege.addCourse(10001, 78, 3);
        langaraCollege.addCourse(10002, 80, 2);
        langaraCollege.addCourse(10002, 83, 3);
        langaraCollege.addCourse(10003, 82, 2);
        langaraCollege.addCourse(10003, 62, 3);
        langaraCollege.addCourse(10004, 70, 2);
        langaraCollege.addCourse(10005, 78, 3);
        langaraCollege.addCourse(10005, 73, 2);

        // toString method available to show the list of all the information and students in the string:
        // System.out.println(langaraCollege.toString() + "\n");

        // interactive menu starts
        System.out.println("Welcome to College! Select an option to modify the student directory.");

        while (true) {
            menuText(); // shows available menu options and what each option does
            int studentNum;
            String options = input.next();
            switch (options) { // number entered goes to different functions
                case "0":
                    System.out.print("Exiting College.");
                    System.exit(0);
                case "1":
                    System.out.print("Adding a new student. Enter student's first and last name separated by a space: ");
                    String first = input.nextLine();
                    String last = input.nextLine();
                    String full_name = (first + " " + last).trim();
                    if (isName(first) && isName(last) && full_name.contains(" ")) { // if name is valid all letters and contains at least 1 space
                        System.out.println("Enter address if applicable, if not, leave blank and enter: ");
                        String address = input.nextLine();
                        if (address.isEmpty()) {
                            langaraCollege.addStudent(new Student(full_name)); // if address is empty, use name only constructor in Student
                        } else
                            langaraCollege.addStudent(new Student(full_name, address)); // else use name and address constructor in Student
                        System.out.println("Student '" + full_name + "' added.");
                    }
                    else System.out.println("Error: Student name is invalid. Must contain only letters separated by at least one space.");
                    break;
                case "2":
                    System.out.print("Look up an existing student. Enter student number: ");
                    String studentNumStr = input.next();
                    if (isValidNum(studentNumStr)) {
                        studentNum = Integer.parseInt(studentNumStr);
                        langaraCollege.findStudent(studentNum); // if student found with student id, print information
                    }
                    else errorStudentNum(); // returns error
                    break;
                case "3":
                    System.out.print("Delete a student from the college. Enter student number: ");
                    studentNumStr = input.next();
                    if (isValidNum(studentNumStr)) {
                        studentNum = Integer.parseInt(studentNumStr);
                        langaraCollege.deleteStudent(studentNum);
                    }
                    else errorStudentNum(); // returns error
                    break;
                case "4":
                    System.out.print("Add a course grade and credits earned. Enter the student number: ");
                    studentNumStr = input.next();
                    if (isValidNum(studentNumStr)) {
                        studentNum = Integer.parseInt(studentNumStr);
                        System.out.print("Enter the grade (0-100): ");
                        String gradeStr = input.next();
                        if (isValidDouble(gradeStr)) { // verifies grade is a valid number b/w 0-100
                            double grade = Double.parseDouble(gradeStr);
                            if (grade < 0 || grade > 100) {
                                System.out.println("Error: Grade input is not a valid number, must be number between 0 and 100.");
                                break;
                            }
                            System.out.print("Enter the credits earned: ");
                            String creditStr = input.next();
                            if (isValidDouble(creditStr)) { // verifies credit is a valid number
                                double credit = Double.parseDouble(creditStr);
                                if (credit <= 0) {
                                    System.out.println("Error: Credit input is not a valid number, must be number greater than 0.");
                                    break;
                                }
                                if (langaraCollege.addCourse(studentNum, grade, credit)) { // if returns true, course added successfully
                                    System.out.println("Course grade and credits added to student '" + studentNum + "'.");
                                }
                            }
                        }
                        else System.out.println("Student '" + studentNum + "' not found."); // else student number didn't match anything
                    }
                    else errorStudentNum(); // returns error
                    break;
                case "5":
                    System.out.print("Retrieve login id. Enter the student number: ");
                    studentNumStr = input.next();
                    if (isValidNum(studentNumStr)) {
                        studentNum = Integer.parseInt(studentNumStr);
                        System.out.println("Student number '" + studentNum + "' returns login id '" + langaraCollege.getLoginId(studentNum) + "'.");
                    }
                    else errorStudentNum(); // returns error
                    break;
                case "6":
                    System.out.println(langaraCollege.getHighestGPA());
                    break;
                default:
                    System.out.println("Input Error: Please enter an option on the menu.");
                    break;
            }
        }
    }

    /** Shows available functions and what each function does
     *  @return menu options for the main program
     */
    public static void menuText() {
        System.out.println("""
                    
                    Enter '1' to add a new student.
                    Enter '2' to look up an existing student based on their student number.
                    Enter '3' to delete a student from the college using their student number.
                    Enter '4' to add a grade point value and credit value earned for a course taken by an existing student using their student number.
                    Enter '5' to retrieve the login id for an existing student using their student number.
                    Enter '6' to find the student with the college's highest GPA.
                    Enter '0' to quit.
                    """);
    }

    /**
     * @return error message for if student number is not a valid integer or greater than 10000
     */
    public static void errorStudentNum() {
        System.out.println("Error: Student number is not a valid integer or is not greater than 10000");
    }

    /**
     * Parses studentNum to integer to see if it is a valid input and checks if it is within the minimum of valid student numbers
     * @param studentNum student number as a string
     * @return true or false if studentNum is a valid integer/possible student number
     */
    public static boolean isValidNum(String studentNum) {
        try {
            int checkNum = Integer.parseInt(studentNum);
            if (checkNum > 10000) {
                return true;
            }
        } catch (NumberFormatException e) { // catches if parseInt fails meaning it's not all numbers
            return false;
        }
        return false;
    }

    /**
     * Parses num to double to see if it is a valid number
     * @param num number as a string
     * @return true or false if number is a valid integer
     */
    public static boolean isValidDouble(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) { // catches if parseInt fails meaning it's not all numbers
            return false;
        }
    }

    /**
     * Checks if name entered by user is valid.
     * @param name name of student
     * @return true if name contains only letters or spaces, false otherwise
     */
    public static boolean isName(String name) {
        char[] letters = name.toCharArray(); // puts string into char array to individually check if contains all letters
        for (char c : letters) {
            if (!Character.isLetter(c) && c != ' ') { // if not letter, returns false
                return false;
            }
        }
        return true;
    }
}
