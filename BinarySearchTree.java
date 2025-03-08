import java.util.Scanner;

// Node class for BST
class Node {
    int data;
    Node left, right;

    public Node(int data) {
        this.data = data;
        left = right = null;
    }
}

// BST class
class BST {
    private Node root;

    public BST() {
        root = null;
    }

    // Insert a node into the BST
    public void insert(int data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node root, int data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }

        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }

        return root;
    }

    // 1-D Array Representation of BST
    public void displayArray() {
        if (root == null) {
            System.out.println("Tree is empty.");
            return;
        }

        int height = getHeight(root);
        int size = (int) Math.pow(2, height) - 1;
        int[] array = new int[size];
        buildArray(root, array, 0);

        System.out.println("\n1-D Array Representation:");
        System.out.print("Size of the Array = 2^k - 1");
        System.out.println(" = " + size);
        System.out.print("int BST[" + size + "] = {");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i < array.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("}");
    }

    private void buildArray(Node root, int[] array, int index) {
        if (root == null || index >= array.length) {
            return;
        }

        array[index] = root.data;
        buildArray(root.left, array, 2 * index + 1);
        buildArray(root.right, array, 2 * index + 2);
    }

    private int getHeight(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }

    // Preorder Traversal
    public void preorder() {
        System.out.print("Preorder: ");
        preorderRec(root);
        System.out.println();
    }

    private void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    // Inorder Traversal
    public void inorder() {
        System.out.print("Inorder: ");
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }

    // Postorder Traversal
    public void postorder() {
        System.out.print("Postorder: ");
        postorderRec(root);
        System.out.println();
    }

    private void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.data + " ");
        }
    }
}

// Main class
public class BinarySearchTree {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BST bst = new BST();
        boolean tryAgain = true;

        while (tryAgain) {
            System.out.println("Enter integers to insert into the BST (type 'done' to finish):");
            while (true) {
                String input = scanner.next();
                if (input.equalsIgnoreCase("done")) {
                    break;
                }
                try {
                    int data = Integer.parseInt(input);
                    bst.insert(data);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter an integer or 'done' to finish.");
                }
            }

            // Display 1-D Array Representation
            bst.displayArray();

            // Display Tree Traversals
            System.out.println("\nTraversals:");
            bst.preorder();
            bst.inorder();
            bst.postorder();

            // Ask user if they want to try again
            System.out.println("\nDo you want to try again? (yes/no):");
            String choice = scanner.next();
            if (!choice.equalsIgnoreCase("yes")) {
                tryAgain = false;
            } else {
                bst = new BST(); // Reset BST for new input
            }
        }

        scanner.close();
        System.out.println("Program ended.");
    }
}
