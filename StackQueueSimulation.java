import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class StackQueueSimulation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Stack<Object> stack = new Stack<>(); // Use Object to handle multiple types
        LinkedList<Object> queue = new LinkedList<>();

        while (true) {
            System.out.println("\nChoose operation:");
            System.out.println("1. Push to Stack");
            System.out.println("2. Pop from Stack");
            System.out.println("3. Enqueue to Queue");
            System.out.println("4. Dequeue from Queue");
            System.out.println("5. Bulk Push to Stack (comma-separated values)");
            System.out.println("6. Bulk Enqueue to Queue (comma-separated values)");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            // Handle invalid input for choice
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter value to push: ");
                    Object pushValue = parseInput(scanner.nextLine().trim());
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
                    Object enqueueValue = parseInput(scanner.nextLine().trim());
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
                    System.out.print("Enter values to push (comma-separated): ");
                    String[] pushValues = scanner.nextLine().trim().split(",");
                    for (String value : pushValues) {
                        stack.push(parseInput(value.trim()));
                    }
                    displayStack(stack);
                    break;
                case 6:
                    System.out.print("Enter values to enqueue (comma-separated): ");
                    String[] enqueueValues = scanner.nextLine().trim().split(",");
                    for (String value : enqueueValues) {
                        queue.add(parseInput(value.trim()));
                    }
                    displayQueue(queue);
                    break;
                case 7:
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    /**
     * Parses the input string into an appropriate type (Integer, Float, or String).
     *
     * @param input The input string to parse.
     * @return The parsed object (Integer, Float, or String).
     */
    private static Object parseInput(String input) {
        try {
            return Integer.parseInt(input); // Try parsing as Integer
        } catch (NumberFormatException e1) {
            try {
                return Float.parseFloat(input); // Try parsing as Float
            } catch (NumberFormatException e2) {
                return input; // Default to String if not a number
            }
        }
    }

    /**
     * Displays the contents of the stack.
     *
     * @param stack The stack to display.
     */
    private static void displayStack(Stack<Object> stack) {
        System.out.println("Stack contents: " + stack);
    }

    /**
     * Displays the contents of the queue.
     *
     * @param queue The queue to display.
     */
    private static void displayQueue(LinkedList<Object> queue) {
        System.out.println("Queue contents: " + queue);
    }
}
