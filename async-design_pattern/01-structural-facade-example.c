#include <stdio.h>
#include <string.h>

/**
 * @brief Facade function for printing strings.
 *
 * This function acts as a facade to simplify string output. Instead of requiring the caller
 * to manually format strings with newlines and handle the underlying formatting and printing
 * details, this function encapsulates that complexity. It uses snprintf to format the string
 * with a newline character and then calls printf to output it.
 *
 * This implementation demonstrates the Facade design pattern. The Facade pattern provides a
 * simple interface to a more complex subsystem. In this case, the complexity of string formatting
 * and printing is hidden behind the simple interface of the print function. This makes the code easier
 * to read, maintain, and reuse.
 *
 * Benefits:
 * - Simplifies the interface for printing strings.
 * - Hides the underlying formatting and printing logic.
 * - Promotes code reuse and adheres to the DRY (Don't Repeat Yourself) principle.
 *
 * @param str The string to be printed.
 */
void print(char* str) {
    char buffer[1024]; // Ensure this is large enough for the concatenated string
    snprintf(buffer, sizeof(buffer), "%s\n", str);
    printf("%s", buffer);
}


int main(void) {
    // Using the facade function 'print' (simplified interface)
    print("Using facade: Hello World!");
    print("Using facade: I am learning C.");
    print("Using facade: And it is awesome!");
    
    // Using direct printing without the facade
    printf("\n--- Direct Printing without Facade ---\n");
    printf("%s\n", "Direct: Hello World!");
    printf("%s\n", "Direct: I am learning C.");
    printf("%s\n", "Direct: And it is awesome!");
    
    return 0;
}
