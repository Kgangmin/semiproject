package myshop.domain;

public class ReviewVO
{
	private String	review_no;
	private double	review_score;
	private String	review_contents;
	private String	review_writedate;
	private String	fk_reserv_no;
	
	private ReservationVO	rsvvo;
	private RoomVO			rvo;
	
	public ReviewVO() {};
	
	public ReviewVO(String review_no,
					double review_score,
					String review_contents,
					String review_writedate,
					String fk_reserv_no)
	{
		super();
		this.review_no			= review_no;
		this.review_score		= review_score;
		this.review_contents	= review_contents;
		this.review_writedate	= review_writedate;
		this.fk_reserv_no		= fk_reserv_no;
	}
	
	public String getReview_no()
	{
		return review_no;
	}
	public void setReview_no(String review_no)
	{
		this.review_no = review_no;
	}
	
	public double getReview_score()
	{
		return review_score;
	}
	public void setReview_score(double review_score)
	{
		this.review_score = review_score;
	}

	public String getReview_contents()
	{
		return review_contents;
	}
	public void setReview_contents(String review_contents)
	{
		this.review_contents = review_contents;
	}
	
	public String getReview_writedate()
	{
		return review_writedate;
	}
	public void setReview_writedate(String review_writedate)
	{
		this.review_writedate = review_writedate;
	}
	
	public String getFk_reserv_no()
	{
		return fk_reserv_no;
	}
	public void setFk_reserv_no(String fk_reserv_no)
	{
		this.fk_reserv_no = fk_reserv_no;
	}
	
	public ReservationVO getRsvvo()
	{
		return rsvvo;
	}
	public void setRsvvo(ReservationVO rsvvo)
	{
		this.rsvvo = rsvvo;
	}
	
	public RoomVO getRvo()
	{
		return rvo;
	}
	public void setRvo(RoomVO rvo)
	{
		this.rvo = rvo;
	}
}
