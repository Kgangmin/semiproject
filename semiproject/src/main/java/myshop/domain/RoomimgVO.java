package myshop.domain;

public class RoomimgVO
{
	private String room_extraimg_no;
	private String fk_room_no;
	private String room_extraimg_filename;
	
	public String getRoom_extraimg_no()
	{
		return room_extraimg_no;
	}
	public void setRoom_extraimg_no(String room_extraimg_no)
	{
		this.room_extraimg_no = room_extraimg_no;
	}
	
	public String getFk_room_no()
	{
		return fk_room_no;
	}
	public void setFk_room_no(String fk_room_no)
	{
		this.fk_room_no = fk_room_no;
	}
	
	public String getRoom_extraimg_filename()
	{
		return room_extraimg_filename;
	}
	public void setRoom_extraimg_filename(String room_extraimg_filename)
	{
		this.room_extraimg_filename = room_extraimg_filename;
	}
}
