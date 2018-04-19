package pseudocode;

public class Timer {

	public void pause(int paramInt)
	{
		try
		{
			Thread.currentThread();
			Thread.sleep(paramInt);
			return;
		}
		catch(InterruptedException localInterruptedException){}
		
		
	}
	
	
}
