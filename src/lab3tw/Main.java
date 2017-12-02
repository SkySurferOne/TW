package lab3tw;


public class Main {
	
	public static void main(String[] args) {
//		BoundedBuffer buffer = new BoundedBuffer();
//		Producer3 producer = new Producer3(buffer);
//		Consumer3 consumer = new Consumer3(buffer);
//		
//		for (int i = 0; i<1; i++) {
//			Thread thread = new Thread(producer);
//			thread.start();
//		}
//		
//		for (int i = 0; i<100; i++) {
//			Thread thread = new Thread(consumer);
//			thread.start();
//		}
//		
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		int operatorsNum = 10;
//		int printersNum = 5;
//		PrinterMonitor printerMonitor = new PrinterMonitor(printersNum);
//		PrinterOperator printerOperator = new PrinterOperator(printerMonitor);
//
//		for (int i=0; i<operatorsNum; i++) {
//			System.out.println("opertator number: " + i);
//
//			Thread thread = new Thread(printerOperator);
//			thread.start();
//		}

		int pairs = 10;
		WaiterMonitor waiterMonitor = new WaiterMonitor();

		for (int i=0; i<pairs; i++) {
			System.out.println("Creating pair number: " + i);
			RestaurantCustomer restaurantCustomer = new RestaurantCustomer(waiterMonitor, i);

			Thread thread1 = new Thread(restaurantCustomer);
			Thread thread2 = new Thread(restaurantCustomer);
			thread1.start();
			thread2.start();
		}

//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
