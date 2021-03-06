package Model.company;

import Model.Time.Check;
import Model.Time.DateAndTime;

import java.io.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.BufferedReader;

public class Company implements Serializable {

    private String name;
    private ArrayList<Department> listDepartment = new ArrayList<>();
    private ArrayList<Employee> listEmployees = new ArrayList<>();
    private ArrayList<Manager> listManagers = new ArrayList<>();

    //<editor-fold desc = "Constructors"

    /**
     * Constructor with name
     * @param name String
     * @throws Exception if the name of the company is null
     */
    public Company(String name) throws Exception {
        if (name == null)
            throw new Exception("Company null argument");
        this.name = name;
    }

    /**
     * Constructor without parameters
     */
    public Company(){
        name = null;
    }


    //</editor-fold

    //<editor-fold desc = "Get and Set">

    public ArrayList<Manager> getListManagers() {
        return listManagers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Department> getListDepartment() {
        return listDepartment;
    }

    public ArrayList<Employee> getListEmployees() {
        return listEmployees;
    }


    //</editor-fold>

    //<editor-fold desc = "Method">

    //////////////////////////////////////////   Employee   ////////////////////////////////////////////////////////

    /**
     * add an employee in a company.
     * @param employee an Employee
     * @throws Exception if the employee is already in the company, or il the argument is null
     */
    public void addEmployee(Employee employee) throws Exception {
        if (employee != null){
            if (isEmployeeIn(employee))
                throw new Exception("this employee is already in the company");
            listEmployees.add(employee);
        }else{
            throw new Exception("arguments null");
        }

    }

    /**
     * search if an employee is in the company
     * @param employee an Employee
     * @return true if the Employee is in the company, false if not
     * @throws Exception if arguments are null
     */
    public boolean isEmployeeIn(Employee employee) throws Exception {
        if (employee == null)
            throw new Exception("null argument");
        for (Employee e : listEmployees) {
            if (e.getId() == employee.getId())
                return true;
        }
        return false;
    }

    /**
     * Add an Employee to a Department
     * @param employee an Employee
     * @param department a Department
     * @throws Exception if arguments are null, or employee is already in a Department
     */
    public void addEmployeeToDepartment(Employee employee,Department department) throws Exception {
        if (employee == null || department == null)
            throw new Exception("employee and department shouldn't be null");

        for (Department d : listDepartment) {
            if (d.isEmployeeIn(employee))
                throw new Exception("employee is already in a department");
        }

        department.addEmployee(employee);
    }

    /**
     * remove an employee from a department
     * @param employee an Employee
     * @param department a Department
     * @throws Exception if arguments are null, or the employee is not in the department
     */
    public void removeEmployeeFromDepartment(Employee employee, Department department) throws Exception {
        if (employee == null || department == null)
            throw new Exception("null arguments");

        if (!department.isEmployeeIn(employee))
            throw new Exception("this employee is not in this department");

        department.removeEmployee(employee);
    }

    /**
     * remove an employee from the company
     * if the employee isn't in the company this method does nothing
     * if the employee is in a department, he will be removed from this department
     * @param employee an Employee
     * @throws Exception if the argument is null
     */
    public void removeEmployee(Employee employee) throws Exception {
        if (employee == null)
            throw new Exception("null argument");
        if (!isEmployeeIn(employee))
            return ;

        for (Department d : listDepartment) {
            if (d.isEmployeeIn(employee)){
                d.removeEmployee(employee);
                listEmployees.remove(employee);
                return ;
            }
        }
        listEmployees.remove(employee);
    }

    /**
     * add a Check to an Employee
     * @param employee an Employee
     * @param check a Check
     * @throws Exception if Argument are null, or if the employee isn't in the company
     *                   or if the check isn't assigned to this employee
     *                   or if the employee have already checked twice today
     */
    public void addCheckToEmployee(Employee employee, Check check) throws Exception {
        if (employee == null || check == null)
            throw new Exception("Argument null");
        if (!isEmployeeIn(employee))
            throw new Exception("This employee isn't in the company");

        employee.addCheck(check);
    }

    /**
     * search an Employee in the company with his id
     * @param id int
     * @return The Employee which has this id
     * @throws Exception if there isn't an employee with this id
     */
    public Employee searchEmployeeWithId(int id) throws Exception {
        for (Employee m : getListEmployees()) {
            if (m.getId() == id)
                return m;
        }
        throw new Exception("Company : searchEmployeeWithId : This id isn't the the company");
    }

    /**
     * Check if an employee is assign to a department
     * @param employee Employee
     * @return true or false
     * @throws Exception if argument is null
     */
    public boolean isInADepartment(Employee employee) throws Exception {
        if (employee == null)
            throw new Exception("Company : isInADepartment : null arguments");
        for (Department d : listDepartment) {
            if (d.isEmployeeIn(employee))
                return true;
        }

        return false;

    }

    //////////////////////////////////////////   Manager   ////////////////////////////////////////////////////////

    /**
     * add a manager to a company
     * @param manager a Manager to add to the list
     * @throws Exception if this manager is already in the company, or if the manager is null
     */
    public void addManager(Manager manager) throws Exception {
        if (manager != null){
            if (isManagerIn(manager))
                throw new Exception("this manager is already in the company");
            listManagers.add(manager);
        }else{
            throw new Exception("argument null");
        }

    }

    /**
     * search if a manager is in the company
     * @param manager a Manager
     * @return true if the manager is in the company, false if not
     * @throws Exception if arguments are null
     */
    public boolean isManagerIn(Manager manager) throws Exception {
        if (manager == null)
            throw new Exception("null argument");
        for (Manager m : listManagers) {
            if (m.getId() == manager.getId())
                return true;
        }
        return false;
    }

    /**
     * Add a Manager to a Department
     * @param manager a Manager
     * @param department a Department
     * @throws Exception if arguments are null, or manager is already in a Department
     */
    public void addManagerToDepartment(Manager manager,Department department) throws Exception {
        if (manager == null || department == null)
            throw new Exception("employee and department shouldn't be null");

        for (Department d : listDepartment) {
            if (d.isManagerIn(manager))
                throw new Exception("manager is already in a department");
        }

        department.addManager(manager);
    }

    /**
     * remove an manager from a department
     * @param manager a Manager
     * @param department a Department
     * @throws Exception if arguments are null, or the manager is not in the department
     */
    public void removeManagerFromDepartment(Manager manager, Department department) throws Exception {
        if (manager == null || department == null)
            throw new Exception("null arguments");

        if (!department.isManagerIn(manager))
            throw new Exception("this employee is not in this department");

        department.removeManager(manager);
    }

    /**
     * remove a manager to the company
     * if the manager isn't in the company this method does nothing
     * if the manager is in a department an he isn't the chief, he will be remove from this department
     * @param manager a Manager
     * @throws Exception if the argument is null, or if the manager if the chief of a department
     */
    public void removeManager(Manager manager) throws Exception {
        if (manager == null)
            throw new Exception("null argument");
        if (!isManagerIn(manager))
            return ;

        for (Department d : listDepartment) {
            if (d.isManagerIn(manager)){
                if (manager.isChief())
                    throw new Exception("This manager is the chief of a department");
                d.removeManager(manager);
                listManagers.remove(manager);
                return ;
            }
        }
        listManagers.remove(manager);
    }

    /**
     * add a Check to a Manager
     * @param manager a Manager
     * @param check a Check
     * @throws Exception if Argument are null, or if the manager isn't in the company
     *                   or if the check isn't assigned to this manager
     *                   or if the manager have already checked twice today
     */
    public void addCheckToManager(Manager manager, Check check) throws Exception {
        if (manager== null || check == null)
            throw new Exception("Argument null");
        if (!isManagerIn(manager))
            throw new Exception("This manager isn't in the company");
        manager.addCheck(check);
    }

    /**
     * Check if a manager is assign to a department
     * @param manager a Manager
     * @return true or false
     * @throws Exception if argument is null
     */
    public boolean isInADepartment(Manager manager) throws Exception {
        if (manager == null)
            throw new Exception("Company : isInADepartment : null arguments");
        for (Department d : listDepartment) {
            if (d.isManagerIn(manager))
                return true;
        }

        return false;

    }

    /**
     * search a manager in the company with his id
     * @param id int
     * @return The Manager which has this id
     * @throws Exception if there isn't a manager with this id
     */
    public Manager searchManagerWithId(int id) throws Exception {
        for (Manager m : getListManagers()) {
            if (m.getId() == id)
                return m;
        }
        throw new Exception("Company : searchManagerWithId : This id isn't the the company");
    }

    //////////////////////////////////////////   Department   ////////////////////////////////////////////////////

    /**
     * add an department in the company
     * @param department a Department to add to the list
     * @throws Exception if the department is already in the company, or if the department is null
     */
    public void addDepartment(Department department) throws Exception {
        if (department != null) {
            for (Department d : listDepartment) {
                if (d.getName().equals(department.getName()))
                    throw new Exception("this Department is already in the company");
            }
            listDepartment.add(department);
        }else{
            throw new Exception("argument null");
        }
    }

    /**
     * Change the chief of a Department
     * the old chief won't be the chief of this department anymore.
     * @param department a Department
     * @param manager a Manager which will be the new chief of this department
     * @throws Exception if arguments are null, if the new chief is not in the department
     */
    public void changeChiefOfDepartment(Department department,Manager manager) throws Exception {
        if (department==null || manager == null)
            throw new Exception("Arguments null");
        department.changeChief(manager);
    }

    /**
     * Remove a department from the company, if there is only the chief
     * of the department inside.
     * @param department a Department
     * @throws Exception if the department is null, if the department isn't in the company
     *                   , or if there is more employee than the chief inside
     */
    public void removeDepartment(Department department) throws Exception {
        for (Department d : listDepartment) {
            if (d.getName().equals(department.getName())) {
                if (department.getListEmployees().size() != 0)
                    throw new Exception("To remove a department, only its chief must be inside");
                if (department.getListManagers().size() > 1)
                    throw new Exception("To remove a department, only its chief must be inside");
                listDepartment.remove(department);
                return;
            }
        }

        throw new Exception("this department isn't in the company");


    }

    /**
     * Search in the company a department with its name
     * @param string String the name of the department
     * @return Department the department which has this name
     * @throws Exception if there isn't any department with this name in the company
     */
    public Department searchDepartment(String string) throws Exception {
        for (Department department : listDepartment) {
            if (department.getName().equals(string))
                return department;
        }
        throw new Exception("Company : searchDepartment : No department with this name");
    }


    /**
     * Serialize the company in "save/company.serial".this path can't be changed by the user
     */
    public void serialize(){
        try {
            FileOutputStream fos = new FileOutputStream("save/company.serial");

            ObjectOutputStream oos= new ObjectOutputStream(fos);
            try {
                oos.writeObject(this);
                oos.flush();
                System.out.println("serialized");
            } finally {
                try {
                    oos.close();
                } finally {
                    fos.close();
                }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * deserialise a company from "save/company.serial"
     * @return the Company which has been deserialised
     * @throws Exception if the deserialization failed
     */
    static public Company deserialize() throws Exception {
        Company company = null;
        try {
            FileInputStream fis = new FileInputStream("save/company.serial");
            ObjectInputStream ois= new ObjectInputStream(fis);
            try {
                int id = 0;
                company = (Company) ois.readObject();
                for (Employee e : company.getListEmployees()) {
                    if (e.getId()>id)
                        id = e.getId();
                }
                for (Manager m : company.getListManagers()) {
                    if (m.getId()>id)
                        id = m.getId();
                }

                Employee.setCounter(id+1);
            } finally { try {
                    ois.close();
                } finally {
                    fis.close();
                }
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        if(company != null) {
            System.out.println("deserialize");
            return company;
        }
        throw new Exception("deserialization failed");

    }

    /**
     * Import a list of employees / managers to the company from a csv file.
     * the id of imported employees will be recalculate
     * @param file String, the path to the file to import
     * @throws Exception if the file does't exist, if the file can't be read, or if the value in the csv file aren't in a good format
     */
    public void importCSV(String file) throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader(file));
        String line = "";
        fileReader.readLine();
        while((line = fileReader.readLine()) != null) {
            String[] tab = line.split(",");
            if (tab[6].equals("manager")) {
                Manager m = new Manager(tab[1], tab[2], tab[3], new DateAndTime(tab[4], DateAndTime.TIME), new DateAndTime(tab[5], DateAndTime.TIME));
                addManager(m);
            }
            if (tab[6].equals("employee")) {
                Employee e = new Employee(tab[1], tab[2], tab[3], new DateAndTime(tab[4], DateAndTime.TIME), new DateAndTime(tab[5], DateAndTime.TIME));
                addEmployee(e);
            }
        }
    }

    /**
     * Export all the employees / managers of the company in a csv file.
     * @param file String the path where to export the list of employees
     * @throws IOException if the  file isn't found
     */
    public void exportCSV(String file) throws IOException {
        FileWriter writer = new FileWriter(file);
        String header = "Id,First Name, Last Name,mail,Departure Time,Arriving Time,Rank\n";
        String row;
        writer.append(header);
        for (Employee e : listEmployees) {
            row = Integer.toString(e.getId())+","+e.getFirstname()+","+e.getLastname()+","+e.getMail()+","+e.getArrivingTime().toString()+","+e.getDepartureTime().toString()+",employee\n";
            writer.append(row);
        }
        for (Manager e : listManagers) {
            row = Integer.toString(e.getId())+","+e.getFirstname()+","+e.getLastname()+","+e.getMail()+","+e.getArrivingTime().toString()+","+e.getDepartureTime().toString()+",manager\n";
            writer.append(row);
        }

        writer.close();
    }

    //</editor-fold>
}
