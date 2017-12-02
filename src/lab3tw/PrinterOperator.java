package lab3tw;

public class PrinterOperator implements Runnable {
	
	private final PrinterMonitor printerMonitor;
	
	PrinterOperator(PrinterMonitor printerMonitor) {
		this.printerMonitor = printerMonitor;
	}
	
	@Override
	public void run() {
		while (true) {			
			Integer printerNumber = null;
			try {
				Thread.sleep(300);
				printerNumber = printerMonitor.take();
				System.out.println("Printer number: " + printerNumber);
				printerMonitor.put(printerNumber);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
