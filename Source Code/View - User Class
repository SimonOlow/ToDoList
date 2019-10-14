/* User Class 
 * --- This class lists available menu for ToDolist application.
 * --- It is invoked by main method ,this is the view class of the ToDolist application
 */


import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class User {
	private String userName;
	private static ToDoList toDoList;
	private static Scanner scan1 = new Scanner(System.in);
	private static Scanner scan2 = new Scanner(System.in);

	public User(String name) {
		this.userName = name;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
// statToDoApp - this method is invoked by main method of the ToDolist application
	public void startToDoApp(User user) {
		toDoList = new ToDoList();
		boolean quit = false;

		Project project1 = new Project("Daily");
		Project project2 = new Project("Hospital");
		Project project3 = new Project("School");
		Project project4 = new Project("Trip");
		Project project5 = new Project("Hobby");

		toDoList.addProjects(project1);
		toDoList.addProjects(project2);
		toDoList.addProjects(project3);
		toDoList.addProjects(project4);
		toDoList.addProjects(project5);
		
		
		System.out.println("------------------------- Welcome to ToDoList Applciation ------------------------\n");
		toDoList.loadUserTask(user);
		System.out.println("\n Main menu : ");
		System.out.println("----------------");
		showMenu();
		while (!quit) {

			System.out.println("\nEnter your option from main menu : ");
			int option = scan1.nextInt();
			switch (option) {
			case 1:
				showProjectList();
				break;
			case 2:
				showTaskList();
				break;
			case 3:
				addTask();
				break;
			case 4:
				editTask();
				break;
			case 5:
				showMenu();
				break;
			case 6:
				quit = true;
			}

		}

		scan1.close();
		scan2.close();
	}
// showMenu() - This method shows the list of options in a menu 
	public static void showMenu() {

		System.out.println(">> Available options:\n" + ">> (1) Show Project List\n"
				+ ">> (2) Show Task List (by date or project)\n"
				+ ">> (3) Add New Task\n" + ">> (4) Edit Task (update, mark as done, remove)\n"
				+ ">> (5) Show available Options\n" + ">> (6) Save and Quit");
	}
	
// showProjectList() - this method shows the list of available projects
	public static void showProjectList() {
		System.out.println("List of Projects are :");
		System.out.println("-----------------------");
		toDoList.getListOfProjects();
		for(Project project:toDoList.getListOfProjects()) {
			System.out.println(project.getProjectName());
		}
	}
/* addTask() 
 * --- This method is used create or add an task to the project. This method accepts task name,project name & duedate from user
 * --- Validates the date entered by the user against todays date 
 * --- validates the project name 
 * --- shows appropriate messages if any errors occurs during validation of the data
 */
	public static void addTask() {

		System.out.println("Enter the task name: ");
		String taskName = scan2.nextLine();

		System.out.println("Which project the task belongs to:");
		String projectName = scan2.next();
		Project project = toDoList.chkProject(projectName);
		while (project == null) {
			System.out.println(projectName + " is invalid project name, enter a valid project name:  ");
			projectName = scan2.next();
			project = toDoList.chkProject(projectName);
		}

		System.out.println("Enter the due date(MM/dd/yyyy): ");
		String dateToValidate = scan2.next();
		Date taskDate = null;

		try {
			taskDate = DateValidator.isThisDateValid(dateToValidate);
			while (taskDate == null) {
				System.out.println("Enter the date greater than current date :");
				taskDate = DateValidator.isThisDateValid(scan2.next());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (taskDate != null && project != null) {
			if (toDoList.addTask(taskName, taskDate, project)) {
				System.out.println("Task is added successfully..");
			} else {
				System.out.println("Task is not added !!!");
			}
		}

	}
/* showTaskList() 
 * --- This method shows list of tasks
 * --- user can view tasks list sorted by date
 * --- user can view tasks lists sorted by project
 */
	public static void showTaskList() {
		System.out.println(
				"Press 1 - to view tasks list sorted by date" + "\nPress 2 - to view tasks lists sorted by project");
		int like = scan1.nextInt();
		switch (like) {
		case 1:
			System.out.println("Tasks list sorted by Date");
			toDoList.showTaskListByDate();
			break;
		case 2:
			System.out.println("Tasks list sorted by Project");
			toDoList.showTaskListByProject();
			break;
		}

	}
// editTask() - This method is used to perform the edit operation on a task
	public static void editTask() {
		toDoList.editTask();
	}
// saveAndQuit() - This method is invoked by main method at the end of the application usuage
	public  void saveAndQuit() {
		System.out.println("Exiting from ToDoList application ... Thank you :)");
	}
}
