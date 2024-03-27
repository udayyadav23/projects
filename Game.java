import java.util.ArrayList;
import java.util.Scanner;

class Questions {
    ArrayList<String> questions;
    ArrayList<String[]> options;
    ArrayList<Integer> answers;
    int length;

    public Questions() {
        questions = new ArrayList<>();
        options = new ArrayList<>();
        answers = new ArrayList<>();
        length = 0;
        initializeQuestions();
    }

    private void initializeQuestions() {
        questions.add("What does the acronym 'HTML' stand for?");
        questions.add("What is the output of 'print(2 + 2 * 3)' in Python?");
        questions.add("What data type is used to store a sequence of characters in Python?");
        questions.add("Which sorting algorithm has the best average time complexity?");
        questions.add("What is the purpose of the 'git' command in version control systems?");

        options.add(new String[]{"Hypertext Markup Language", "Hyper Text Making Language",
                "Hyperlink and Text Markup Language", "High Text Markup Language"});
        options.add(new String[]{"8", "10", "12", "14"});
        options.add(new String[]{"Integer", "List", "String", "Tuple"});
        options.add(new String[]{"Bubble Sort", "Insertion Sort", "Merge Sort", "Quick Sort"});
        options.add(new String[]{"To initialize a repository", "To commit changes",
                "To clone a repository", "To view repository history"});

        answers.add(1);
        answers.add(3);
        answers.add(3);
        answers.add(2);
        answers.add(1);

        length = questions.size();
    }

    public void addQuestions(String question, String[] option, int answer) {
        questions.add(question);
        options.add(option);
        answers.add(answer);
        length++;
    }

    public void deleteQuestions(int i) {
        if (i < 0 || i >= length) {
            System.out.println("Question Not Found. Please Check the Question Number Entered!!!");
            return;
        }
        if (length == 0) {
            System.out.println("No Question To Delete");
            return;
        }

        questions.remove(i);
        options.remove(i);
        answers.remove(i);
        length = questions.size();
        System.out.println("Question No " + (i + 1) + " has Been Deleted");
    }
}

class Review {
    Questions q;
    ArrayList<Integer> user;
    ArrayList<Integer> wrong;

    public Review(ArrayList<Integer> userAnswers, Questions questions) {
        q = questions;
        user = userAnswers;
        wrong = new ArrayList<>();
    }

    public String showResults() {
        int score = 0;
        for (int i = 0; i < q.length; i++) {
            if (user.get(i).equals(q.answers.get(i))) {
                score++;
            } else {
                wrong.add(i);
            }
        }
        return "Your Score Is " + score;
    }

    public void display() {
        if (q.length == 0) {
            System.out.println("No Questions to Display ");
            return;
        }
        for (int i = 0; i < q.length; i++) {
            System.out.println(q.questions.get(i));
            System.out.println("1. " + q.options.get(i)[0] + " 2. " + q.options.get(i)[1] + "\n"
                    + "3. " + q.options.get(i)[2] + " 4. " + q.options.get(i)[3]);
            System.out.println("Your Answer was " + q.options.get(i)[user.get(i) - 1]);
            if (!user.get(i).equals(q.answers.get(i))) {
                System.out.println("Your Answer is Wrong \n"
                        + "Correct Answer is " + q.options.get(i)[q.answers.get(i) - 1]);
            } else {
                System.out.println("Your Answer Was Correct \n");
            }
        }
    }
}

class Game {
    static Questions q = new Questions();

    private String password = "uday";

    public void adminPanel() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Password :");
        String inputPassword = sc.nextLine();

        if (password.equals(inputPassword)) {
            while (true) {
                System.out.println("1. Add Questions \n2. Display Questions \n3. Remove Question \n4. Exit ");
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 1) {
                    System.out.println("Enter Question :");
                    String question = sc.nextLine();
                    System.out.println("Enter Options in One Line With Space :");
                    String[] options = sc.nextLine().split(" ");
                    System.out.println("Enter Answer Option Number :");
                    int answer = Integer.parseInt(sc.nextLine());
                    q.addQuestions(question, options, answer);
                } else if (choice == 2) {
                    for (int i = 0; i < q.length; i++) {
                        System.out.println((i + 1) + ". " + q.questions.get(i));
                        System.out.println("1. " + q.options.get(i)[0] + " 2. " + q.options.get(i)[1] + "\n"
                                + "3. " + q.options.get(i)[2] + " 4. " + q.options.get(i)[3] + "\n");
                    }
                } else if (choice == 3) {
                    System.out.println("Enter the Question Number to Remove :");
                    int num = Integer.parseInt(sc.nextLine());
                    q.deleteQuestions(num - 1);
                } else if (choice == 4) {
                    System.out.println("Logging Out of Admin Panel ");
                    break;
                } else {
                    System.out.println("Please Select Option Between 1 - 4 ");
                }
            }
        } else {
            System.out.println("Wrong Password!!!!!");
        }
    }

    public void quizz() {
        System.out.println("---------------Quizz Game---------------");
        if (q.length == 0) {
            System.out.println("No Question are There in Quiz. Please Contact Admin For More Information");
            return;
        }
        ArrayList<Integer> user = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        for (int i = 0; i < q.length; i++) {
            System.out.println((i + 1) + ". " + q.questions.get(i));
            System.out.println("1. " + q.options.get(i)[0] + " 2. " + q.options.get(i)[1] + "\n"
                    + "3. " + q.options.get(i)[2] + " 4. " + q.options.get(i)[3]);
            user.add(Integer.parseInt(s.nextLine()));
        }
        Review r = new Review(user, q);
        System.out.println(r.showResults());
        System.out.println("Wanna Check The Answers (Y/N)");
        String check = s.nextLine();
        if (check.equals("Y")) {
            r.display();
        }
    }

    public static void main(String[] args) {
        Game g = new Game();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Admin Panel \n2. Play Quizz \n3. Exit\n");
            int choice = Integer.parseInt(sc.nextLine());
            if (choice == 1) {
                g.adminPanel();
            } else if (choice == 2) {
                g.quizz();
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}