import java.util.*;

public class InfixToPostfix {

    // Returns the precedence of an operator
    public static int getPrecedence(char operator) {
        switch (operator) {
            case '^': // Exponentiation has the highest precedence
                return 3;
            case '*':
            case '/': // Multiplication and Division have precedence of 2
            case '%':
                return 2;
            case '+':
            case '-': // Addition and Subtraction have precedence of 1
                return 1;
            default:
                return 0; // For invalid characters
        }
    }

    // Checks if a character is an operand (digit or decimal point)
    public static boolean isOperand(char ch) {
        return Character.isDigit(ch) || ch == '.';
    }

    // Checks if an operator is right-associative
    public static boolean isRightAssociative(char operator) {
        return operator == '^'; // Exponentiation is right-associative
    }

    // Converts infix expression to postfix expression
    public static String infixToPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();

        for (int i = 0; i < infix.length(); i++) {
            char currentChar = infix.charAt(i);

            // Skip spaces
            if (currentChar == ' ') {
                continue;
            }

            // Handle multi-digit numbers and floating-point numbers
            if (isOperand(currentChar)) {
                StringBuilder operand = new StringBuilder();
                while (i < infix.length() && (isOperand(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    operand.append(infix.charAt(i));
                    i++;
                }
                i--; // Adjust index after the loop
                postfix.append(operand).append(" "); // Append operand with a space
            } else if (currentChar == '(') {
                stack.push(currentChar); // Push '(' onto the stack
            } else if (currentChar == ')') {
                // Pop until '(' is encountered
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(" ");
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop(); // Pop '('
                } else {
                    return "Invalid expression: Mismatched parentheses";
                }
            } else if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/' || currentChar == '^' || currentChar == '%') {
                // Process operators
                while (!stack.isEmpty() &&
                        (getPrecedence(stack.peek()) > getPrecedence(currentChar) ||
                                (getPrecedence(stack.peek()) == getPrecedence(currentChar) && !isRightAssociative(currentChar)))) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(currentChar); // Push the current operator onto the stack
            } else {
                // Skip invalid characters (e.g., exclamation marks, random strings)
                System.out.println("Skipping invalid character: " + currentChar);
            }

            System.out.println("Current stack: " + stack);
            System.out.println("Current output: " + postfix);
        }

        // Pop remaining operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                return "Invalid expression: Mismatched parentheses";
            }
            postfix.append(stack.pop()).append(" ");
        }

        return postfix.toString().trim(); // Remove trailing space
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String infix;

        while (true) {
            System.out.print("Enter an infix expression (or type 'exit' to quit): ");
            infix = scanner.nextLine();

            if (infix.equalsIgnoreCase("exit")) {
                break;
            }

            // Remove any extra spaces from the expression
            infix = infix.replaceAll("\\s+", "");

            System.out.println("Converting infix to postfix...");
            String postfix = infixToPostfix(infix);
            System.out.println("Postfix expression: " + postfix);
            System.out.println();
        }
        scanner.close();
    }
}