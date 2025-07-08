package myshop.domain;

public class PaymentVO
{
   private String   payment_id;			//	결제내역번호
   private String   imp_uid;			//	결제코드(아임포트 결제id)
   private String   fk_reserv_no;		//	예약번호
   private String   fk_user_id;			//	회원아이디
   private int      paid_amount;		//	실 결제금액
   private int      used_point;			//	소모 포인트
   private int      earned_point;		//	적립 예정 포인트
   private String   pay_method;			//	결제수단
   private String   status;				//	결제상태
   private String   pay_time;			//	결제시각
   private String   cancel_time;		//	결제취소시각
   private int      total_payment_stamp;//	결제 당시의 누적결제금액
   private int		processed;			//	포인트 적립 및 등급업데이트 여부

   public String getPayment_id()
   {
      return payment_id;
   }
   public void setPayment_id(String payment_id)
   {
      this.payment_id = payment_id;
   }
   
   public String getImp_uid()
   {
      return imp_uid;
   }
   public void setImp_uid(String imp_uid)
   {
      this.imp_uid = imp_uid;
   }
   
   public String getFk_reserv_no()
   {
      return fk_reserv_no;
   }
   public void setFk_reserv_no(String fk_reserv_no)
   {
      this.fk_reserv_no = fk_reserv_no;
   }
   
   public String getFk_user_id()
   {
      return fk_user_id;
   }
   public void setFk_user_id(String fk_user_id)
   {
      this.fk_user_id = fk_user_id;
   }
   
   public int getPaid_amount()
   {
      return paid_amount;
   }
   public void setPaid_amount(int paid_amount)
   {
      this.paid_amount = paid_amount;
   }
   
   public int getUsed_point()
   {
      return used_point;
   }
   public void setUsed_point(int used_point)
   {
      this.used_point = used_point;
   }
   
   public int getEarned_point()
   {
      return earned_point;
   }
   public void setEarned_point(int earned_point)
   {
      this.earned_point = earned_point;
   }
   
   public String getPay_method()
   {
      return pay_method;
   }
   public void setPay_method(String pay_method)
   {
      this.pay_method = pay_method;
   }
   
   public String getStatus()
   {
      return status;
   }
   public void setStatus(String status)
   {
      this.status = status;
   }
   
   public String getPay_time()
   {
      return pay_time;
   }
   public void setPay_time(String pay_time)
   {
      this.pay_time = pay_time;
   }
   
   public String getCancel_time()
   {
      return cancel_time;
   }
   public void setCancel_time(String cancel_time)
   {
      this.cancel_time = cancel_time;
   }
   
   public int getTotal_payment_stamp()
   {
      return total_payment_stamp;
   }
   public void setTotal_payment_stamp(int total_payment_stamp)
   {
      this.total_payment_stamp = total_payment_stamp;
   }
   
   public int getProcessed()
   {
	   return processed;
   }
   public void setProcessed(int processed)
   {
	   this.processed = processed;
   }
}
