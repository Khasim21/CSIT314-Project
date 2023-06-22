package proj2;

import java.util.Scanner;

class User {
    private String username;
    private String password;
    private String email;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

class Student extends User {
    private String studentID;

    public Student(String username, String password, String email, String studentID) {
        super(username, password, email);
        this.studentID = studentID;
    }
    public String getStudentId() {
        return studentID;
    }

    public void setStudentId(String studentId) {
        this.studentID = studentId;
    }
}

class Teacher extends User {
    private String teacherID;

    public Teacher(String username, String password, String email, String teacherID) {
        super(username, password, email);
        this.teacherID = teacherID;
    }
    public String getTeacherId() {
        return teacherID;
    }

    public void setTeacherId(String teacherId) {
        this.teacherID = teacherId;
    }
}

class Parent extends User {
    private String phoneNumber;

    public Parent(String username, String password, String email, String phoneNumber) {
        super(username, password, email);
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

class Application {
    private String name;
    private String type;
    private String author;

    public Application(String name, String type, String author) {
        this.name = name;
        this.type = type;
        this.author = author;
    }
    

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	 public void setName(String name) {
	        this.name = name;
	    }
	 public void setType(String type) {
	        this.type = type;
	    }

	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
	public void setAuthor(String author) {
        this.author = author;
    }
	public String getAuthor() {
		// TODO Auto-generated method stub
		return author;
	}
}

class Utility {
    private Student[] students;
    private Teacher[] teachers;

    public Utility(Student[] students, Teacher[] teachers) {
        this.students = students;
        this.teachers = teachers;
    }
}
    
public class iLearnSystem {
    private User[] users;
    private Student[] students;
    private Teacher[] teachers;
    private Parent[] parents;
    private Application[] applications;
    private Utility utility;

    private int userIndex;
    private int studentIndex;
    private int teacherIndex;
    private int parentIndex;
    private int appIndex;

    public iLearnSystem() {
        users = new User[5];
        students = new Student[5];
        teachers = new Teacher[5];
        parents = new Parent[5];
        applications = new Application[5];
        utility = new Utility(students,teachers);

        userIndex = 0;
        studentIndex = 0;
        teacherIndex = 0;
        parentIndex = 0;
        appIndex = 0;
    }

    public boolean login(String username, String password) {
        for (User user : users) {
            if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void signUp(String username, String password, String email, String role) {
        int index = getNextIndex(role);
        if (index != -1) {
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Sign up failed.");
                return;
            }

            switch (role.toLowerCase()) {
                case "student":
                    String studentID = "S" + studentIndex;
                    students[studentIndex] = new Student(username, password, email, studentID);
                    users[userIndex] = students[studentIndex];
                    studentIndex++;
                    break;
                case "teacher":
                    String teacherID = "T" + teacherIndex;
                    teachers[teacherIndex] = new Teacher(username, password, email, teacherID);
                    users[userIndex] = teachers[teacherIndex];
                    teacherIndex++;
                    break;
                case "parent":
                    String phoneNumber = "123456789" + parentIndex;
                    parents[parentIndex] = new Parent(username, password, email, phoneNumber);
                    users[userIndex] = parents[parentIndex];
                    parentIndex++;
                    break;
                default:
                    System.out.println("Invalid role. Sign up failed.");
                    return;
            }

            System.out.println("Signed up successfully as a " + role + ".");
            userIndex++;
        } else {
            System.out.println("No available space for new users. Sign up failed.");
        }
    }

    public void addApplication(User user, String name, String type, String author) {
        // Check user role and add application accordingly
        if (user instanceof Teacher) {
            applications[appIndex] = new Application(name, type, author);
            System.out.println("Application added successfully.");
            appIndex++;
        } else if (user instanceof Student) {
            if (type.equalsIgnoreCase("student")) {
                applications[appIndex] = new Application(name, type, author);
                System.out.println("Application added successfully.");
                appIndex++;
            } else {
                System.out.println("Students can only add student applications.");
            }
        } else {
            System.out.println("Parents cannot add any applications.");
        }
    }
    public void removeApplication(User user, String name) {
        boolean found = false;
        for (int i = 0; i < appIndex; i++) {
            if (applications[i] != null && applications[i].getName().equalsIgnoreCase(name)) {
                if ((user instanceof Teacher) || (user instanceof Student && applications[i].getType().equalsIgnoreCase("student"))) {
                    applications[i] = null;
                    found = true;
                    System.out.println("Application removed successfully.");
                    break;
                } else {
                    System.out.println("You don't have the permission to remove this application.");
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("Application not found.");
        }
    }

    private int getNextIndex(String role) {
        int index = -1;
        switch (role.toLowerCase()) {
            case "student":
                index = studentIndex;
                break;
            case "teacher":
                index = teacherIndex;
                break;
            case "parent":
                index = parentIndex;
                break;
        }
        return index < 5 ? index : -1;
    }

    private boolean isValidEmail(String email) {
        return email.contains("@") && email.endsWith(".com");
    }
    
    

        public void viewStudentInfo(String studentName) {
            boolean found = false;
            for (Student student : students) {
                if (student != null && student.getUsername().equalsIgnoreCase(studentName)) {
                    System.out.println("Student Name: " + student.getUsername());
                    System.out.println("Email: " + student.getEmail());
                    System.out.println("Student ID: " + student.getStudentId());
                    System.out.println("Applications:");
                    
                    // Print the applications the student has
                    boolean hasApplications = false;
                    for (Application application : applications) {
                        if (application != null && application.getType().equalsIgnoreCase("student")) {
                            System.out.println("- Name: " + application.getName());
                            System.out.println("  Type: " + application.getType());
                            System.out.println("  Author: " + application.getAuthor());
                            hasApplications = true;
                        }
                    }
                    
                    if (!hasApplications) {
                        System.out.println("No applications found.");
                    }
                    
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Error: No student found with the name " + studentName);
            }
        }

        public void viewTeacherInfo(String teacherName) {
            boolean found = false;
            for (Teacher teacher : teachers) {
                if (teacher != null && teacher.getUsername().equalsIgnoreCase(teacherName)) {
                    System.out.println("Teacher Name: " + teacher.getUsername());
                    System.out.println("Email: " + teacher.getEmail());
                    System.out.println("Teacher ID: " + teacher.getTeacherId());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Error: No teacher found with the name " + teacherName);
            }
        }
    

    public static void main(String[] args) {
        iLearnSystem system = new iLearnSystem();
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Log in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();

                    boolean loggedIn = false;
                    for (User user : system.users) {
                        if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                            loggedIn = true;
                            break;
                        }
                    }

                    if (loggedIn) {
                        System.out.println("Login successful.");

                        // Perform actions for the logged in user
                        User user = null;
                        for (User u : system.users) {
                            if (u != null && u.getUsername().equals(username)) {
                                user = u;
                                break;
                            }
                        }

                        if (user instanceof Teacher) {
                            System.out.println("Welcome, Teacher!");
                        } else if (user instanceof Student) {
                            System.out.println("Welcome, Student!");
                        } else if (user instanceof Parent) {
                            System.out.println("Welcome, Parent!");
                        }

                        boolean loggedInMenu = true;
                        while (loggedIn) {
                            System.out.println("\n1. Add Application");
                            System.out.println("2. Remove Application");
                            System.out.println("3. View Student Information");
                            System.out.println("4. View Teacher Information");
                            System.out.println("5. Log out");
                            System.out.print("Enter your choice: ");
                            int userChoice = scanner.nextInt();

                            switch (userChoice) {
                                case 1:
                                    scanner.nextLine(); // Consume newline character
                                    System.out.print("Name: ");
                                    String appName = scanner.nextLine();
                                    System.out.print("Type (Student/Teacher): ");
                                    String appType = scanner.nextLine();
                                    System.out.print("Author: ");
                                    String appAuthor = scanner.nextLine();

                                    system.addApplication(user, appName, appType, appAuthor);
                                    break;
                                case 2:
                                    scanner.nextLine(); // Consume newline character
                                    System.out.print("Name of application to remove: ");
                                    String appToRemove = scanner.nextLine();

                                    system.removeApplication(user, appToRemove);
                                    break;
                                case 3:
                                    scanner.nextLine(); // Consume newline character
                                    System.out.print("Enter the student's name: ");
                                    String studentName = scanner.nextLine();

                                    system.viewStudentInfo(studentName);
                                    break;
                                case 4:
                                    scanner.nextLine(); // Consume newline character
                                    System.out.print("Enter the teacher's name: ");
                                    String teacherName = scanner.nextLine();

                                    system.viewTeacherInfo(teacherName);
                                    break;
                                case 5:
                                    loggedIn = false;
                                    System.out.println("Logged out successfully");
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please enter a valid option.");
                                    break;
                            }
                        }

                    } else {
                        System.out.println("Invalid username or password. Please try again.");
                    }
                    break;
                case 2:
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Password: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Email: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Role (Student/Teacher/Parent): ");
                    String newRole = scanner.nextLine();

                    system.signUp(newUsername, newPassword, newEmail, newRole);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }

        scanner.close();
    }
}
