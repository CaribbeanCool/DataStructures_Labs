public class Lab01P1Wrapper {

    /**
     * A private static class named Arithmetic that has two private fields int a &
     * int b with their appropriate getters and setters. This class will also have
     * the following member methods that do basic mathematical operations:
     */
    private static class Arithmetic {
        /*
         * TODO ADD THE FOLLOWING: PRIVATE FIELDS, CONSTRUCTOR, GETTERS, SETTERS, MEMBER
         * METHODS
         */
        private int a, b;

        // Constructors
        public Arithmetic(int a, int b) {
            this.a = a;
            this.b = b;
        }

        // Getters and Setters
        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }

        public int add() {
            return a + b;
        }

        public int subtract() {
            return a - b;
        }

        public int multiply() {
            return a * b;
        }

        public double divide() {
            return a / b;
        }
    }

    public static class Calculator extends Arithmetic {
        public Calculator(int n1, int n2) {
            super(n1, n2);
        }

        public int add(int a, int b) {
            Arithmetic arithmetic = new Arithmetic(a, b);
            return arithmetic.add();
        }

        public int subtract(int a, int b) {
            Arithmetic arithmetic = new Arithmetic(a, b);
            return arithmetic.subtract();
        }

        public int multiply(int a, int b) {
            Arithmetic arithmetic = new Arithmetic(a, b);
            return arithmetic.multiply();
        }
    }
}