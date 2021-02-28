/** This program uses a one-time-pad encryption in order to encrypt the message from a text file cipher. 
*   The program will output the final encryption in the console.
*/

#include <iostream>
#include <fstream>
#include <cctype>

char shiftChar(char og, char shift) { // using the char values for inputted files, encrypts the message by adding the numeric values % 26, and converting to a letter
    if (isdigit(og)) {
        return og;
    }
    char encrypt = ((-('A' - og) + -('A' - shift)) % 26);
    return encrypt = (char)('A' + encrypt);
}

char readMsg(std::string msg, int &position) { // reads string message and returns char value
    if (isdigit(msg[position])) {
        return msg[position];
    }
    return toupper(msg[position]);
}

std::string getMsg(const std::string &filename) { // reads the file and returns a string
    std::ifstream in;
    in.open(filename);
    std::string line, msg = "";
    std::string alnum_msg = "";

    if (in.is_open()) {
        while (in >> line) {
            msg += line;
        }
        in.close();
    }
    for (int i = 0; i < msg.size(); i++) { // takes out characters that are not alphanumeric
        if (isalnum(msg[i])) {
            alnum_msg += msg[i];
        }
    }
    return alnum_msg;
}

char readPad(std::string filename, int &pos) { // reads the pad map and returns a single char value at a specific position
    std::ifstream in; // inputs file stream
    in.open(filename); // opens the pad map encryption file
    std::string line;
    char pad;

    if (in.is_open()) {
        while (!in.eof()) {
            for (int i = 0; i <= pos; i++) {
                in >> pad;
            }
            return pad; // returns the single letter in pad map at position given
        }
        in.close();
    }
    return '0';
}

int getSize(std::string filename) { // finds the length of the pad file
    std::ifstream in;
    in.open(filename);
    int count = -2; // starts at -2 to account for pad starting at 1, and the index starting at 0
    char c;
    if (in.is_open()) { // counts all characters in the file
        while (!in.eof()) {
            in >> c;
            count += 1;
        }
        in.close();
    }
    return count;
}

void getPos(int &pos) { // asks for the starting position on the pad
    std::cout << "Enter starting position on the pad: " << std::endl;
    std::cin >> pos;
    pos -= 1; // pad starts at one, so 1 is subtracted to compensate for starting at 0
}

int main() {
    std::string filename, filename_msg;
    std::cout << "Which one time pad do you want to use?" << std::endl;
    std::cin >> filename;

    int pad_size = getSize(filename);
    int pos;
    getPos(pos);

    if (pos > pad_size) { // checks if pad is long enough for the starting point
        std::cout << "Pad not long enough.";
        exit(0);
    }

    int position = 0;
    std::cout << "What file do you want to encrypt: " << std::endl;
    std::cin >> filename_msg;
    std::string msg = getMsg(filename_msg);

    while (position < msg.size()) { // uses a loop to encrypt each letter of the message file
        if (pos > pad_size) { // prints message if pad reaches the end
            std::cout << "\nPad not long enough.";
            exit(0);
        }
        if (isdigit(msg[position])) { // if a digit is encountered, skips over the encryption process, keeps the same pad position, and prints the number
            std::cout << msg[position];
            position += 1;
        }
        else {
            char c = shiftChar((readPad(filename, pos)), readMsg(msg, position));
            std::cout << c;
            position++;
            pos++;
        }
    }

    return 0;
}
