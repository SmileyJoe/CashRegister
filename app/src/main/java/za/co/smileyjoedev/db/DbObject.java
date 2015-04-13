package za.co.smileyjoedev.db;

public abstract class DbObject {

	private long mId;
	
	public void create(long id){
		mId = id;
	}
	
	public void setId(long id){
		mId = id;
	}
	
	public long getId(){
		return mId;
	}
	
	@Override
	public String toString() {
		return "DbObject [getId()=" + getId() + "]";
	}
	
	
	
}
