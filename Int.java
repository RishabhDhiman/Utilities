public class Int {
    public static Integer fromString(String input) {
        int output = 0;
        if (input == null || input.equals("")) {
            Log.e("NullValue", "The Value is null or empty String");
            return null;
        } else {
            try {
                output = (int) Double.parseDouble(input);
            } catch (NumberFormatException j) {
                output = 0;
                j.printStackTrace();
            }
        }
        return output;
    }

    public static String toString(Integer input) {
        String output;
        if (input == null) {
            return null;
        } else {
            output = String.valueOf(input);
        }
        return output;
    }
}
