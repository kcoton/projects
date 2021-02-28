/**
 * This program takes a Point from the user (x,y,z), k value for number of points to return, and reads graph1.txt file.
 * Each point in graph1.txt is compared to the user Point to find the closest distance of k number of points.
 * The closest points found are used to determined the class property (A,B,C,D) for the user Point.
 */

#include <iostream>
#include <cmath>
#include <sstream>
#include <fstream>
#include <vector>
#include <string>
#include <algorithm>

struct Point {
    int x, y, z;
    char cl;

    /**
     * Calculates and returns the distance between two points using their x,y,z values
     * @param other Point object
     * @return distance between two points
     */
    [[nodiscard]] double getDistance(Point const &other) const { // compares object to specified point
        double delta_x = pow((x - other.x),2);
        double delta_y = pow((y - other.y),2);
        double delta_z = pow((z - other.z),2);
        return sqrt(delta_x + delta_y + delta_z);
    }

    /**
     * Creates string in the format: (x,y,z) is class cl
     * @return output string statement
     */
    [[nodiscard]] std::string printOut() const {
        std::ostringstream os;
        os << "(" << x << "," << y << "," << z << ") is class " << cl;
        std::string output = os.str();
        return output;
    }
};

/**
 * Creates a new Point object with the parameters
 * @param x value
 * @param y value
 * @param z value
 * @param cl character class
 * @return Point object
 */
Point createPoint(int &x, int &y, int &z, char &cl) {
    Point p = {x, y, z, cl}; // creates a Point
    return p;
}

/**
 * Creates a new distance vector of values that shows distance between user input Point and all of vectorOfPoints
 * @param vectorOfPoints
 * @param last_point created by user
 * @return vector of distance values for each Point in the vectorOfPoints
 */
std::vector<int> compareDistance(const std::vector<Point> &vectorOfPoints, const int &last_point) {
    std::vector<int> distance(vectorOfPoints.size()-1); // -1 to avoid last point added by user

    // finds distance between all points compared with last point entered by user
    for (int i = 0; i < distance.size(); i++) {
        distance[i] = vectorOfPoints[last_point].getDistance(vectorOfPoints[i]);
    }
    return distance;
}

/**
 * Finds the closest points using smallest values calculated in the distance vector
 * @param distance - vector of all distances calculated compared to user input Point
 * @param last_closest - gives the last closest point so it isn't repeated
 * @return index of the next closest distance
 */
int findPoints(const std::vector<int> &distance, const int &last_closest) {
    int index = 0;
    for (int i = 0; i < distance.size(); i++) {
        if (distance[i] > last_closest && distance[i] < distance[index]) {
            index = i;
        }
    }
    return index;
}

/**
 * Counts how often each class occurrence is found in the closest points
 * @param vectorOfPoints
 * @param index of closest point found
 * @param A - num of points in class A
 * @param B - num of points in class B
 * @param C - num of points in class C
 * @param D - num of points in class D
 */
void countClass(const std::vector<Point> &vectorOfPoints, const int &index, int &A, int &B, int &C, int &D) {
    char cl = vectorOfPoints[index].cl;
    if (cl == 'A')
        A++;
    else if (cl == 'B')
        B++;
    else if (cl == 'C')
        C++;
    else
        D++;
}

/**
 *
 * @param vectorOfPoints
 * @param last_point - index of user entered Point
 * @param A - number of closest points with class A
 * @param B - number of closest points with class B
 * @param C - number of closest points with class C
 * @param D - number of closest points with class D
 */
void findClass(std::vector<Point> &vectorOfPoints, const int &last_point, const int &A, const int &B, const int &C, const int &D) {
    char cl;
    if (A >= std::max(B,C) && A >= std::max(B,D))
        cl = 'A';
    else if (B >= std::max(C,D) && B > A)
        cl = 'B';
    else if (C >= D && C > std::max(A,B))
        cl = 'C';
    else
        cl = 'D';

    vectorOfPoints[last_point].cl = cl;
}

int main() {
    std::vector<Point> vectorOfPoints(0); // empty vector

    // opens graph1.txt file, reads each line, and creates new Point for vectorOfPoints
    std::ifstream in;
    std::string line;
    in.open("graph1.txt");
    if (in.is_open()) {
        while (getline(in,line)) {
            // get value of x
            int pos_x = line.find_first_not_of("1234567890-");
            std::string str_x;
            str_x = line.substr(0,pos_x);
            int x;
            std::stringstream(str_x) >> x;

            // get value of y
            int pos_y_begin = line.find_first_of("1234567890-",pos_x+1);
            int pos_y_end = line.find_first_not_of("1234567890-",pos_y_begin+1);
            std::string str_y;
            str_y = line.substr(pos_y_begin,pos_y_end);
            int y;
            std::stringstream(str_y) >> y;

            // get value of z
            int pos_z_begin = line.find_first_of("1234567890-",pos_y_end+1);
            int pos_z_end = line.find_first_not_of("1234567890-",pos_z_begin+1);
            std::string str_z;
            str_z = line.substr(pos_z_begin,pos_z_end);
            int z;
            std::stringstream(str_z) >> z;

            // get value of cl
            int pos_cl = line.find_first_of("ABCD");
            char cl = line[pos_cl];

            vectorOfPoints.push_back(createPoint(x,y,z,cl));
        }
        in.close();
    }

    int x, y, z, k;
    std::cout << "Enter X and Y and Z: " << std::endl;
    std::cin >> x >> y >> z;
    std::cout << "What is the value for k? " << std::endl;
    std::cin >> k;
    char cl;

    vectorOfPoints.push_back(createPoint(x,y,z,cl));
    int last_point = static_cast<int>(vectorOfPoints.size()-1); // finds last point that was added

    char repeat;
    do {
        // creates distance vector for all distances of the user Point and vectorOfPoints
        std::vector<int> distance = compareDistance(vectorOfPoints, last_point);

        // finds smallest point closest to the user input Point
        int closest = findPoints(distance, 0); // returns index of closest distance value
        std::cout << vectorOfPoints[closest].printOut() << std::endl;

        int A_count = 0;
        int B_count = 0;
        int C_count = 0;
        int D_count = 0;

        // records which class closest is in
        countClass(vectorOfPoints, closest, A_count, B_count, C_count, D_count);

        // if k > 1, loops to find other closest points but doesn't repeat points
        if (k > 1) {
            for (int i = 1; i < k; i++) {
                closest = findPoints(distance, distance[closest]);
                std::cout << vectorOfPoints[closest].printOut() << std::endl;
                countClass(vectorOfPoints, closest, A_count, B_count, C_count, D_count);
            }
        }

        // finds the class user entered Point is in
        findClass(vectorOfPoints, last_point, A_count, B_count, C_count, D_count);
        std::cout << vectorOfPoints[last_point].printOut() << std::endl;

        // repeats program if user types 'Y' or 'y'
        std::cout << "Would you like to try another point? (Type 'Y' to repeat)" << std::endl;
        std::cin >> repeat;

        if (toupper(repeat) != 'Y') {
            std::cout << "Exiting program.";
            exit(0);
        }

        std::cout << "Enter X and Y and Z: " << std::endl;
        std::cin >> x >> y >> z;
        std::cout << "What is the value for k? " << std::endl;
        std::cin >> k;

        // creates a new point after the last added point when repeated
        vectorOfPoints.push_back(createPoint(x,y,z,cl));
        last_point = static_cast<int>(vectorOfPoints.size()-1); // finds last point that was added

    } while (toupper(repeat) == 'Y');

    return 0;
}
