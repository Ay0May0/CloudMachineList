
public class Program1Tests {
	
	public static void main(String[] args) {
		
		DLinkedList curr1 = new DLinkedList();
		System.out.println(curr1.isEmpty());
		
		curr1.add(new DblListnode(3));
		curr1.add(5);
		curr1.add(7);
		
		System.out.println(curr1.contains(3));
		System.out.println(curr1.get(2));
		System.out.println(curr1.size());
		System.out.println(curr1.remove(1));
		System.out.println(curr1.get(1));
		System.out.println(curr1.size());
		
	}
}