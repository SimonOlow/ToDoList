/* ToDoList class 
 * --- This class is the controller part of the ToDoList application.
 * --- This class methods are invoked by view part that is the User class
 */

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ToDoList {

	private List<Task> listOfTasks;
	private List<Project> listOfProjects;
	private FileHandler fileHandler;
	private Scanner scan2 = new Scanner(System.in);
	private boolean quit = false;
	private String tmpTaskName;
	private String msg;
	private Task oldTask = new Task();
	private Task newTask = new Task();

	public ToDoList() {
		this.setListOfTasks(new ArrayList<Task>());
		this.setListOfProjects(new ArrayList<Project>());
		this.fileHandler = new FileHandler();
	}

	public void setListOfTasks(List<Task> taskList) {
		this.listOfTasks = taskList;
	}

	public void setListOfProjects(List<Project> listOfProjects) {

		this.listOfProjects = listOfProjects;
	}

	public List<Task> getListOfTasks() {
		return this.listOfTasks;
	}

	public List<Project> getListOfProjects() {
		return this.listOfProjects;
	}

	public void addProjects(Project project) {
		listOfProjects.add(project);
	}
/* chkProjects()
 * --- this method is used to check for the valid project name
 * --- this method accepts a string value as input parameter and returns the object of Project class
 * --- this method is invoked when a user inputs project name while creating/adding the task to the project
 */

	public Project chkProject(String projectName) {

		Project tmpProject = null;
		Iterator<Project> iterator = listOfProjects.iterator();
		while (iterator.hasNext()) {
			tmpProject = (Project) iterator.next();
			if (tmpProject.getProjectName().equals(projectName)) {
				return tmpProject;
			}
		}
		return null;
	}
	
/* loadUserTask()
 * --- this method is used to find the number of complete and pending tasks for a user
 * --- this method is invoked to display the total number of tasks are completed / pending 
 * --- this method is invoked while displaying available options in Main menu
 */
	public void loadUserTask(User user) {
		List<Task> fileContent = fileHandler.readFromFile();
		int completedTask = 0;
		int incompleteTask= 0;
		if (fileContent.size() != 0) {
			for (int i = 0; i <= fileContent.size() - 1; i++) {
				String[] taskDetails = fileContent.get(i).toString().split(",");
				if(taskDetails[2].equals(Constants.END_STATUS)) {
					completedTask++;
				}else{
					incompleteTask++;
				}
			}
			System.out.println(user.getUserName()+" you have "+incompleteTask+" tasks todo and "+completedTask+" tasks are done!");
		} else {
			System.out.println("No tasks available ");
		}

	}
/* addTask()
 * --- This method is used to add a task with the supplied details and write the same in to a file
 * --- this method accepts 3 string values task title,taskDate and project name 
 * --- this method returns a boolean value indicating adding of a task is success or not
 * 
 */
	public boolean addTask(String taskName, Date taskDate, Project project) {
		boolean chkFlag = false;
		fileHandler = new FileHandler();
		Task task = new Task(taskName, taskDate, project);

		// write the contents of listOfTasks to file here
		String fileWriteStr = fileHandler.writeToFile(task);
		if ((fileWriteStr.split(":")[0].equals(Constants.SUCCESS))) {
			chkFlag = true;
		}
		return chkFlag;
	}
	
/* showTaskListByDate()
 * --- this method is used to show the list of tasks sorted by date
 * --- this method uses the sort() method of Collections class to sort the tasks by date
 */
	public void showTaskListByDate() {
		List<Task> fileContentDate = fileHandler.readFromFile();
		if (fileContentDate.size() != 0) {
			Collections.sort(fileContentDate, Task.DateComparator);
			printTask(fileContentDate);
		} else {
			System.out.println("No tasks available ");
		}
	}

/* showTaskListByProject()
 * --- this method is used to show the list of tasks sorted by project name
 * --- this method uses the Collections class sort() method to sort the tasks by project
 * 	
 */
	public void showTaskListByProject() {
		List<Task> fileContentProject = fileHandler.readFromFile();
		if (fileContentProject.size() != 0) {
			Collections.sort(fileContentProject, Task.ProjectComparator);
			printTask(fileContentProject);
		} else {
			System.out.println("No tasks available ");
		}
	}
/* printTask() 
 * --- this method is used by showTaskListByDate() & showTaskListByProject() operations to show the list of tasks
 */
	private void printTask(List<Task> sortedTaskDets) {
		System.out.println("---------------------------------------------------------------------------------------\n");
		System.out.println("Task title\tTaskDueDate\tStatus\tProject Name \n");
		System.out
				.println("---------------------------------------------------------------------------------------- \n");
		for (int i = 0; i <= sortedTaskDets.size() - 1; i++) {
			String[] taskDetails = sortedTaskDets.get(i).toString().split(",");
			System.out.print(taskDetails[0] + "\t        ");
			System.out.print(taskDetails[1] + "\t");
			System.out.print(taskDetails[2] + "\t");
			System.out.print(taskDetails[3]);
			System.out.println();
		}
	}

/* editTask()
 * --- this method provides the menu details to edit a task
 * --- this operation accepts project name in which the task to modified by title/due date/status and write back the details in to file
 * --- this method provides an option to remove a task from the project
 * --- this method provides an option to go back to the main menu
 */
	public void editTask() {

		System.out.println("In which project task to be modified ? \n\nEnter the project name : ");
		String editProjectName = scan2.next();

		Project project = chkProject(editProjectName);

		while (project == null) {
			System.out.println("Enter valid project name ");
			editProjectName = scan2.next();
			project = chkProject(editProjectName);
		}

		listOfTasks = fileHandler.searchInFile(editProjectName);
		for (Task t : listOfTasks) {
			System.out.println(t.toString());
		}
		System.out.println();
		System.out.println("Enter the title of the task to be modified");
		tmpTaskName = scan2.next();
		System.out.println();

		editMenu();
		while (!quit) {
			System.out.println("\nEnter your option: ");
			int choice = scan2.nextInt();

			for (Task tmpTask : listOfTasks) {
				if (tmpTask.getTaskTitle().trim().equals(tmpTaskName.trim())) {
					oldTask = tmpTask;
					newTask = tmpTask;
				}
			}

			switch (choice) {

			case 1:
				System.out.println("Ente new title:");
				String title = scan2.next();
				newTask.setTitle(title);
				msg = update(oldTask, newTask, listOfTasks, editProjectName);
				System.out.println(msg);
				break;
			case 2:
				System.out.println("Enter new date(MM/dd/yyyy):");
				String date = scan2.next();
				Date newDate = null;
				;
				try {
					newDate = DateValidator.isThisDateValid(date);
					while (newDate == null) {
						System.out.println("Enter the correct date in (MM/dd/yyyy) format :");
						newDate = DateValidator.isThisDateValid(scan2.next());
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				newTask.setDueDate(newDate);
				msg = update(oldTask, newTask, listOfTasks, editProjectName);
				System.out.println(msg);
				break;
			case 3:
				System.out.println("Please enter the new status(Progress or Done):");
				String status = scan2.next();

				if (status.equalsIgnoreCase("Progress")) {
					newTask.setStatus(Constants.PROGRESS_STATUS);
				} else if (status.equalsIgnoreCase("Done")) {
					newTask.setStatus(Constants.END_STATUS);
				}
				msg = update(oldTask, newTask, listOfTasks, editProjectName);
				System.out.println(msg);
				break;
			case 4:
				msg = removeTask(tmpTaskName, listOfTasks);
				System.out.println(msg);
				break;
			case 5:
				quit = true;
				break;
			}
		}

	}
// editMenu() - contains the menu holding list of options to edit a task 
	public void editMenu() {
		System.out.println("Edit Menu: \n" + "----------------\n" + "Press 1 to change task title \n"
				+ "Press 2 to change due date \n" + "Press 3 to change status \n" + "Press 4 to remove task \n"
				+ "Press 5 to go to Main menu \n");
	}

// removeTask() - this operation is used to remove a task from the project and update the file accordingly
	public String removeTask(String str, List<Task> listOfTasks) {
		Iterator<Task> iterator = listOfTasks.iterator();
		while (iterator.hasNext()) {
			if (iterator.next().getTaskTitle().equals(str)) {
				iterator.remove();
			}
		}
		String msg = fileHandler.writeToFile(listOfTasks);
		return "Remove of task is " + msg;
	}
// update() - this method is invoked whenever user modifies the title,status,duedate to upate the changes in to file
	public String update(Task oldTask, Task newTask, List<Task> listOfTasks, String projectName) {
		int index = -1;
		for (Task t : listOfTasks) {
			if (t.equals(oldTask))
				index = listOfTasks.indexOf(oldTask);
		}
		listOfTasks.set(index, newTask);
		String retStr = fileHandler.writeToFile(listOfTasks);
		return "Updating of the file is " + retStr;

	}

}
