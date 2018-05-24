package Model.company;


import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable{

    private String name;
    private ArrayList<Employee> listEmployees = new ArrayList<>();
    private ArrayList<Manager> listManagers = new ArrayList<>();

    //<editor-fold desc = "Constructors">

    /**
     * This constructor is only here for test, he will be removed at the end
     * Constructor with name
     * @param name String
     * @throws Exception if the name is null
     */
    public Department(String name) throws Exception {
        if  (name == null)
            throw new Exception("argument null");
        this.name = name;
    }

    /**
     * Constructor with a name and a manager
     * @param name String
     * @param manager this Manager is the chief of this department
     * @throws Exception if arguments are null
     */
    public Department(String name, Manager manager) throws Exception {
        if (manager == null || name == null)
            throw new Exception("null arguments");
        this.name = name;
        manager.setChief(true);
        listManagers.add(manager);
    }

    /**
     * Constructor without parameters
     */
    public Department(){
        name = null;
        listEmployees = null;
    }


    //</editor-fold>

    //<editor-fold desc = "Get and Set">


    public ArrayList<Employee> getListEmployees() {
        return listEmployees;
    }

    public ArrayList<Manager> getListManagers() {
        return listManagers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    //</editor-fold>

    //<editor-fold desc = "Method">

    /**
     * search if an employee is in this department
     * @param employee an Employee
     * @return true if the employee is already in this department, else return false
     * @throws Exception if the employee is null
     */
    public boolean isEmployeeIn(Employee employee) throws Exception {
        if (employee == null)
            throw new Exception("arguments null");
        for (Employee e : listEmployees){
            if (e.getId() == employee.getId())
                return true;
        }
        return false;
    }

    /**
     * remove an Employee of the department
     * if the employee isn't in the department this method does nothing
     * @param employee an Employee
     */
    public void removeEmployee(Employee employee){
        int counter = 0;
        for (Employee e : listEmployees) {
            if (e.getId() == employee.getId()){
                listEmployees.remove(counter);
                return ;
            }
            counter++;
        }
    }

    /**
     * search if a manager is in this department
     * @param manager a Manager
     * @return true if the manager is already in this department, else return false
     */
    public boolean isManagerIn(Manager manager){
        for (Manager m : listManagers){
            if (m.getId() == manager.getId())
                return true;
        }
        return false;
    }

    /**
     * remove a manager from the department
     * if the manager isn't in the department this method does nothing
     * @param manager a Manager
     * @throws Exception if the manager is the chief of the department
     */
    public void removeManager(Manager manager) throws Exception {
        int counter = 0;
        for (Manager m : listManagers) {
            if (m.getId() == manager.getId()){
                if (m.isChief())
                    throw new Exception("Department : removeManager : We can't remove the chief of a department");
                listManagers.remove(counter);
                return ;
            }
            counter++;
        }
    }

    /**
     * add an employee to a department
     * @param employee an Employee
     * @throws Exception if arguments are null, or if the employee is already in this department
     */
    public void addEmployee(Employee employee) throws Exception {
        if (employee == null)
            throw new Exception("null arguments");
        if (isEmployeeIn(employee))
            throw new Exception("this employee is already in this department");
        listEmployees.add(employee);
    }

    /**
     * add a manager to a Department
     * if this manager was a chief he won't be anymore
     * if you want to change the chief of a department use the method changeChief(Manager)
     * @param manager a Manager
     * @throws Exception if arguments are null, or if the manager is already in this department
     */
    public void addManager(Manager manager) throws Exception {
        if (manager== null)
            throw new Exception("null arguments");
        if (isManagerIn(manager))
            throw new Exception("this employee is already in this department");
        manager.setChief(false);
        listManagers.add(manager);
    }

    /**
     * Change the chief of a department.
     * the old chief won't be the chief of this department anymore.
     * @param manager The Manager which will be the new chief of the department
     * @throws Exception if the argument is null, or if the manager isn't in this department
     */
    public void changeChief(Manager manager) throws Exception {
        if (manager == null )
            throw new Exception("Argument null");
        if (!isManagerIn(manager))
            throw new Exception("this Manager isn't in this department");
        for (Manager m : listManagers) {
            if (m.isChief()){
                m.setChief(false);
                break;
            }
        }
        manager.setChief(true);
    }

    //</editor-fold>

}
