package it.unisa.diem.se.biblioteca.book;


public class InvalidBookException extends Exception {


    public InvalidBookException() {
    }

    /**
     * Constructs an instance of <code>InvalidBookException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidBookException(String msg) {
        super(msg);
    }
}
