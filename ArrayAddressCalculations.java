import java.util.Scanner;

public class ArrayAddressCalculations{
    public static void main(String[] args) {
        Scanner gioScanner = new Scanner(System.in);

        // Step 1: Get user inputs
        System.out.println("Enter the dimensions of the array (e.g., [][][] = 3, [][][][] = 4):");
        int userDimension = gioScanner.nextInt();

        System.out.println("Enter the base address:");
        int baseAddress = gioScanner.nextInt();

        System.out.println("Enter the size of each element in bytes:");
        int elementSize = gioScanner.nextInt();

        // Step 2: Get sizes of each dimension
        int[] userSizes = new int[userDimension];
        System.out.println("Enter the sizes of the array (e.g., 2,4,1 = [2][4][1], 7,5,5 = [7][5][5]):");
        for (int i = 0; i < userDimension; i++) {
            userSizes[i] = gioScanner.nextInt();
        }

        // Step 3: Get location of the element
        int[] elementLoc = new int[userDimension];
        System.out.println("Enter the location of the element (e.g., 2,4,1 = [2][4][1], 7,5,5 = [7][5][5]):");
        for (int i = 0; i < userDimension; i++) {
            elementLoc[i] = gioScanner.nextInt();
            while (elementLoc[i] >= userSizes[i]) { // Validate indices
                System.out.println("Address of element exceeds size of dimension. Please try again.");
                elementLoc[i] = gioScanner.nextInt();
            }
        }

        // Step 4: Calculate and display results
        System.out.println("Address of the element in the array: " + calculateAddress(elementLoc, userSizes, elementSize, baseAddress));
        System.out.println("Total size of the array in bytes: " + calculateSize(userSizes));
    }

    /**
     * Calculates the address of an element in a multidimensional array.
     *
     * @param eLoc      The indices of the element.
     * @param uSize     The sizes of each dimension.
     * @param eSize     The size of each element in bytes.
     * @param bAddress  The base address of the array.
     * @return The calculated address.
     */
    private static int calculateAddress(int[] eLoc, int[] uSize, int eSize, int bAddress) {
        int offset = 0;
        int stride = 1;

        // Iterate through the dimensions in reverse order
        for (int i = uSize.length - 1; i >= 0; i--) {
            offset += eLoc[i] * stride;
            stride *= uSize[i];
        }

        return bAddress + (offset * eSize); // Multiply by element size at the end
}

    /**
     * Calculates the total size of the array in bytes.
     *
     * @param uSize The sizes of each dimension.
     * @param eSize The size of each element in bytes.
     * @return The total size of the array in bytes.
     */
    private static int calculateSize(int[] uSize) {
        int totalSize = 1;

        // Calculate the total number of elements
        for (int i = 0; i < uSize.length; i++) {
            totalSize *= uSize[i];
        }

        return totalSize; // Multiply by element size
    }
}
