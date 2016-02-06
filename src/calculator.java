/**
 * Created by onotole on 06.02.16.
 */
public class calculator {
    public static void main(String args[]){
        if (! verify_input(args)) {
            System.out.println("Bad input's arguments!");
            System.exit(1);
        }

        String[] first_step_proceed = do_high_priority_operation(args);
        int result = do_low_priority_operation(first_step_proceed);
        System.out.println("Answer: " + result);
    }


    static boolean verify_input(String args[]){
        // string have to be like "\d+ op \d+ op \d+"
        if (args.length % 2 == 0) {
            return false;
        }

        int i = 0;
        while (i < args.length) {
            try {
                Integer.parseInt(args[i]);
            }
            catch (java.lang.NumberFormatException exceptionObject) {
                return false;
            }

            i += 2;
        }

        return true;
    }

    static void output_array(String args[]){
        for (int i = 0; i < args.length; i++){
            System.out.println("args[" + i + "] = " + args[i]);
        }
    }

    static String[] do_high_priority_operation(String args[]){
//        operations like * and / have to be executed before + and -
        String result[] = new String[args.length];

        int result_counter = 0;
        // hack for skip second argument after * or /
        // good: ['3', '*', '4'] -> ['12']
        // bad: ['3', '*', '4'] -> ['12', '4']
        boolean skip_second_argument = false;

        for (int i = 0; i < args.length; i++) {
            if (skip_second_argument) {
                skip_second_argument = false;
            } else {
                if (args[i].equals("*") | args[i].equals("/")) {
                    int first = Integer.parseInt(result[result_counter-1]);
                    int second = Integer.parseInt(args[i+1]);

                    if (args[i].equals("*")) {
                        result[result_counter-1] = String.valueOf(multiplication(first, second));
                    }
                    if (args[i].equals("/")) {
                        result[result_counter-1] = String.valueOf(integer_devision(first, second));
                    }
                    skip_second_argument = true;
                } else {
                    result[result_counter] = args[i];
                    result_counter++;
                }
            }

        }
        String clean_result[] = new String[result_counter];
        for (int i = 0; i < result_counter; i++) {
            clean_result[i] = result[i];
        }

        return clean_result;
    }

    static int do_low_priority_operation(String args[]){
        boolean skip_second_argument = false;
        int sum = Integer.parseInt(args[0]);
        for (int i = 0; i < args.length; i++) {
            if (skip_second_argument) {
                skip_second_argument = false;
            } else {
                if (args[i].equals("+") | args[i].equals("-")) {
                    if (args[i].equals("+")) {
                        sum = plus(sum, Integer.parseInt(args[i + 1]));
                    }
                    if (args[i].equals("-")) {
                        sum = minus(sum, Integer.parseInt(args[i + 1]));
                    }
                    skip_second_argument = true;
                }
            }
        }

        return sum;
    }

    static int plus(int first, int second){
        return first + second;
    }

    static int minus(int first, int second){
        return first - second;
    }

    static int multiplication(int first, int second){
        return first * second;
    }

    static int integer_devision(int first, int second){
        return first / second;
    }


}
