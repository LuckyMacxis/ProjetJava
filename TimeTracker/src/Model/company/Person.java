package Model.company;

import java.io.Serializable;

public abstract class Person implements Serializable
{
    //Attribute

    private String mail;
    private String lastname;
    private String firstname;

    //<editor-fold desc="Constructors">

    /**
     * Constructor with all parameters
     * @param mail -
     * @param lastname -
     * @param firstname -
     */
    public Person(String lastname, String firstname, String mail) throws Exception {
        this(lastname,firstname);
        this.mail = mail;
    }

    /**
     * Constructor without parameters
     */
    public Person() {
        mail = null;
        lastname = null;
        firstname = null;
    }

    /**
     * Constructor with first and last name
     * @param lastname -
     * @param firstname -
     */
    public Person(String lastname, String firstname) throws Exception {
        if (lastname == null || firstname == null)
            throw new Exception("Person null arguments");
        this.lastname = lastname;
        this.firstname = firstname;
        mail = null;
    }

    //</editor-fold>

    //<editor-fold desc="Get and Set">

    /**
     * get mail
     * @return the mail of a person
     */
    public String getMail() {
        return mail;
    }

    /**
     *  set mail
     * @param mail String
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * get last name
     * @return the last name of a person
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * set lastname
     * @param lastname String
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * get firstname
     * @return the lastname of a person
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * set firstname
     * @param firstname String
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    //</editor-fold>

    //<editor-fold desc = "Method">

    //</editor-fold>










}
