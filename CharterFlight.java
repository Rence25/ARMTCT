package sb;

import static sb.CharterFlight.Status.*;

public class CharterFlight {
	public static enum Status { SUCCESS, FAILURE, ERROR };
    public String Flightname; //--
	public int Ticketrate; //--
	
	CharterFlight(){ //--
		Flightname = "";
		Ticketrate=0;
	}		
	public static Status fits(int passengers, boolean comfortFlag)
	{
		Status rv = FAILURE;
		int availableSeats = 120;
		
		if (comfortFlag)
			availableSeats = availableSeats - 40;
		
		if ( (passengers>0) && (passengers<=availableSeats) )
			rv = SUCCESS;
		else if (passengers<=0)
			rv = ERROR;

		return rv;
	}
	
	public void booking(boolean comfortFlag)
	{
      if (comfortFlag){
	      Flightname = "Ethihad";
		  Ticketrate = 1000;
	  }
	  else{
		  Flightname ="Luftansa";
		  Ticketrate = 500;
	  }
	}

}
