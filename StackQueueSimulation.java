import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class StackQueueSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Integer> stack = new Stack<>();
        LinkedList<Integer> queue = new LinkedList<>();

        while (true) {
            System.out.println("Choose operation:");
            System.out.println("1. Push to Stack");
            System.out.println("2. Pop from Stack");
            System.out.println("3. Enqueue to Queue");
            System.out.println("4. Dequeue from Queue");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter value to push: ");
                    int pushValue = scanner.nextInt();
                    stack.push(pushValue);
                    displayStack(stack);
                    break;
                case 2:
                    if (!stack.isEmpty()) {
                        System.out.println("Popped: " + stack.pop());
                    } else {
                        System.out.println("Stack is empty!");
                    }
                    displayStack(stack);
                    break;
                case 3:
                    System.out.print("Enter value to enqueue: ");
                    int enqueueValue = scanner.nextInt();
                    queue.add(enqueueValue);
                    displayQueue(queue);
                    break;
                case 4:
                    if (!queue.isEmpty()) {
                        System.out.println("Dequeued: " + queue.remove());
                    } else {
                        System.out.println("Queue is empty!");
                    }
                    displayQueue(queue);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void displayStack(Stack<Integer> stack) {
        System.out.println("Stack contents: " + stack);
    }

    private static void displayQueue(LinkedList<Integer> queue) {
        System.out.println("Queue contents: " + queue);
    }
}
