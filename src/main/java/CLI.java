import java.util.Scanner;

public class CLI {

    final Scanner in = new Scanner(System.in);
    final String OUTPUT_FORMAT = "%-30s%s";
    final AES aes = new AES();
    String MODE_OF_OPERATION = "";
    String CYPHER_FUNCTION = "";

    private void modeOfOperationSelection(){
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Select the mode of operation for AES encryption: \n" +
                    "1. Electronic Code Book (ECB) \n" +
                    "2. Cipher Block Chaining (CBC) \n" +
                    "3. Output Feedback (OFB) \n" +
                    "4. Cipher Feedback (CFB) \n" +
                    "5. Counter (CTR) \n" +
                    "6. Galois Counter (GCM) \n" +
                    "7. Exit \n" +
                    "\nEnter number for mode selection: \n");
            String selection = in.nextLine().trim();

            switch (selection){
                case "1":
                    MODE_OF_OPERATION = "ECB";
                    validInput = true;
                    break;
                case "2":
                    MODE_OF_OPERATION = "CBC";
                    validInput = true;
                    break;
                case "3":
                    MODE_OF_OPERATION = "OFB";
                    validInput = true;
                    break;
                case "4":
                    MODE_OF_OPERATION = "CFB";
                    validInput = true;
                    break;
                case "5":
                    MODE_OF_OPERATION = "CTR";
                    validInput = true;
                    break;
                case "6":
                    MODE_OF_OPERATION = "GCM";
                    validInput = true;
                    break;
                case "7":
                    MODE_OF_OPERATION = "Exit";
                    validInput = true;
                    break;
                default:
                    System.out.println("Invalid selection. Try again...\n");
            }
        }
    }

    private void cypherFunctionSelection(){
        boolean validInput = false;

        while(!validInput) {
            System.out.println("Select cypher function: \n" +
                    "1. Encrypt \n" +
                    "2. Decrypt \n" +
                    "3. Back to mode operation menu \n" +
                    "\nEnter number for mode selection: ");

            String selection = in.nextLine().trim();

            switch (selection) {
                case "1":
                    CYPHER_FUNCTION = "Encrypt";
                    validInput = true;
                    break;
                case "2":
                    CYPHER_FUNCTION = "Decrypt";
                    validInput = true;
                    break;
                case "3":
                    CYPHER_FUNCTION = "Back";
                    validInput = true;
                    break;
                default:
                    System.out.println("Invalid selection. Try again...\n");
            }
        }
    }

    private void cypherFunction () throws Exception {
        boolean validInput = false;
        String secretKey = "";
        String userText = "";

        while (!validInput){
            System.out.println("Enter secret key: " );
            secretKey = in.nextLine().trim();
            if (!secretKey.equalsIgnoreCase("")){
                validInput = true;
            } else {
                System.out.println("Secret key can not be empty string. Try again...\n");
            }
        }

        validInput = false;

        while (!validInput){
            System.out.println("Enter string to "+ CYPHER_FUNCTION.toLowerCase() + ": " );
            userText = in.nextLine().trim();
            if (!userText.equalsIgnoreCase("")){
                validInput = true;
            } else {
                System.out.println(CYPHER_FUNCTION + " text can not be empty string. Try again... \n");
            }
        }

        if (CYPHER_FUNCTION.equalsIgnoreCase("encrypt")){
            String encryptedText = aes.encrypt(this.MODE_OF_OPERATION, userText, secretKey);
            System.out.println(String.format(OUTPUT_FORMAT, "Plain Text", userText));
            System.out.println(String.format(OUTPUT_FORMAT, "Encrypted Message (base64)", encryptedText));
            System.out.println(String.format(OUTPUT_FORMAT, "Encrypted (Hex)", aes.hex(encryptedText.getBytes())));
            System.out.println(String.format(OUTPUT_FORMAT, "Encrypted (hex) (Block = 16)", aes.hexWithBlockSize(encryptedText.getBytes(), 16)));
            System.out.println(String.format(OUTPUT_FORMAT, "Key (hex)", aes.hex(aes.getKey().getEncoded())));
        }else {
            String decryptedText = aes.decrypt(this.MODE_OF_OPERATION, userText, secretKey);
            System.out.println(String.format(OUTPUT_FORMAT, "Encrypted Message (base64)", userText));
            System.out.println(String.format(OUTPUT_FORMAT, "Plain Text", decryptedText));
            System.out.println(String.format(OUTPUT_FORMAT, "Encrypted Message (hex)", aes.hex(userText.getBytes())));
            System.out.println(String.format(OUTPUT_FORMAT, "Encrypted (hex) (Block = 16)", aes.hexWithBlockSize(userText.getBytes(), 16)));
            System.out.println(String.format(OUTPUT_FORMAT, "Key (hex)", aes.hex(aes.getKey().getEncoded())));

        }
    }


    public static void main(String[] args) throws Exception {
        boolean exit = false;
        CLI cli = new CLI();
        while (!exit){
            cli.modeOfOperationSelection();
            System.out.println("Mode of operation Selected: " + cli.MODE_OF_OPERATION + "\n");
            if (cli.MODE_OF_OPERATION.equalsIgnoreCase("exit")){
                exit = true;
            }
            else {
                cli.cypherFunctionSelection();
                if(!cli.CYPHER_FUNCTION.equalsIgnoreCase("Back")) {
                    System.out.println("Cypher function Selected: " + cli.CYPHER_FUNCTION + "\n");
                    exit = true;
                    cli.cypherFunction();
                }
            }
        }
    }
}
