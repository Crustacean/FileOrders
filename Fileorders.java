import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter; 

class Fileorders{
	
	static class Orders implements Comparable<Orders>{
		
		int customerNumber;
		double orderAmount;
		String customerName;
		
		public Orders(int x, double y, String z){
			
			this.customerNumber = x;
			this.orderAmount = y;
			this.customerName = z;	
			
		}
		
		@Override
		public int compareTo(Orders m){
			
			return (m.customerNumber > this.customerNumber) ? 1 : -1;
			
		}
		
		@Override
		public String toString(){
			return this.customerNumber +"\t"+ this.orderAmount +"\t"+ this.customerName;
		}
		
		public int getCustomerNumber(){
			return customerNumber;
		}
		
		public double getOrderAmount(){
			return orderAmount;
		}
		
		public String getCustomerName(){
			return customerName;
		}
		
	}
	
	public static void writeTo(Orders o) throws IOException, FileNotFoundException{
		
		String filePath = "./";
		
		File file = new File(filePath);
		
		String[] allFiles = file.list();
		
		LocalDateTime time = LocalDateTime.now();
		DateTimeFormatter myDate = DateTimeFormatter.ofPattern("YYYYMMdd");
		String formattedTime =  time.format(myDate);
		
		File fileObj = new File("./orderReceipt_"+formattedTime+"_.txt");
		
		if(!fileObj.exists()){
			
			System.out.println(fileObj.getName()+" does not exist.");
			
			if(fileObj.createNewFile()){
				System.out.println(fileObj.getName()+" is created successfully.");
				if(fileObj.canRead() && fileObj.canWrite()){
					System.out.println("File is ready for use.");
				}
			}
			
		}
		
		FileWriter fw = new FileWriter(fileObj, true);
		
		String customerNumber = String.valueOf(o.getCustomerNumber());
		String orderAmount = String.valueOf(o.getOrderAmount());
		String customerName = o.getCustomerName();
		fw.write(customerNumber+",\t"+orderAmount+",\t"+customerName+"\n");
		
		fw.close();
		
		
	}
	
	public static class SortOnAmount implements Comparator<Orders>{
		
		@Override
		public int compare(Orders num1, Orders num2){
			return  num1.getOrderAmount() > num2.getOrderAmount() ? 1 : -1;
		}
		
	}
	
	public static void printReceipt(int n) throws IOException, FileNotFoundException{
		
		PriorityQueue<Orders> pq = new PriorityQueue<>();
		
		Scanner sc = new Scanner(System.in);
		
		for(int i=0;i<n;i++){
			
			System.out.print("Enter order amount: ");
			double orderAmount = sc.nextDouble();
			
			System.out.print("Enter customer name: ");
			String customerName = sc.nextLine();
			
			while(customerName != null){
				System.out.print(customerName);
				
				if(sc.hasNextLine()){
					customerName = sc.nextLine();
					break;
				}else{
					customerName = null;
				}
				
			}
			System.out.print("\n");
			
			Orders o;
			
			o = new Orders((i+1), orderAmount, customerName);
			pq.add(o);
			
		}
		
		System.out.println("Printing please wait");
		
		while(!pq.isEmpty()){
			writeTo(pq.peek());
			System.out.println(pq.poll());
		}
		
	}
	
	public static void main(String[] args){
		
		try{
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Please enter number of orders: ");
		int n = sc.nextInt();
		
		printReceipt(n);
		}catch(Exception e){
			
			System.out.println("There has been an issue");
			
		}
		
		
	}
	
}


/*

https://rollbar.com/blog/how-to-fix-method-constructor-in-class-cannot-be-applied-to-given-types-in-java/

https://www.digitalocean.com/community/tutorials/java-append-to-file



*/