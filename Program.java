public class Program {

    public static void main(String[] args) {

        HashMap<String, String> hashMap = new HashMap<>(6);


        hashMap.put("+79001112233", "Андрей");
        hashMap.put("+79046756112231", "Саша");
        hashMap.put("+79001465456112231", "Петя");
        hashMap.put("+7900123561122", "Маша");
        hashMap.put("+79001235611", "Вика");
        hashMap.put("+790012356112231", "Лера");


        for (Object o: hashMap) {
            System.out.println(o);
        }

    }

}