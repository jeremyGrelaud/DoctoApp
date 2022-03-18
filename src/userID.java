// SINGLETON PATTERN
class userID{
    private static userID INSTANCE = null;
    private int id;

    private userID(){
        id = 0;
    }

    public synchronized static userID getInstance() {
        if(INSTANCE == null){
            INSTANCE = new userID();
        }
        return INSTANCE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}