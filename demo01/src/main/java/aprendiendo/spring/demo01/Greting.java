package aprendiendo.spring.demo01;

public class Greting {
    
    private long id;
    private String message;
    
    public Greting(long id, String message) {
        this.id = id;
        this.message = message;
    }


    public long getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void setId(long id) {
        this.id = id;
    }
    
}
